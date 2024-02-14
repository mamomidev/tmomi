package org.hh99.tmomi.domain.reservation.controller;

import org.hh99.tmomi.domain.reservation.dto.ReservationResponseDto;
import org.hh99.tmomi.domain.reservation.service.ReservationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

	private final ReservationService reservationService;


}
