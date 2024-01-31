package org.hh99.tmomi.domain.stage.repository;

import java.util.List;

import org.hh99.tmomi.domain.stage.entity.Rank;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RankRepository extends JpaRepository<Rank, Long> {

	List<Rank> findByStageId(Long stageId);
}
