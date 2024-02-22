package org.hh99.tmomi.global.elasticsearch.document;

import jakarta.persistence.*;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.springframework.data.elasticsearch.annotations.Document;

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
	private String uuid;

	private Long eventId;

	private Long eventTimesId;

	private Long seatId;

	private Integer seatNumber;

	@Enumerated(EnumType.STRING)
	private Status status;

	public ElasticSearchReservation(String uuid, Reservation reservation) {
		this.uuid = uuid;
		this.eventId = reservation.getEventId();
		this.eventTimesId = reservation.getEventTimesId();
		this.seatId = reservation.getSeatId();
		this.seatNumber = reservation.getSeatNumber();
		this.status = reservation.getStatus();
	}
}
