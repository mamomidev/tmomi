package org.hh99.tmomi.domain.event.service

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesResponseDto
import org.hh99.tmomi.domain.event.entity.Event
import org.hh99.tmomi.domain.event.entity.EventTimes
import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.event.repository.EventTimesRepository
import org.hh99.tmomi.domain.reservation.document.ElasticSearchReservation
import org.hh99.tmomi.domain.reservation.respository.ElasticSearchReservationRepository
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.global.exception.GlobalException
import spock.lang.Specification

class EventTimesServiceTest extends Specification {
    EventTimesRepository eventTimesRepository = Mock()
    EventRepository eventRepository = Mock()
    SeatRepository seatRepository = Mock()
    ElasticSearchReservationRepository elasticSearchReservationRepository = Mock()
    EventTimesService service

    def setup() {
        service = new EventTimesService(eventTimesRepository,
                eventRepository,
                seatRepository,
                elasticSearchReservationRepository)
    }

    def "행사 시간 생성"() {
        given: "테스트에 필요한 데이터 설정"
        EventTimesRequestDto eventTimesRequestDto = new EventTimesRequestDto()
        Long eventId = 123L
        Event event = Mock() {
            getStage() >> Mock(Stage) {
                getId() >> 1L
            }
        }
        EventTimes eventTimes = new EventTimes(eventTimesRequestDto, event)
        List<Seat> seatList = Arrays.asList(Mock(Seat) {
            getSeatCapacity() >> 10L
        })

        List<ElasticSearchReservation> elasticSearchReservationList = new ArrayList<>()

        and: "Mock 객체의 동작 정의"
        eventRepository.findById(_ as Long) >> Optional.of(event)
        eventTimesRepository.save(_) >> eventTimes
        seatRepository.findByStageId(_ as Long) >> seatList
        elasticSearchReservationRepository.saveAll(_) >> _
        elasticSearchReservationList.clear()

        when: "메서드 호출"
        service.createEventTimes(eventTimesRequestDto, eventId)

        then: "예외가 발생하지 않아야 함"
        noExceptionThrown()
    }

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

        eventTimesRepository.delete(eventTimes) >> _

        when:
        def result = service.deleteEventTimes(eventTimeId)

        then:
        result instanceof EventTimesResponseDto
    }
}
