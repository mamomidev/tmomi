package org.hh99.tmomi.global.redis;

import org.hh99.tmomi.domain.reservation.entity.Reservation;
import org.springframework.data.repository.CrudRepository;

public interface RedisReservationRepository extends CrudRepository<Reservation, Long> {
}
