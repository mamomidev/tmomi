package org.hh99.tmomi.domain.stage.dto.seat;

import org.hh99.tmomi.domain.stage.entity.Rank;
import org.hh99.tmomi.domain.stage.entity.Stage;

public class SeatRequestDto {
	private Long id;
	private Stage stage;
	private Rank rank;
	private String seatName;
	private Integer seatCapacity;
}
