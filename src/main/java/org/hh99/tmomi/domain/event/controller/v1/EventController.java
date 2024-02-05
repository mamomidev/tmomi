package org.hh99.tmomi.domain.event.controller.v1;

import java.util.List;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto;
import org.hh99.tmomi.domain.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class EventController {

	private final EventService eventService;

	@PostMapping("/admin/events")
	public ResponseEntity<EventResponseDto> createEvent(@RequestBody EventRequestDto eventRequestDto) {
		return ResponseEntity.ok(eventService.createEvent(eventRequestDto));
	}

	@PutMapping("/admin/events/{eventId}")
	public ResponseEntity<EventResponseDto> updateEvent(@RequestBody EventRequestDto eventRequestDto,
		@PathVariable Long eventId) {
		return ResponseEntity.ok(eventService.updateEvent(eventRequestDto, eventId));
	}

	@DeleteMapping("/admin/events/{eventId}")
	public ResponseEntity<EventResponseDto> deleteEvent(@PathVariable Long eventId) {
		return ResponseEntity.ok(eventService.deleteEvent(eventId));
	}

	@GetMapping("/events/{eventId}")
	public ResponseEntity<EventResponseDto> getEvent(@PathVariable Long eventId) {
		return ResponseEntity.ok(eventService.getEvent(eventId));
	}

	@GetMapping("/search")
	public ResponseEntity<List<EventResponseDto>> getEventListByEventName(@RequestParam String query) {
		return ResponseEntity.ok(eventService.getEventListByEventName(query));
	}
}