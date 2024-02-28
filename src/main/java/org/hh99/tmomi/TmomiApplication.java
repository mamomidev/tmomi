package org.hh99.tmomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
@SpringBootApplication
public class TmomiApplication {
	public static void main(String[] args) {

		SpringApplication.run(TmomiApplication.class, args);
	}

}