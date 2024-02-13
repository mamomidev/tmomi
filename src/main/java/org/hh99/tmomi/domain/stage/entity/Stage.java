package org.hh99.tmomi.domain.stage.entity;

import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "stages")
@NoArgsConstructor
public class Stage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String address;

	@Column
	private String alias;

	public Stage(StageRequestDto stageRequestDto) {
		this.address = stageRequestDto.getAddress();
	}

	public void updateAddress(StageRequestDto stageRequestDto) {
		this.address = stageRequestDto.getAddress();
	}
}
