package org.hh99.tmomi.domain.event.service;

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto;
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.event.repository.EventTimesRepository;
import org.springframework.stereotype.Service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventTimesService {

	private final EventTimesRepository eventTimesRepository;
	private final EventRepository eventRepository;

	public EventTimesResponseDto createEventTimes(EventTimesRequestDto eventTimesRequestDto) {
		Event event = getEventById(eventTimesRequestDto.getEventId());
		EventTimes eventTimes = new EventTimes(eventTimesRequestDto, event);

		return new EventTimesResponseDto(eventTimes);
	}

	public EventTimesResponseDto updateEventTimes(EventTimesRequestDto eventTimesRequestDto, Long eventTimeId) {
		EventTimes eventTimes = getEventTimeById(eventTimeId);
		eventTimes.update(eventTimesRequestDto);

		return new EventTimesResponseDto(eventTimes);
	}

	public EventTimesResponseDto deleteEventTimes(Long eventTimeId) {
		EventTimes eventTimes = getEventTimeById(eventTimeId);
		eventTimesRepository.delete(eventTimes);

		return new EventTimesResponseDto(eventTimes);
	}

	private Event getEventById(Long eventId) {
		return eventRepository.findById(eventId).orElseThrow(EntityNotFoundException::new);
	}

	private EventTimes getEventTimeById(Long eventTimeId) {
		return eventTimesRepository.findById(eventTimeId).orElseThrow(EntityNotFoundException::new);
	}
}
