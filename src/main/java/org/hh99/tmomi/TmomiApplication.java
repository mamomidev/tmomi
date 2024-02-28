package org.hh99.tmomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = {"org.hh99.tmomi.domain.event", "org.hh99.tmomi.domain.stage",
	"org.hh99.tmomi.domain.ticket", "org.hh99.tmomi.domain.user"})
@SpringBootApplication
public class TmomiApplication {
	public static void main(String[] args) {

		SpringApplication.run(TmomiApplication.class, args);
	}

}