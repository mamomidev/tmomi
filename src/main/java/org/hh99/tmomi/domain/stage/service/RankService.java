package org.hh99.tmomi.domain.stage.service;

import java.util.List;
import java.util.stream.Collectors;

import org.hh99.tmomi.domain.stage.dto.rank.RankRequestDto;
import org.hh99.tmomi.domain.stage.dto.rank.RankResponseDto;
import org.hh99.tmomi.domain.stage.dto.rank.RankStageListResponseDto;
import org.hh99.tmomi.domain.stage.entity.Rank;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.repository.RankRepository;
import org.hh99.tmomi.domain.stage.repository.SeatRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankService {

	private final RankRepository rankRepository;
	private final SeatRepository seatRepository;

	public List<RankStageListResponseDto> getRankListByStageId(Long stageId) {
		return rankRepository.findByStageId(stageId)
			.stream()
			.map(RankStageListResponseDto::new)
			.collect(Collectors.toList());
	}

	public RankResponseDto createRank(RankRequestDto rankRequestDto) {
		Seat seat = seatRepository.findById(rankRequestDto.getSeatId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RANK));

		return new RankResponseDto(rankRepository.save(new Rank(rankRequestDto, seat, seat.getStage())));
	}

	@Transactional
	public RankResponseDto updateRank(Long rankId, RankRequestDto rankRequestDto) {
		Seat seat = seatRepository.findById(rankRequestDto.getSeatId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_SEAT));
		Rank rank = rankRepository.findById(rankId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RANK));
		rank.updateSeatAndRankNameAndPrice(rankRequestDto, seat);

		return new RankResponseDto(rank);
	}

	@Transactional
	public RankResponseDto deleteRank(Long rankId) {
		Rank rank = rankRepository.findById(rankId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_RANK));
		rankRepository.deleteById(rankId);

		return new RankResponseDto(rank);
	}
}
