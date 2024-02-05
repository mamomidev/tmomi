package org.hh99.tmomi.domain.stage.dto.seat;

import org.hh99.tmomi.domain.stage.entity.Seat;

import lombok.Getter;

@Getter
public class SeatStageListResponseDto {
	private final String seatName;
	private final Integer seatCapacity;

	public SeatStageListResponseDto(Seat seat) {
		this.seatName = seat.getSeatName();
		this.seatCapacity = seat.getSeatCapacity();
	}
}
