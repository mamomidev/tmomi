package org.hh99.tmomi.domain.reservation.respository;

import java.util.List;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByEventTimesIdAndStatus(Long eventTimesId, Status status);
}
