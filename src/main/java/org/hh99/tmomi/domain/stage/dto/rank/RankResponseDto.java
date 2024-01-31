package org.hh99.tmomi.domain.stage.dto.rank;

import org.hh99.tmomi.domain.stage.entity.Rank;
import org.hh99.tmomi.domain.stage.entity.Seat;

import lombok.Getter;

@Getter
public class RankResponseDto {
	private Seat seat;
	private String rankName;
	private Integer price;

	public RankResponseDto(Rank rank) {
		this.seat = rank.getSeat();
		this.rankName = rank.getRankName();
		this.price = rank.getPrice();
	}
}
