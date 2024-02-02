package org.hh99.tmomi.domain.stage.dto.seat;

import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.entity.Stage;

import lombok.Getter;

@Getter
public class SeatResponseDto {

	private final Stage stage;
	private final String seatName;
	private final Integer seatCapacity;

	public SeatResponseDto(Seat seat) {
		this.stage = seat.getStage();
		this.seatName = seat.getSeatName();
		this.seatCapacity = seat.getSeatCapacity();
	}

}
