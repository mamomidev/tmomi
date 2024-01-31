package org.hh99.tmomi.domain.ticket.dto;

import org.hh99.tmomi.domain.ticket.TicketStatus;

import lombok.Getter;

@Getter
public class TicketRequestDto {

	private Long id;
	private Long userId;
	private Long eventId;
	private Long eventTimesId;
	private Long seatId;
	private TicketStatus ticketStatus;
	private String seatNumber;
}
