package org.hh99.tmomi.domain.stage.entity;

import org.hh99.tmomi.domain.stage.dto.rank.RankRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "ranks")
@NoArgsConstructor
public class Rank {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id")
	private Stage stage;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "seat_id")
	private Seat seat;

	@Column
	private String rankName;

	@Column
	private Integer price;

	public Rank(RankRequestDto rankRequestDto, Seat seat, Stage stage) {
		this.seat = seat;
		this.stage = stage;
		this.rankName = rankRequestDto.getRankName();
		this.price = rankRequestDto.getPrice();
	}

	public void updateSeatAndRankNameAndPrice(RankRequestDto rankRequestDto, Seat seat) {
		this.seat = seat;
		this.rankName = rankRequestDto.getRankName();
		this.price = rankRequestDto.getPrice();
	}
}
