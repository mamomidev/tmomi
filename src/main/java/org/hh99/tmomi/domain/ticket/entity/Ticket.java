package org.hh99.tmomi.domain.ticket.entity;

import java.io.Serializable;

import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.ticket.TicketStatus;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "tickets")
public class Ticket implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id")
	private Event event;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_times_id")
	private EventTimes eventTimes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@Column
	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;

	@Column
	private String seatNumber;

	public Ticket(TicketRequestDto ticketRequestDto, User user, Event event, EventTimes eventTimes, Seat seat) {
		this.user = user;
		this.event = event;
		this.eventTimes = eventTimes;
		this.seat = seat;
		this.ticketStatus = ticketRequestDto.getTicketStatus();
		this.seatNumber = ticketRequestDto.getSeatNumber();
	}

	public void refund() {
		this.ticketStatus = TicketStatus.REFUND;
	}
}
