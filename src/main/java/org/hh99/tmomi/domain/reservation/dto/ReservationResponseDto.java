package org.hh99.tmomi.domain.reservation.dto;

import org.hh99.tmomi.domain.reservation.Status;
import org.hh99.tmomi.domain.reservation.entity.Reservation;

public class ReservationResponseDto {
    private Long id;
    private Long eventTimesId;

    private Long seatId;
    private Integer seatNumber;

    public ReservationResponseDto(Reservation reservation) {
        this.id = reservation.getId();
        this.eventTimesId = reservation.getEventTimesId();
        this.seatId = reservation.getSeatId();
        this.seatNumber = reservation.getSeatNumber();
    }
}
