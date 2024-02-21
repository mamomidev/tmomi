package org.hh99.tmomi.domain.event.service

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto
import org.hh99.tmomi.domain.event.dto.event.EventResponseDto
import org.hh99.tmomi.domain.event.entity.Event
import org.hh99.tmomi.domain.event.entity.EventTimes
import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.event.repository.EventTimesRepository
import org.hh99.tmomi.domain.stage.entity.Stage
import org.hh99.tmomi.domain.stage.repository.StageRepository
import org.hh99.tmomi.global.exception.GlobalException
import spock.lang.Specification

class EventServiceTest extends Specification {

    EventRepository eventRepository = Mock(EventRepository)
    StageRepository stageRepository = Mock(StageRepository)
    EventTimesRepository eventTimesRepository = Mock(EventTimesRepository)
    EventService eventService

    def setup() {
        eventService = new EventService(eventRepository,
            stageRepository,
            eventTimesRepository)
    }

    def "행사 생성 테스트"() {
        given:
        def eventRequestDto = new EventRequestDto(stageId: 1, eventName: "Test Event")
        def stage = Mock(Stage)
        def optionalStage = Optional.of(stage)

        stageRepository.findById(eventRequestDto.getStageId()) >> optionalStage

        def event = new Event(eventRequestDto, stage)

        eventRepository.save(_ as Event) >> event
        stage.getAddress() >> "Stage Address"

        when:
        def result = eventService.createEvent(eventRequestDto)

        then:
        result instanceof EventResponseDto
        result.eventName == "Test Event"
        result.stageAddress == "Stage Address"
    }

    def "행사 생성시 없는 행사장 아이디로 조회 시 에러"() {
        given:
        def eventRequestDto = new EventRequestDto(stageId: 999, eventName: "Test Event")
        stageRepository.findById(_ as Long) >> _

        when:
        eventService.createEvent(eventRequestDto)

        then:
        thrown(GlobalException)
    }

    def "행사 수정 테스트"() {
        given:
        def eventRequestDto = Mock(EventRequestDto)
        def eventId = 1L

        eventRepository.findById(eventId) >> Optional.of(Mock(Event))
        stageRepository.findById(eventRequestDto.getStageId()) >> Optional.of(Mock(Stage))

        when:
        def result = eventService.updateEvent(eventRequestDto, eventId)

        then:
        result instanceof EventResponseDto
    }

    def "행사 삭제 테스트"() {
        given:
        def eventId = 1L
        def event = Mock(Event)
        eventRepository.findById(eventId) >> Optional.of(Mock(Event) {
            getStage() >> Mock(Stage) {
                getAddress() >> "인천"
            }
        })
        eventRepository.delete(event)

        when:
        def result = eventService.deleteEvent(eventId)

        then:
        result instanceof EventResponseDto
    }

    def "행사 조회 테스트"() {
        given:
        def eventId = 1L
        def stageId = 1L

        eventRepository.findById(eventId) >> Optional.of(Mock(Event))
        eventRepository.findById(eventId) >> Optional.of(Mock(Event) {
            getStage() >> Mock(Stage) {
                getAddress() >> "인천"
            }
        })
        eventTimesRepository.findByEventId(eventId) >> List<EventTimes>

        when:
        def result = eventService.getEvent(eventId)

        then:
        result instanceof EventResponseDto
    }
}
