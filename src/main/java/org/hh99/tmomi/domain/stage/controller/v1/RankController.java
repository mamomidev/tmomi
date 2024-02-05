package org.hh99.tmomi.domain.stage.controller.v1;

import java.util.List;

import org.hh99.tmomi.domain.stage.dto.rank.RankRequestDto;
import org.hh99.tmomi.domain.stage.dto.rank.RankResponseDto;
import org.hh99.tmomi.domain.stage.dto.rank.RankStageListResponseDto;
import org.hh99.tmomi.domain.stage.service.RankService;
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
public class RankController {

	private final RankService rankService;

	@GetMapping("/stages/{stageId}/ranks")
	public ResponseEntity<List<RankStageListResponseDto>> getRankListByStageId(@PathVariable Long stageId) {
		return ResponseEntity.ok(rankService.getRankListByStageId(stageId));
	}

	@PostMapping("/ranks")
	public ResponseEntity<RankResponseDto> createRank(@RequestBody RankRequestDto rankRequestDto) {
		return ResponseEntity.ok(rankService.createRank(rankRequestDto));
	}

	@PutMapping("/ranks/{rankId}")
	public ResponseEntity<RankResponseDto> updateRank(@PathVariable Long rankId,
		@RequestBody RankRequestDto rankRequestDto) {
		return ResponseEntity.ok(rankService.updateRank(rankId, rankRequestDto));
	}

	@DeleteMapping("/ranks/{rankId}")
	public ResponseEntity<RankResponseDto> deleteRank(@PathVariable Long rankId) {
		return ResponseEntity.ok(rankService.deleteRank(rankId));
	}
}
