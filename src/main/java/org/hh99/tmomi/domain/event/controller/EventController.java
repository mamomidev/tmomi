package org.hh99.tmomi.domain.event.controller;

import org.hh99.tmomi.domain.event.service.EventService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class EventController {

	private final EventService eventService;
}