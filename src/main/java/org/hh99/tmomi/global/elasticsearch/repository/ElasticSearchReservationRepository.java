package org.hh99.tmomi.global.elasticsearch.repository;

import java.util.List;

import org.hh99.tmomi.global.elasticsearch.document.ElasticSearchReservation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ElasticSearchReservationRepository extends ElasticsearchRepository<ElasticSearchReservation, String> {
	List<ElasticSearchReservation> findByEventTimesId(Long eventTimeId);
}
