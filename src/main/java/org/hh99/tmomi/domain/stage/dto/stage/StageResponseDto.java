package org.hh99.tmomi.domain.stage.dto.stage;

import org.hh99.tmomi.domain.stage.entity.Stage;

import lombok.Getter;

@Getter
public class StageResponseDto {

	private final String address;

	public StageResponseDto(Stage stage) {
		this.address = stage.getAddress();
	}
}