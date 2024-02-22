package org.hh99.tmomi.global.elasticsearch.repository;

import org.hh99.tmomi.global.elasticsearch.document.ElasticSearchReservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchReservationRepository extends ElasticsearchRepository<ElasticSearchReservation, String> {
}
