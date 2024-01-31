package org.hh99.tmomi.domain.stage.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
@Getter
public class Stage {
	@Id
	private Long id;

	@Column
	private String location;
}
