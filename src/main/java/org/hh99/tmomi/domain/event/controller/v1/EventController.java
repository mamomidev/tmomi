package org.hh99.tmomi.domain.event.controller.v1;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto;
import org.hh99.tmomi.domain.event.service.EventService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class EventController {

	private final EventService eventService;

	@PostMapping("/admin/events")
	public ResponseEntity<EventResponseDto> createEvent(EventRequestDto eventRequestDto) {
		return ResponseEntity.ok(eventService.createEvent(eventRequestDto));
	}

	@PutMapping("/admin/events/{eventId}")
	public ResponseEntity<EventResponseDto> updateEvent(EventRequestDto eventRequestDto, @PathVariable Long eventId) {
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