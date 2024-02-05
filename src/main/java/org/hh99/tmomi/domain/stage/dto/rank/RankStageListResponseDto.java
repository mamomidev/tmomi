package org.hh99.tmomi.domain.stage.dto.rank;

import org.hh99.tmomi.domain.stage.entity.Rank;

import lombok.Getter;

@Getter
public class RankStageListResponseDto {

	private final String rankName;
	private final Integer price;

	public RankStageListResponseDto(Rank rank) {
		this.rankName = rank.getRankName();
		this.price = rank.getPrice();
	}
}
