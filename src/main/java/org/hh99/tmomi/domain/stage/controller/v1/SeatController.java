package org.hh99.tmomi.domain.stage.controller.v1;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SeatController {

	@PostMapping("/v1/seat")
	public void createSeat() {

	}

	@PutMapping("/v1/seat/{seatId}")
	public void updateSeat(@PathVariable Long seatId) {

	}

	@DeleteMapping("/v1/seat/{seatId}")
	public void deleteSeat(@PathVariable Long seatId) {

	}
}
