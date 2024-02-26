package org.hh99.tmomi.domain.ticket.service;

import java.util.concurrent.TimeUnit;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.document.ElasticSearchReservation;
import org.hh99.tmomi.domain.reservation.dto.ElasticReservationRequestDto;
import org.hh99.tmomi.domain.reservation.respository.ElasticSearchReservationRepository;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.entity.Ticket;
import org.hh99.tmomi.domain.ticket.repository.TicketRepository;
import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.domain.user.repository.UserRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.hh99.tmomi.global.redis.SeatValidate;
import org.hh99.tmomi.global.redis.SeatValidateRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final UserRepository userRepository;
	private final ElasticSearchReservationRepository elasticSearchReservationRepository;
	private final RedissonClient redissonClient;
	private final ElasticsearchTemplate elasticsearchTemplate;
	private final SeatValidateRepository seatValidateRepository;

	@Transactional
	public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto, String userEmail) {
		SeatValidate seatValidate = seatValidateRepository.findById(ticketRequestDto.getReservationId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_SEAT));

		if (!seatValidate.getEmail().equals(userEmail)) {
			throw new GlobalException(HttpStatus.LOCKED, ExceptionCode.NOT_SELECT_LOCKED);
		}

		ElasticSearchReservation elasticSearchReservation = elasticSearchReservationRepository.findById(
				ticketRequestDto.getReservationId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RESERVATION));
		elasticSearchReservation.updateStatus(Status.PURCHASE);
		elasticsearchTemplate.update(elasticSearchReservation);
		User users = userRepository.findByEmail(userEmail).orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_USER));
		Ticket ticket = ticketRepository.findByReservationId(ticketRequestDto.getReservationId());
		if (ticket != null && ticket.getStatus().equals(Status.PURCHASE)) {
			throw new GlobalException(HttpStatus.BAD_REQUEST, ExceptionCode.PURCHASED_TICKET);
		}
		return new TicketResponseDto(ticketRepository.save(new Ticket(elasticSearchReservation, users.getId())));
	}

	@Transactional
	public void refundTicket(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_TICKET));

		ElasticSearchReservation elasticSearchReservation = elasticSearchReservationRepository.findById(
				ticket.getReservationId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RESERVATION));

		elasticSearchReservation.updateStatus(Status.NONE);
		elasticsearchTemplate.update(elasticSearchReservation);

		ticket.updateStatus(Status.REFUND);
	}

	@Transactional
	public void updateReservationStatusWithLocked(ElasticReservationRequestDto elasticReservationRequestDto,
		String email) throws
		InterruptedException {

		String uuid = elasticReservationRequestDto.getReservationId();
		String lockName = "seat_lock:" + uuid;
		RLock rLock = redissonClient.getLock(lockName);

		ElasticSearchReservation elasticSearchReservation = elasticSearchReservationRepository.findById(
				elasticReservationRequestDto.getReservationId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RESERVATION));

		long waitTime = 0L;
		long leaseTime = 180L;
		boolean isLockAcquired = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS); // 락 획득 시도

		if (!isLockAcquired) {
			throw new GlobalException(HttpStatus.LOCKED, ExceptionCode.LOCKED);
		}

		seatValidateRepository.save(new SeatValidate(uuid, email));
		elasticSearchReservation.updateStatus(Status.RESERVATION);
		elasticsearchTemplate.update(elasticSearchReservation);

	}

	@Transactional
	public void updateReservationStatusWithUnLocked(String key) {
		String[] lockName = key.split(":");
		String id = lockName[1];

		ElasticSearchReservation elasticSearchReservation = elasticSearchReservationRepository.findById(id)
			.orElseThrow();
		if (!elasticSearchReservation.getStatus().equals(Status.PURCHASE)) {
			elasticSearchReservation.updateStatus(Status.NONE);
			elasticsearchTemplate.update(elasticSearchReservation);
		}
	}
}
