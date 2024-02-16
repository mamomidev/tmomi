package org.hh99.tmomi;

import org.hh99.tmomi.global.config.KafkaAdminConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = TmomiApplication.class)
@ActiveProfiles("test")
@AutoConfigureMockMvc
class TmomiApplicationTests {

	@Autowired
	private MockMvc mvc;

	@Autowired
	private KafkaAdminConfig kafkaTopicConfig;
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	@Test
	void contextLoads() {
	}

	@Test
	public void 카프카_전송() {
		kafkaTemplate.send("Topic2", "메세지 전송 테스트");
	}

	@KafkaListener(id = "Consumer3", topics = "Topic2", groupId = "1")
	public void 카프카_응답(String message) {
		System.out.println("Message: " + message);
	}
}
