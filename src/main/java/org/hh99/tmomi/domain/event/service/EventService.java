package org.hh99.tmomi.domain.event.service;

import java.util.List;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto;
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto;
import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.event.repository.EventTimesRepository;
import org.hh99.tmomi.domain.stage.entity.Stage;
import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EventService {

	private final EventRepository eventRepository;
	private final StageRepository stageRepository;
	private final EventTimesRepository eventTimesRepository;

	@Transactional
	public EventResponseDto createEvent(EventRequestDto eventRequestDto) {
		Stage stage = stageRepository.findById(eventRequestDto.getStageId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_STAGE));
		Event event = new Event(eventRequestDto, stage);
		eventRepository.save(event);

		return new EventResponseDto(event, stage.getAddress());
	}

	@Transactional
	public EventResponseDto updateEvent(EventRequestDto eventRequestDto, Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT));
		Stage stage = stageRepository.findById(eventRequestDto.getStageId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_STAGE));

		event.update(eventRequestDto, stage);

		return new EventResponseDto(event, stage.getAddress());
	}

	@Transactional
	public EventResponseDto deleteEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT));
		eventRepository.delete(event);

		return new EventResponseDto(event);
	}

	public EventResponseDto getEvent(Long eventId) {
		Event event = eventRepository.findById(eventId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT));
		Stage stage = stageRepository.findById(event.getStage().getId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_STAGE));
		List<EventTimesResponseDto> timeList = eventTimesRepository.findByEventId(eventId).stream().map(EventTimesResponseDto::new).toList();

		return new EventResponseDto(event, stage.getAddress(), timeList);
	}

	public List<EventResponseDto> getEventListByEventName(String query) {
		return eventRepository.findAllByEventNameContaining(query).stream().map(EventResponseDto::new).toList();
	}
}
