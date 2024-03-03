package org.hh99.tmomi.domain.ticket.dto;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.ticket.entity.Ticket;

import lombok.Getter;

@Getter
public class TicketResponseDto {

	private final Long id;
	private final Long userId;
	private final Long eventId;
	private final Long seatId;
	private final String reservationId;
	private final Integer seatNumber;
	private final Status status;

	public TicketResponseDto(Ticket ticket) {
		this.id = ticket.getId();
		this.userId = ticket.getUserId();
		this.eventId = ticket.getEventId();
		this.seatId = ticket.getSeatId();
		this.reservationId = ticket.getReservationId();
		this.seatNumber = ticket.getSeatNumber();
		this.status = ticket.getStatus();
	}
}
