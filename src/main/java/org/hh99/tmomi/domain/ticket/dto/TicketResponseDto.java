package org.hh99.tmomi.domain.ticket.dto;

import org.hh99.tmomi.domain.ticket.entity.Ticket;

import lombok.Getter;

@Getter
public class TicketResponseDto {

	private final Ticket ticket;

	public TicketResponseDto(Ticket ticket) {
		this.ticket = ticket;
	}
}
