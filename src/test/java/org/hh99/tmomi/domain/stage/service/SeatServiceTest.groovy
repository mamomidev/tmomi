package org.hh99.tmomi.domain.stage.service

import org.hh99.tmomi.domain.stage.dto.seat.SeatRequestDto
import org.hh99.tmomi.domain.stage.dto.seat.SeatResponseDto
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.domain.stage.repository.StageRepository
import org.springframework.util.CollectionUtils
import spock.lang.Specification

class SeatServiceTest extends Specification {
    SeatService service;
    StageRepository stageRepository = Mock(StageRepository);
    SeatRepository seatRepository = Mock(SeatRepository);

    def setup() {
        service = new SeatService(stageRepository, seatRepository)
    }

    def "공연장 아이디로 좌석 조회"() {
        given:
        def stageId = 1L
        seatRepository.findByStageId(stageId) >> Arrays.asList(Mock(Seat))

        when:
        service.getSeatListByStageId(stageId)

        then:
        CollectionUtils.isEmpty(service.getSeatListByStageId(stageId)) == false
    }

    def "좌석 생성"() {
        given:
        def seatRequestDto = Mock(SeatRequestDto)
        stageRepository.findById(seatRequestDto.getStageId()) >> Optional.of(Mock(Stage))
        seatRepository.save(_ as Seat) >> Mock(Seat)

        when:
        def result = service.createSeat(seatRequestDto)

        then:
        result instanceof SeatResponseDto
    }

    def "좌석 수정"() {
        given:
        def seatId = 1L
        def seatRequestDto = Mock(SeatRequestDto)
        seatRepository.findById(seatId) >> Optional.of(Mock(Seat))
        Mock(Seat).updateNameAndCapacity(seatRequestDto)

        when:
        def result = service.updateSeat(seatId, seatRequestDto)

        then:
        result instanceof SeatResponseDto
    }

    def "좌석 삭제"() {
        given:
        def seatId = 1L
        seatRepository.findById(seatId) >> Optional.of(Mock(Seat))
        seatRepository.deleteById(seatId)

        when:
        def result = service.deleteSeat(seatId)

        then:
        result instanceof SeatResponseDto
    }
}