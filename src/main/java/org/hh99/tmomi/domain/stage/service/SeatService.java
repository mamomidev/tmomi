package org.hh99.tmomi.domain.stage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hh99.tmomi.domain.stage.dto.seat.SeatRequestDto;
import org.hh99.tmomi.domain.stage.dto.seat.SeatResponseDto;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.entity.Stage;
import org.hh99.tmomi.domain.stage.repository.SeatRepository;
import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SeatService {

	private final StageRepository stageRepository;
	private final SeatRepository seatRepository;

	public List<SeatResponseDto> getSeatListByStageId(Long stageId) {
		return seatRepository.findByStageId(stageId)
			.stream()
			.map(SeatResponseDto::new)
			.collect(Collectors.toList());
	}

	public SeatResponseDto createSeat(SeatRequestDto seatRequestDto) {
		Stage stage = stageRepository.findById(seatRequestDto.getStageId())
			.orElseThrow(() -> new EntityNotFoundException());

		return new SeatResponseDto(seatRepository.save(new Seat(seatRequestDto, stage)));
	}

	@Transactional
	public SeatResponseDto updateSeat(Long seatId, SeatRequestDto seatRequestDto) {
		Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new EntityNotFoundException());
		seat.updateNameAndCapacity(seatRequestDto);

		return new SeatResponseDto(seat);
	}

	@Transactional
	public SeatResponseDto deleteSeat(Long seatId) {
		Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new EntityNotFoundException());
		seatRepository.deleteById(seatId);

		return new SeatResponseDto(seat);
	}
}
