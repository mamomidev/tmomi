package org.hh99.tmomi.domain.ticket.dto;

import java.time.LocalDate;

import org.hh99.tmomi.domain.ticket.entity.Ticket;

import lombok.Getter;

@Getter
public class TicketResponseDto {

	private Long id;
	private final String userEmail;
	private final String eventName;
	private final LocalDate eventDate;
	private final String seatName;
	private final String seatNumber;

	public TicketResponseDto(Ticket ticket) {
		this.userEmail = ticket.getUser().getEmail();
		this.eventName = ticket.getEvent().getEventName();
		this.eventDate = ticket.getEvent().getEventEndDate();
		this.seatName = ticket.getSeat().getSeatName();
		this.seatNumber = ticket.getSeatNumber();
	}
}
