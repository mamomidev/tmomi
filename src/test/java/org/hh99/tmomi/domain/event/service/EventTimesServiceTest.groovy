package org.hh99.tmomi.domain.event.service

import org.apache.kafka.clients.admin.AdminClient
import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto
import org.hh99.tmomi.domain.event.entity.Event
import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.event.repository.EventTimesRepository
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.global.config.KafkaAdminConfig
import org.hh99.tmomi.global.exception.GlobalException
import spock.lang.Specification
import spock.lang.Unroll

class EventTimesServiceTest extends Specification {
    EventTimesRepository eventTimesRepository = Mock()
    EventRepository eventRepository = Mock()
    SeatRepository seatRepository = Mock()
    ReservationRepository reservationRepository = Mock()
    KafkaAdminConfig kafkaAdminConfig = Mock()
    EventTimesService service

    def setup() {
        service = new EventTimesService(eventTimesRepository,
                eventRepository,
                seatRepository,
                reservationRepository,
                kafkaAdminConfig)
    }

    @Unroll
    def "service.createEventTimes"() {
        given:
        // 현재 주어진 조건...
        def eventTimesRequestDto = Mock(EventTimesRequestDto)
        def eventId = 123L
        eventRepository.findById(*_) >> _optionalEvent
        eventTimesRepository.save(_) >> _
        1 * seatRepository.findByStageId(_optionalEvent.get().getStage().getId()) >> Arrays.asList(Mock(Seat) {
            getSeatCapacity() >> 2
        })
        reservationRepository.saveAll(_) >> _
        kafkaAdminConfig.kafkaAdmin() >> Mock(AdminClient)

        when:
        // 언제?
        service.createEventTimes(eventTimesRequestDto, eventId)

        then:
        // 결국 어떻게 되는지..
        noExceptionThrown()

        where:
        _optionalEvent          || _expectResult
        mockOptionalEvent(123L) || true
        mockOptionalEvent(124L) || true
        mockOptionalEvent(125L) || true
    }


    @Unroll
    def "service.createEventTimes - exception"() {
        given:
        // 현재 주어진 조건...
        def eventTimesRequestDto = Mock(EventTimesRequestDto)
        def eventId = 123L
        eventRepository.findById(*_) >> _optionalEvent

        when:
        // 언제?
        service.createEventTimes(eventTimesRequestDto, eventId)

        then:
        // 결국 어떻게 되는지..
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
}
