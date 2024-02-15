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

	public void lockSeat(ReservationRequestDto reservationRequestDto, String userEmail) {
		String lockName = "seat_lock:"+ userEmail + ":" + reservationRequestDto.getEventTimesId() + ":" + reservationRequestDto.getSeatId();
		RLock rLock = redissonClient.getLock(lockName);

		long waitTime = 0;
		long leaseTime = 180L;
		Reservation reservation = reservationRepository.findById(reservationRequestDto.getId()).orElseThrow();

		try {
			boolean isLockAcquired = rLock.tryLock(0, 180, TimeUnit.SECONDS); // 락 획득 시도
			if (isLockAcquired) {
				reservation.updateStatus(Status.RESERVATION);
			} else {
				// 락을 획득하지 못한 경우, 이미 선택된 좌석
			}
		} catch (InterruptedException e) {
			// 이미 선택된 좌석
		} finally {
			if (rLock.isLocked() && rLock.isHeldByCurrentThread()) {
				reservation.updateStatus(Status.NONE);
				rLock.unlock();
			}
		}

	}
}
