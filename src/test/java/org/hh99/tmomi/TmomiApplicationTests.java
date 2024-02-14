package org.hh99.tmomi;

import org.hh99.tmomi.domain.stage.repository.StageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
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
	private StageRepository stageRepository;

	@Test
	void contextLoads() {
	}

	@Test
	public void test() {
		stageRepository.findAll().forEach(e -> System.out.println(e.getAddress()));
		assert equals("ListTest");
	}
}
