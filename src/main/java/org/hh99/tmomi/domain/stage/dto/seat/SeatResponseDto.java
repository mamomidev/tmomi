package org.hh99.tmomi.domain.stage.dto.seat;

import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.entity.Stage;

import lombok.Getter;

@Getter
public class SeatResponseDto {
	private Stage stage;
	private String seatName;
	private Integer seatCapacity;

	public SeatResponseDto(Seat seat) {
		this.stage = seat.getStage();
		this.seatName = seat.getSeatName();
		this.seatCapacity = seat.getSeatCapacity();
	}

}
