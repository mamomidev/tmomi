package org.hh99.tmomi.domain.reservation.service;

import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto;
import org.hh99.tmomi.domain.reservation.dto.ReservationResponseDto;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

	private final ReservationRepository reservationRepository;

	@Transactional
	public void createReservation(ReservationRequestDto reservationRequestDto) {
		Reservation reservation = new Reservation(reservationRequestDto);
		reservationRepository.save(reservation);
	}
}
