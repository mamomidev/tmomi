package org.hh99.tmomi.domain.event.service;

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto;
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.event.repository.EventTimesRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTimesService {

	private final EventTimesRepository eventTimesRepository;
	private final EventRepository eventRepository;

	public EventTimesResponseDto createEventTimes(EventTimesRequestDto eventTimesRequestDto, Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new EntityNotFoundException(""));
		EventTimes eventTimes = new EventTimes(eventTimesRequestDto, event);
		eventTimesRepository.save(eventTimes);
		return new EventTimesResponseDto(eventTimes);
	}

	@Transactional
	public EventTimesResponseDto updateEventTimes(EventTimesRequestDto eventTimesRequestDto, Long eventTimeId) {
		EventTimes eventTimes = eventTimesRepository.findById(eventTimeId)
			.orElseThrow(() -> new EntityNotFoundException(""));
		eventTimes.update(eventTimesRequestDto);

		return new EventTimesResponseDto(eventTimes);
	}

	@Transactional
	public EventTimesResponseDto deleteEventTimes(Long eventTimeId) {
		EventTimes eventTimes = eventTimesRepository.findById(eventTimeId)
			.orElseThrow(() -> new EntityNotFoundException(""));
		eventTimesRepository.delete(eventTimes);

		return new EventTimesResponseDto(eventTimes);
	}
}
