package org.hh99.tmomi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@EnableJpaRepositories(basePackages = {"org.hh99.tmomi.domain.event", "org.hh99.tmomi.domain.stage",
	"org.hh99.tmomi.domain.ticket", "org.hh99.tmomi.domain.user"})
@EnableRedisRepositories(basePackages = "org.hh99.tmomi.global.redis")
@EnableElasticsearchRepositories(basePackages = "org.hh99.tmomi.domain.reservation")
@SpringBootApplication
public class TmomiApplication {
	public static void main(String[] args) {

		SpringApplication.run(TmomiApplication.class, args);
	}

}