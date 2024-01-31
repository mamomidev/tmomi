package org.hh99.tmomi.domain.stage.dto.seat;

import lombok.Getter;

@Getter
public class SeatRequestDto {
	private Long id;
	private Long stageId;
	private Long rankId;
	private String seatName;
	private Integer seatCapacity;
}
