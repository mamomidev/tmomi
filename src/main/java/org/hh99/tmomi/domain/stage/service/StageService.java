package org.hh99.tmomi.domain.stage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto;
import org.hh99.tmomi.domain.stage.dto.stage.StageResponseDto;
import org.hh99.tmomi.domain.stage.entity.Stage;
import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageService {

	private final StageRepository stageRepository;

	public List<StageResponseDto> getStageListByAddress(StageRequestDto stageRequestDto) {
		return stageRepository.findByAddress(stageRequestDto.getAddress())
			.stream()
			.map(StageResponseDto::new)
			.collect(Collectors.toList());
	}

	public StageResponseDto getStage(Long stageId) {
		return new StageResponseDto(stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException()));
	}

	public StageResponseDto createStage(StageRequestDto stageRequestDto) {
		return new StageResponseDto(new Stage(stageRequestDto));
	}

	public StageResponseDto updateStage(Long stageId, StageRequestDto stageRequestDto) {
		Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException());
		stage.updateAddress(stageRequestDto);

		return new StageResponseDto(stage);
	}

	public StageResponseDto deleteStage(Long stageId) {
		Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException());
		stageRepository.deleteById(stageId);
		
		return new StageResponseDto(stage);
	}
}