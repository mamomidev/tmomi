package org.hh99.tmomi.domain.stage.controller.v1;

import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class StageController {

	@PostMapping("/v1/stage")
	public void createStage(@RequestBody StageRequestDto stageRequestDto) {

	}

	@PutMapping("/v1/stage/{stageId}")
	public void updateStage(@PathVariable Long stageId, @RequestBody StageRequestDto stageRequestDto) {

	}

	@DeleteMapping("/v1/stage/{stageId}")
	public void deleteStage(@PathVariable Long stageId) {

	}
}
