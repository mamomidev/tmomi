package org.hh99.tmomi.domain.reservation.dto;

import org.hh99.tmomi.domain.reservation.Status;

import lombok.Getter;

@Getter
public class ReservationRequestDto {

	private Long id;
	private Long eventId;
	private Long eventTimesId;
	private Long seatId;
	private Integer seatNumber;
	private Status status;

}
