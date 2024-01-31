package org.hh99.tmomi.domain.stage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "seats")
public class Seat {
	@Id
	private Long id;

	@ManyToOne
	@JoinColumn(name = "stageId")
	private Stage stage;

	@OneToOne
	@JoinColumn(name = "rankId")
	private Rank rank;

	@Column
	private String seatName;

	@Column
	private Integer seatCapacity;
}
