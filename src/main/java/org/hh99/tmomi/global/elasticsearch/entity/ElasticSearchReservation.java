package org.hh99.tmomi.global.elasticsearch.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(indexName = "items")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ElasticSearchReservation {
	@Id
	private Long id;

	private String name;

	private Integer userId;
}
