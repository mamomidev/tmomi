package org.hh99.tmomi.domain.stage.dto.rank;

import lombok.Getter;

@Getter
public class RankRequestDto {
	private Long id;
	private Long seatId;
	private String rankName;
	private Integer price;
}
