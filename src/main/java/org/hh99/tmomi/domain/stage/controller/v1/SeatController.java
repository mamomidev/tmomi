package org.hh99.tmomi.domain.stage.controller.v1;

import java.util.List;

import org.hh99.tmomi.domain.stage.dto.seat.SeatRequestDto;
import org.hh99.tmomi.domain.stage.dto.seat.SeatResponseDto;
import org.hh99.tmomi.domain.stage.dto.seat.SeatStageListResponseDto;
import org.hh99.tmomi.domain.stage.service.SeatService;
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
public class SeatController {

	private final SeatService seatService;

	@GetMapping("/stages/{stageId}/seats")
	public ResponseEntity<List<SeatStageListResponseDto>> getSeatListByStageId(@PathVariable Long stageId) {
		return ResponseEntity.ok(seatService.getSeatListByStageId(stageId));
	}

	@PostMapping("/seats")
	public ResponseEntity<SeatResponseDto> createSeat(@RequestBody SeatRequestDto seatRequestDto) {
		return ResponseEntity.status(HttpStatus.CREATED).body(seatService.createSeat(seatRequestDto));
	}

	@PutMapping("/seats/{seatId}")
	public ResponseEntity updateSeat(@PathVariable Long seatId, @RequestBody SeatRequestDto seatRequestDto) {
		seatService.updateSeat(seatId, seatRequestDto);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/seats/{seatId}")
	public ResponseEntity deleteSeat(@PathVariable Long seatId) {
		seatService.deleteSeat(seatId);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
