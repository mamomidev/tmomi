package org.hh99.tmomi.domain.event.repository;

import java.util.List;

import org.hh99.tmomi.domain.event.entity.Event;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
	List<Event> findAllByEventNameContaining(String query);
}
