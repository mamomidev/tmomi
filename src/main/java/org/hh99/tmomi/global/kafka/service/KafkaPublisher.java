package org.hh99.tmomi.global.kafka.service;

import java.util.concurrent.CompletableFuture;

import org.hh99.tmomi.global.config.KafkaTopicConfig;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KafkaPublisher {

	private final KafkaTopicConfig kafkaTopicConfig;

	private final KafkaTemplate<String, String> kafkaTemplate;

	public void sendMessage(String message) {

		CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(kafkaTopicConfig.topic().name(),
			message);

		future.whenComplete((result, exception) -> {
			if (exception == null) {
				onSuccess(result, message);
			} else {
				onFailure(exception, message);
			}
		});

	}

	private void onFailure(Throwable exception, String message) {
		System.out.println("Error: " + exception.getMessage());
	}

	private void onSuccess(SendResult<String, String> result, String message) {
		System.out.println(
			"Sent message = [" + message + "] with offset =[" + result.getRecordMetadata().offset() + "]");
	}
}
