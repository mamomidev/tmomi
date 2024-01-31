package org.hh99.tmomi.domain.stage.entity;

import org.hh99.tmomi.domain.stage.dto.seat.SeatRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "seats")
@NoArgsConstructor
public class Seat {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "stage_id")
	private Stage stage;

	@Column
	private String seatName;

	@Column
	private Integer seatCapacity;

	public Seat(SeatRequestDto seatRequestDto, Stage stage) {
		this.stage = stage;
		this.seatName = seatRequestDto.getSeatName();
		this.seatCapacity = seatRequestDto.getSeatCapacity();
	}

	public void updateNameAndCapacity(SeatRequestDto seatRequestDto) {
		this.seatName = seatRequestDto.getSeatName();
		this.seatCapacity = seatRequestDto.getSeatCapacity();
	}
}