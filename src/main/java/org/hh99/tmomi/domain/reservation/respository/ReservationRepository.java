package org.hh99.tmomi.domain.reservation.respository;

import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByEventTimesId(Long eventTimesId);
}
