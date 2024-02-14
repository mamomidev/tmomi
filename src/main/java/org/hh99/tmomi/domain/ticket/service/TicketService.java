package org.hh99.tmomi.domain.ticket.service;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.entity.Ticket;
import org.hh99.tmomi.domain.ticket.repository.TicketRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final TicketRepository ticketRepository;
	private final ReservationRepository reservationRepository;

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

}
