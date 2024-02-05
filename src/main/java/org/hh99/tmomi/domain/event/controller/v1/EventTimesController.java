package org.hh99.tmomi.domain.event.controller.v1;

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto;
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto;
import org.hh99.tmomi.domain.event.service.EventTimesService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class EventTimesController {

	private final EventTimesService eventTimesService;

	@PostMapping("/times/{eventId}")
	public ResponseEntity<EventTimesResponseDto> createEventTimes(@PathVariable Long eventId,
		@RequestBody EventTimesRequestDto eventTimesRequestDto) {
		return ResponseEntity.ok(eventTimesService.createEventTimes(eventTimesRequestDto, eventId));
	}

	@PutMapping("/times/{eventTimesId}")
	public ResponseEntity<EventTimesResponseDto> updateEventTimes(@PathVariable Long eventTimesId,
		@RequestBody EventTimesRequestDto eventTimesRequestDto) {
		return ResponseEntity.ok(eventTimesService.updateEventTimes(eventTimesRequestDto, eventTimesId));
	}

	@DeleteMapping("/times/{eventTimesId}")
	public ResponseEntity<EventTimesResponseDto> deleteEventTimes(@PathVariable Long eventTimesId) {
		return ResponseEntity.ok(eventTimesService.deleteEventTimes(eventTimesId));
	}
}