package org.hh99.tmomi.global.elasticsearch;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchItemsRepostiory extends ElasticsearchRepository<ElasticSearchItems, Long> {
}
