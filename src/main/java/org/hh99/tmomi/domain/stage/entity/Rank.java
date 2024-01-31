package org.hh99.tmomi.domain.stage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "ranks")
public class Rank {
	@Id
	private Long id;

	@Column
	private String rankName;

	@Column
	private Integer price;
}
