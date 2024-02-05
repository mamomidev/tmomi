package org.hh99.tmomi.domain.stage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hh99.tmomi.domain.stage.dto.stage.StageRequestDto;
import org.hh99.tmomi.domain.stage.dto.stage.StageResponseDto;
import org.hh99.tmomi.domain.stage.entity.Stage;
import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StageService {

	private final StageRepository stageRepository;

	public List<StageResponseDto> getStageListByAddress(StageRequestDto stageRequestDto) {
		List<StageResponseDto> stageList = stageRepository.findByAddressContaining(stageRequestDto.getAddress())
			.stream()
			.map(StageResponseDto::new)
			.collect(Collectors.toList());

		if (stageList.size() == 0) {
			throw new EntityExistsException();
		}

		return stageList;
	}

	public StageResponseDto getStage(Long stageId) {
		return new StageResponseDto(stageRepository.findById(stageId).orElseThrow(() -> new GlobalException(
			HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_TICKET)));
	}

	public StageResponseDto createStage(StageRequestDto stageRequestDto) {
		Stage stage = new Stage(stageRequestDto);
		stageRepository.save(stage);
		return new StageResponseDto(new Stage(stageRequestDto));
	}

	@Transactional
	public StageResponseDto updateStage(Long stageId, StageRequestDto stageRequestDto) {
		Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException());
		stage.updateAddress(stageRequestDto);

		return new StageResponseDto(stage);
	}

	@Transactional
	public StageResponseDto deleteStage(Long stageId) {
		Stage stage = stageRepository.findById(stageId).orElseThrow(() -> new EntityNotFoundException());
		stageRepository.deleteById(stageId);

		return new StageResponseDto(stage);
	}
}