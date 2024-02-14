package org.hh99.tmomi.domain.event.repository;

import java.util.List;

import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventTimesRepository extends JpaRepository<EventTimes, Long> {
	List<EventTimes> findByEventId(Long id);
}
