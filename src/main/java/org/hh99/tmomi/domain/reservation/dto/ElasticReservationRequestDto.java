package org.hh99.tmomi.domain.reservation.dto;

import org.hh99.tmomi.domain.reservation.Status;

import lombok.Getter;

@Getter
public class ElasticReservationRequestDto {

	private String reservationId;
	private Status status;
}
