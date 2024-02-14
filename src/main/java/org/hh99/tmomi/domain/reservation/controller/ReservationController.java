package org.hh99.tmomi.domain.reservation.controller;

import org.hh99.tmomi.domain.reservation.service.ReservationService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

	private final ReservationService reservationService;

	// @PatchMapping("/reservation")
	// public ReservationRequestDto updateReservation(@RequestBody ReservationRequestDto reservationRequestDto) {
	// 	reservationservice.updateReservation()
	// }
}
