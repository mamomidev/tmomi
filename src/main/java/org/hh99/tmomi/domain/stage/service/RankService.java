package org.hh99.tmomi.domain.stage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hh99.tmomi.domain.stage.dto.rank.RankRequestDto;
import org.hh99.tmomi.domain.stage.dto.rank.RankResponseDto;
import org.hh99.tmomi.domain.stage.entity.Rank;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.repository.RankRepository;
import org.hh99.tmomi.domain.stage.repository.SeatRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankService {

	private final RankRepository rankRepository;
	private final SeatRepository seatRepository;

	public List<RankResponseDto> getRankListByStageId(Long stageId) {
		return rankRepository.findByStageId(stageId)
			.stream()
			.map(RankResponseDto::new)
			.collect(Collectors.toList());
	}

	public RankResponseDto createRank(RankRequestDto rankRequestDto) {
		Seat seat = seatRepository.findById(rankRequestDto.getSeatId())
			.orElseThrow(() -> new EntityNotFoundException());

		return new RankResponseDto(rankRepository.save(new Rank(rankRequestDto, seat, seat.getStage())));
	}

	public RankResponseDto updateRank(Long rankId, RankRequestDto rankRequestDto) {
		Seat seat = seatRepository.findById(rankRequestDto.getSeatId())
			.orElseThrow(() -> new EntityNotFoundException());
		Rank rank = rankRepository.findById(rankId).orElseThrow(() -> new EntityNotFoundException());
		rank.updateSeatAndRankNameAndPrice(rankRequestDto, seat);

		return new RankResponseDto(rank);
	}

	public RankResponseDto deleteRank(Long rankId) {
		Rank rank = rankRepository.findById(rankId).orElseThrow(() -> new EntityNotFoundException());
		rankRepository.deleteById(rankId);

		return new RankResponseDto(rank);
	}
}
