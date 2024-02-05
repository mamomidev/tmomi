package org.hh99.tmomi.domain.stage.dto.rank;

import lombok.Getter;

@Getter
public class RankRequestDto {
	private Long stageId;
	private Long seatId;
	private String rankName;
	private Integer price;
}
