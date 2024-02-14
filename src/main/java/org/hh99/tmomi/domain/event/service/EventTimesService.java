package org.hh99.tmomi.domain.event.service;

import java.util.ArrayList;
import java.util.List;

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto;
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.event.repository.EventTimesRepository;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.repository.SeatRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTimesService {

	private final EventTimesRepository eventTimesRepository;
	private final EventRepository eventRepository;
	private final SeatRepository seatRepository;
	private final ReservationRepository reservationRepository;

	@Transactional
	public void createEventTimes(EventTimesRequestDto eventTimesRequestDto, Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT));
		EventTimes eventTimes = new EventTimes(eventTimesRequestDto, event);
		eventTimesRepository.save(eventTimes);
		List<Seat> seatList = seatRepository.findByStageId(event.getStage().getId());
		List<Reservation> reservationlist = new ArrayList<>();
		for (int i = 0; i < seatList.size(); i++) {
			for (int j = 1; j <= seatList.get(i).getSeatCapacity(); j++) {
				Reservation reservation = new Reservation(seatList.get(i), event, eventTimes,
					j);
				reservationlist.add(reservation);
			}
		}
		reservationRepository.saveAll(reservationlist);
	}

	@Transactional
	public EventTimesResponseDto updateEventTimes(EventTimesRequestDto eventTimesRequestDto, Long eventTimeId) {
		EventTimes eventTimes = eventTimesRepository.findById(eventTimeId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT_TIME));
		eventTimes.update(eventTimesRequestDto);

		return new EventTimesResponseDto(eventTimes);
	}

	@Transactional
	public EventTimesResponseDto deleteEventTimes(Long eventTimeId) {
		EventTimes eventTimes = eventTimesRepository.findById(eventTimeId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT_TIME));
		eventTimesRepository.delete(eventTimes);

		return new EventTimesResponseDto(eventTimes);
	}
}
