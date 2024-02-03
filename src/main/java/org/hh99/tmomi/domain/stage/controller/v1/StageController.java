package org.hh99.tmomi.domain.stage.controller.v1;

import java.util.List;

import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto;
import org.hh99.tmomi.domain.stage.dto.stage.StageResponseDto;
import org.hh99.tmomi.domain.stage.service.StageService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class StageController {

	private final StageService stageService;

	@GetMapping("/stages")
	public ResponseEntity<List<StageResponseDto>> getStageListByAddress(@RequestBody StageRequestDto stageRequestDto) {
		return ResponseEntity.ok(stageService.getStageListByAddress(stageRequestDto));
	}

	@GetMapping("/stages/{stageId}")
	public ResponseEntity<StageResponseDto> getStage(@PathVariable Long stageId) {
		return ResponseEntity.ok(stageService.getStage(stageId));
	}

	@PostMapping("/stages")
	public ResponseEntity createStage(@RequestBody StageRequestDto stageRequestDto) {
			stageService.createStage(stageRequestDto);
		return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	@PutMapping("/stages/{stageId}")
	public ResponseEntity updateStage(@PathVariable Long stageId, @RequestBody StageRequestDto stageRequestDto) {
		stageService.updateStage(stageId, stageRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/stages/{stageId}")
	public ResponseEntity deleteStage(@PathVariable Long stageId) {
		stageService.deleteStage(stageId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}