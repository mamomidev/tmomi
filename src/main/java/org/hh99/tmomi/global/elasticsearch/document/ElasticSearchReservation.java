package org.hh99.tmomi.global.elasticsearch.document;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ElasticSearchReservation {

	@Id
	private String id;

	private Long eventId;

	private Long eventTimesId;

	private Long seatId;

	private Integer seatNumber;

	@Enumerated(EnumType.STRING)
	private Status status;

	public ElasticSearchReservation(String uuid, Reservation reservation) {
		this.id = uuid;
		this.eventId = reservation.getEventId();
		this.eventTimesId = reservation.getEventTimesId();
		this.seatId = reservation.getSeatId();
		this.seatNumber = reservation.getSeatNumber();
		this.status = reservation.getStatus();
	}
}
