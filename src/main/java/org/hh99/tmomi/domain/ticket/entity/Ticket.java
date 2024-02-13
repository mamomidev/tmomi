package org.hh99.tmomi.domain.ticket.entity;

import java.io.Serializable;

import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.user.entity.User;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "reservation_id", insertable = false, updatable = false)
	private Reservation reservation;

	@Column(name = "reservation_id")
	private Long reservationId;

	public Ticket(TicketRequestDto ticketRequestDto) {
		this.userId = ticketRequestDto.getUserId();
		this.reservationId = ticketRequestDto.getReservationId();
	}

}
