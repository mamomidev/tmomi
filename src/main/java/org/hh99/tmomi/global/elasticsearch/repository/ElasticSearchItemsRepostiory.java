package org.hh99.tmomi.global.elasticsearch.repository;

import org.hh99.tmomi.global.elasticsearch.entity.ElasticSearchReservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchItemsRepostiory extends ElasticsearchRepository<ElasticSearchReservation, Long> {
}
