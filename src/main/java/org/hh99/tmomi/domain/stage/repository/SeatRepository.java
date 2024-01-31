package org.hh99.tmomi.domain.stage.repository;

import java.util.List;

import org.hh99.tmomi.domain.stage.entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
	List<Seat> findByStageId(Long stageId);
}
