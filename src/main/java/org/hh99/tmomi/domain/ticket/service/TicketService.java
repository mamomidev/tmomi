package org.hh99.tmomi.domain.ticket.service;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto;
import org.hh99.tmomi.domain.reservation.dto.ReservationResponseDto;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.entity.Ticket;
import org.hh99.tmomi.domain.ticket.repository.TicketRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;
	private final RedissonClient redissonClient;

	@Transactional
	public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
		return new TicketResponseDto(ticketRepository.save(new Ticket(ticketRequestDto)));
	}

	@Transactional
	public void deleteTicket(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_TICKET));

		Reservation reservation = reservationRepository.findById(ticket.getReservationId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RESERVATION));

		reservation.updateStatus(Status.NONE);
		ticketRepository.delete(ticket);

	}

	public List<ReservationResponseDto> getReservationList(Long eventTimeId) {
		return reservationRepository.findAllByEventTimesIdAndStatus(eventTimeId, Status.NONE).stream()
				.map(ReservationResponseDto::new).toList();
	}

	@Transactional
	public void lockSeat(ReservationRequestDto reservationRequestDto) throws InterruptedException {
		String lockName = "seat_lock:" + reservationRequestDto.getId();
		RLock rLock = redissonClient.getLock(lockName);

		Reservation reservation = reservationRepository.findById(reservationRequestDto.getId()).orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RESERVATION));

		long waitTime = 0L;
		long leaseTime = 180L;
		boolean isLockAcquired = rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS); // 락 획득 시도

		if (!isLockAcquired) {
			throw new GlobalException(HttpStatus.LOCKED, ExceptionCode.NOT_EXIST_USER);
		}

		reservation.updateStatus(Status.RESERVATION);
	}

	@Transactional
	public void unlockSeat(String key) {
		String[] lockName = key.split(":");
		Long id = Long.parseLong(lockName[1]);

		Reservation reservation = reservationRepository.findById(id).orElseThrow();
		if(!reservation.getStatus().equals(Status.PURCHASE)) {
			reservation.updateStatus(Status.NONE);
		}
	}
}
