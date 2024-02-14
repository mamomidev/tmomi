package org.hh99.tmomi.domain.reservation.entity;

import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto;
import org.hh99.tmomi.domain.stage.entity.Seat;

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
@Table(name = "reservations")
public class Reservation {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

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

	@Column(name = "seat_number")
	private Integer seatNumber;

	@Column(name = "status")
	@Enumerated(EnumType.STRING)
	private Status status;

	public Reservation(ReservationRequestDto reservationRequestDto) {
		this.eventId = reservationRequestDto.getEventId();
		this.eventTimesId = reservationRequestDto.getEventTimesId();
		this.seatId = reservationRequestDto.getSeatId();
		this.seatNumber = reservationRequestDto.getSeatNumber();
		this.status = reservationRequestDto.getStatus();
	}

	public Reservation(Seat seat, Event event, EventTimes eventTimes, Integer seatNumber) {
		this.seatId = seat.getId();
		this.eventId = event.getId();
		this.eventTimesId = eventTimes.getId();
		this.seatNumber = seatNumber;
		this.status = Status.NONE;
	}

	public void updateStatus(Status status) {
		this.status = status;
	}
}
