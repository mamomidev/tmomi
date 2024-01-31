package org.hh99.tmomi.domain.ticket.repository;

import org.hh99.tmomi.domain.ticket.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
