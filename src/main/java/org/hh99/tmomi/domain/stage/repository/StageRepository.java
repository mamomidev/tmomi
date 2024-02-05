package org.hh99.tmomi.domain.stage.repository;

import java.util.List;

import org.hh99.tmomi.domain.stage.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StageRepository extends JpaRepository<Stage, Long> {
	List<Stage> findByAddressContaining(String address);
}
