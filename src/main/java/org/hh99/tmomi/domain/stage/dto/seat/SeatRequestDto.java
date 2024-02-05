package org.hh99.tmomi.domain.stage.dto.seat;

import lombok.Getter;

@Getter
public class SeatRequestDto {
	private Long stageId;
	private String seatName;
	private Integer seatCapacity;
}
