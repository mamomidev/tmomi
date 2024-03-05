package org.hh99.tmomi.domain.ticket.dto;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.ticket.entity.Ticket;

import lombok.Getter;

@Getter
public class TicketResponseDto {

	private final Long id;
	private final String email;
	private final String eventName;
	private final String seatName;
	private final String reservationId;
	private final Integer seatNumber;
	private final Status status;

	public TicketResponseDto(Ticket ticket, String email, String eventName, String seatName) {
		this.id = ticket.getId();
		this.email = email;
		this.eventName = eventName;
		this.seatName = seatName;
		this.reservationId = ticket.getReservationId();
		this.seatNumber = ticket.getSeatNumber();
		this.status = ticket.getStatus();
	}
}
