package org.hh99.tmomi.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchConfiguration;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;

@Configuration
@EnableElasticsearchRepositories(basePackages = "org.hh99.tmomi.domain.reservation")
public class ElasticSearchConfig extends ElasticsearchConfiguration {

	@Value("${spring.data.elasticsearch.server}")
	private String elasticSearchServer;

	@Override
	public ClientConfiguration clientConfiguration() {
		return ClientConfiguration.builder()
			.connectedTo(elasticSearchServer)
			.build();
	}
}
