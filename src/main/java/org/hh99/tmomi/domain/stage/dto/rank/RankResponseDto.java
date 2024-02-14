package org.hh99.tmomi.domain.stage.dto.rank;

import org.hh99.tmomi.domain.stage.entity.Rank;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.entity.Stage;

import lombok.Getter;

@Getter
public class RankResponseDto {
	private final Long seatId;
	private final String seatName;
	private final Integer seatCapacity;

	private final String rankName;
	private final Integer price;

	public RankResponseDto(Rank rank) {
		this.seatId = rank.getSeat().getId();
		this.seatName = rank.getSeat().getSeatName();
		this.seatCapacity = rank.getSeat().getSeatCapacity();
		this.rankName = rank.getRankName();
		this.price = rank.getPrice();
	}
}
