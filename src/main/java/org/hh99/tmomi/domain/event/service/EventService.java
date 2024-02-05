package org.hh99.tmomi.domain.event.service;

import java.util.List;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.stage.entity.Stage;
import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final StageRepository stageRepository;

	public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
		Stage stage = stageRepository.findById(eventRequestDto.getStageId())
			.orElseThrow(() -> new EntityNotFoundException(""));
		Event event = new Event(eventRequestDto, stage);
		eventRepository.save(event);

		return new EventResponseDto(event, stage.getAddress());
	}

	@Transactional
	public EventResponseDto updateEvent(EventRequestDto eventRequestDto, Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(""));
		Stage stage = stageRepository.findById(eventRequestDto.getStageId())
			.orElseThrow(() -> new EntityNotFoundException(""));

		event.update(eventRequestDto, stage);

		return new EventResponseDto(event, stage.getAddress());
	}

	public EventResponseDto deleteEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(""));
		eventRepository.delete(event);

		return new EventResponseDto(event);
	}

	public EventResponseDto getEvent(Long eventId) {
		Event event = eventRepository.findById(eventId).orElseThrow(() -> new EntityNotFoundException(""));
		Stage stage = stageRepository.findById(event.getStage().getId())
			.orElseThrow(() -> new EntityNotFoundException(""));

		return new EventResponseDto(event, stage.getAddress());
	}

	public List<EventResponseDto> getEventListByEventName(String query) {
		return eventRepository.findAllByEventNameContaining(query).stream().map(EventResponseDto::new).toList();
	}
}
