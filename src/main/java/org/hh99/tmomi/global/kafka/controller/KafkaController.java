package org.hh99.tmomi.global.kafka.controller;

import org.hh99.tmomi.global.kafka.service.KafkaPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class KafkaController {

	private final KafkaPublisher kafkaPublisher;

	@GetMapping(value = "/producer")
	public String sendMessage(@RequestParam("message") String message) {
		kafkaPublisher.sendMessage(message);
		return "Message sent Success";
	}
}
