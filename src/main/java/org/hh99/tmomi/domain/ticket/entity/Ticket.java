package org.hh99.tmomi.domain.ticket.entity;

import java.io.Serializable;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.global.elasticsearch.document.ElasticSearchReservation;
import org.springframework.security.core.userdetails.UserDetails;

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
import jakarta.persistence.OneToOne;
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

	@Column(name = "event_id")
	private Long eventId;

	@Column(name = "seat_id")
	private Long seatId;

	@Column(name = "reservation_id")
	private String reservationId;

	@Column(name ="seat_number")
	private Integer seatNumber;

	@Enumerated(EnumType.STRING)
	private Status status;

	public Ticket(ElasticSearchReservation elasticSearchReservation, Long userId) {
		this.userId = userId;
		this.eventId = elasticSearchReservation.getEventId();
		this.seatId = elasticSearchReservation.getSeatId();
		this.reservationId = elasticSearchReservation.getId();
		this.seatNumber = elasticSearchReservation.getSeatNumber();
		this.status = elasticSearchReservation.getStatus();
	}

	public void updateStatus(Status status) {
		this.status = status;
	}
}
