package org.hh99.tmomi.domain.ticket.dto;

import org.hh99.tmomi.domain.ticket.entity.Ticket;

import lombok.Getter;

@Getter
public class TicketResponseDto {

	private Long id;
	private final Long userId;
	private final Long reservationId;

	public TicketResponseDto(Ticket ticket) {
		this.userId = ticket.getUserId();
		this.reservationId = ticket.getReservationId();
	}
}
