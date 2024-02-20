package org.hh99.tmomi.domain.stage.service

import org.hh99.tmomi.domain.stage.repository.SeatRepository
import spock.lang.Specification

class SeatServiceTest extends Specification {
    SeatService service;
    SeatRepository seatRepository = Mock(SeatRepository);

    def setup() {
        service = new SeatService(seatRepository)
    }


}