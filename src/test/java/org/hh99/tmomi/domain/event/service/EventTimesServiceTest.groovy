package org.hh99.tmomi.domain.event.service

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto
import org.hh99.tmomi.domain.event.entity.Event
import org.hh99.tmomi.domain.event.entity.EventTimes
import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.event.repository.EventTimesRepository
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.global.exception.GlobalException
import spock.lang.Specification
import spock.lang.Unroll

class EventTimesServiceTest extends Specification {
    EventTimesRepository eventTimesRepository = Mock()
    EventRepository eventRepository = Mock()
    SeatRepository seatRepository = Mock()
    ReservationRepository reservationRepository = Mock()
    EventTimesService service

    def setup() {
        service = new EventTimesService(eventTimesRepository,
                eventRepository,
                seatRepository,
                reservationRepository)
    }

    @Unroll
    def "행사 시간 생성"() {
        given:
        def eventTimesRequestDto = Mock(EventTimesRequestDto)
        def eventId = 123L
        eventRepository.findById(*_) >> _optionalEvent
        eventTimesRepository.save(_) >> _
        1 * seatRepository.findByStageId(_optionalEvent.get().getStage().getId()) >> Arrays.asList(Mock(Seat) {
            getSeatCapacity() >> 2
        })
        reservationRepository.saveAll(_) >> _

        when:
        service.createEventTimes(eventTimesRequestDto, eventId)

        then:
        noExceptionThrown()

        where:
        _optionalEvent          || _expectResult
        mockOptionalEvent(123L) || true
        mockOptionalEvent(124L) || true
        mockOptionalEvent(125L) || true
    }


    @Unroll
    def "행사 시간 생성시 행사 없을 시 에러"() {
        given:
        def eventTimesRequestDto = Mock(EventTimesRequestDto)
        def eventId = 123L
        eventRepository.findById(*_) >> _optionalEvent

        when:
        service.createEventTimes(eventTimesRequestDto, eventId)

        then:
        thrown(GlobalException)

        where:
        _optionalEvent   || _expectResult
        Optional.empty() || true
    }

    Optional<Event> mockOptionalEvent(Long id) {
        return Optional.of(Mock(Event) {
            getStage() >> Mock(Stage) {
                getId() >> id
            }
        })
    }

    def "행사 시간 수정"() {
        given:
        def eventTimesRequestDto = Mock(EventTimesRequestDto)
        def eventTimeId = 1L

        eventTimesRepository.findById(eventTimeId) >> Optional.of(Mock(EventTimes) {
            getId() >> 1L
        })

        when:
        def result = service.updateEventTimes(eventTimesRequestDto, eventTimeId)

        then:
        result instanceof EventTimesResponseDto
    }

    def "행사 시간 삭제"() {
        given:
        def eventTimeId = 1L
        def eventTimes = Mock(EventTimes)
        eventTimesRepository.findById(eventTimeId) >> Optional.of(Mock(EventTimes) {
            getId() >> 1L
        })
        eventTimesRepository.delete(eventTimes)
        reservationRepository.deleteAllByEventTimesId(eventTimeId)

        when:
        def result = service.deleteEventTimes(eventTimeId)

        then:
        result instanceof EventTimesResponseDto
    }
}
