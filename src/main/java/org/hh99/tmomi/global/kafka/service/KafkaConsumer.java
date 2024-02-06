package org.hh99.tmomi.global.kafka.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.listener.ConsumerSeekAware;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer implements ConsumerSeekAware {

	@KafkaListener(id = "Consumer1", topics = "Topic1", groupId = "1")
	public void listenGroupFoo(String message) {
		System.out.println("Message: " + message);
	}
}
