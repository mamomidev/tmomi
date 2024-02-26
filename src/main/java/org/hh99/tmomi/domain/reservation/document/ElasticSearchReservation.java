package org.hh99.tmomi.domain.reservation.document;

import org.hh99.tmomi.domain.reservation.Status;
import org.springframework.data.elasticsearch.annotations.Document;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "reservation")
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

	public ElasticSearchReservation(String uuid, Long seatId, Long eventId, Long eventTimesId, int seatNumber) {
		this.id = uuid;
		this.eventId = eventId;
		this.eventTimesId = eventTimesId;
		this.seatId = seatId;
		this.seatNumber = seatNumber;
	}

	public void updateStatus(Status status) {
		this.status = status;
	}
}
