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
	@JoinColumn(name = "users_id", insertable = false, updatable = false)
	private User user;

	@Column(name = "users_id")
	private Long userId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_id", insertable = false, updatable = false)
	private Event event;

	@Column(name = "event_id")
	private Long eventId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "event_times_id", insertable = false, updatable = false)
	private EventTimes eventTimes;

	@Column(name = "event_times_id")
	private Long eventTimesId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id", insertable = false, updatable = false)
	private Seat seat;

	@Column(name = "seat_id")
	private Long seatId;

	@Column
	@Enumerated(EnumType.STRING)
	private TicketStatus ticketStatus;

	@Column
	private String seatNumber;

	public Ticket(TicketRequestDto ticketRequestDto) {
		this.userId = ticketRequestDto.getUserId();
		this.eventId = ticketRequestDto.getEventId();
		this.eventTimesId = ticketRequestDto.getEventTimesId();
		this.seatId = ticketRequestDto.getSeatId();
		this.ticketStatus = ticketRequestDto.getTicketStatus();
		this.seatNumber = ticketRequestDto.getSeatNumber();
	}

	public void refund() {
		this.ticketStatus = TicketStatus.REFUND;
	}
}
