package org.hh99.tmomi.global.elasticsearch;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Document(indexName = "items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class ElasticSearchItems {
	@Id
	private Long id;

	private String name;

	private Integer userId;
}
