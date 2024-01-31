package org.hh99.tmomi.domain.event.service;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;

	public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
		Event event = new Event(eventRequestDto);
		eventRepository.save(event);

		return new EventResponseDto(event);
	}

	@Transactional
	public EventResponseDto updateEvent(EventRequestDto eventRequestDto, Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(""));
		event.update(eventRequestDto);

		return new EventResponseDto(event);
	}

	public EventResponseDto deleteEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(""));
		eventRepository.delete(event);

		return new EventResponseDto(event);
	}
}
