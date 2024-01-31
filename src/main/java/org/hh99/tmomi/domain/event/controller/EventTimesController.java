package org.hh99.tmomi.domain.event.controller;

import org.hh99.tmomi.domain.event.service.EventTimesService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin")
public class EventTimesController {

	private final EventTimesService eventTimesService;
}
