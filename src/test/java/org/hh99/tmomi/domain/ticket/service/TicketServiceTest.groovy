package org.hh99.tmomi.domain.ticket.service

import org.hh99.tmomi.domain.event.entity.Event
import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.reservation.Status
import org.hh99.tmomi.domain.reservation.document.ElasticSearchReservation
import org.hh99.tmomi.domain.reservation.dto.ElasticReservationRequestDto
import org.hh99.tmomi.domain.reservation.respository.ElasticSearchReservationRepository
import org.hh99.tmomi.domain.stage.entity.Seat
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto
import org.hh99.tmomi.domain.ticket.entity.Ticket
import org.hh99.tmomi.domain.ticket.repository.TicketRepository
import org.hh99.tmomi.domain.user.entity.User
import org.hh99.tmomi.domain.user.repository.UserRepository
import org.hh99.tmomi.global.exception.GlobalException
import org.hh99.tmomi.global.redis.SeatValidate
import org.hh99.tmomi.global.redis.SeatValidateRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate
import org.springframework.util.CollectionUtils
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class TicketServiceTest extends Specification {
    TicketService service
    TicketRepository ticketRepository = Mock(TicketRepository)
    UserRepository userRepository = Mock(UserRepository)
    EventRepository eventRepository = Mock(EventRepository)
    SeatRepository seatRepository = Mock(SeatRepository)

    ElasticSearchReservationRepository elasticSearchReservationRepository = Mock(ElasticSearchReservationRepository)
    ElasticsearchTemplate elasticsearchTemplate = Mock(ElasticsearchTemplate)
    RedissonClient redissonClient = Mock(RedissonClient)
    SeatValidateRepository seatValidateRepository = Mock(SeatValidateRepository)

    def setup() {
        service = new TicketService(ticketRepository, userRepository, eventRepository, seatRepository,
                elasticSearchReservationRepository, elasticsearchTemplate, redissonClient, seatValidateRepository)
    }

    def "티켓 생성"() {
        given:
        TicketRequestDto ticketRequestDto = new TicketRequestDto()
        String userEmail = "test@example.com"
        SeatValidate seatValidate = Mock() {
            getEmail() >> userEmail
        }
        ElasticSearchReservation elasticSearchReservation = Mock() {
            getEventId() >> 1L
            getSeatId() >> 1L
            updateStatus(Status.PURCHASE) >> null
        }
        User user = Mock() {
            getId() >> 1L
        }
        Event event = Mock() {
            getEventName() >> "eventName"
        }
        Seat seat = Mock() {
            getSeatName() >> "seatName"
        }
        Ticket ticket = new Ticket(elasticSearchReservation, user.getId())

        and:
        seatValidateRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(seatValidate)
        elasticSearchReservationRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(elasticSearchReservation)
        userRepository.findByEmail(userEmail) >> Optional.of(user)
        ticketRepository.findByReservationId(ticketRequestDto.getReservationId()) >> null
        eventRepository.findById(elasticSearchReservation.getEventId()) >> Optional.of(event)
        seatRepository.findById(elasticSearchReservation.getSeatId()) >> Optional.of(seat)
        ticketRepository.save(_) >> ticket

        when:
        def result = service.createTicket(ticketRequestDto, userEmail)

        then:
        result instanceof TicketResponseDto
    }

    def "티켓 생성 시 유효하지 않은 사용자가 구매 요청"() {
        given:
        TicketRequestDto ticketRequestDto = new TicketRequestDto()
        String userEmail = "test@example.com"
        SeatValidate seatValidate = Mock() {
            getEmail() >> "different@example.com" // userEmail과 다른 이메일
        }

        and:
        seatValidateRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(seatValidate)

        when:
        service.createTicket(ticketRequestDto, userEmail)

        then:
        def e = thrown(GlobalException)
        e.httpStatus == e.httpStatus.LOCKED
        e.exceptionCode == e.exceptionCode.NOT_SELECT_LOCKED
    }

    def "이미 구매된 티켓을 구매요청할 경우"() {
        given:
        TicketRequestDto ticketRequestDto = new TicketRequestDto()
        String userEmail = "test@example.com"
        SeatValidate seatValidate = Mock() {
            getEmail() >> userEmail
        }
        ElasticSearchReservation elasticSearchReservation = Mock() {
            getEventId() >> 1L
            getSeatId() >> 1L
        }
        User user = Mock() {
            getId() >> 1L
        }
        Ticket ticket = Mock() {
            getStatus() >> Status.PURCHASE // 티켓이 이미 구매된 상태
        }

        and:
        seatValidateRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(seatValidate)
        elasticSearchReservationRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(elasticSearchReservation)
        userRepository.findByEmail(userEmail) >> Optional.of(user)
        ticketRepository.findByReservationId(ticketRequestDto.getReservationId()) >> ticket

        when:
        service.createTicket(ticketRequestDto, userEmail)

        then:
        def e = thrown(GlobalException)
        e.httpStatus == e.httpStatus.BAD_REQUEST
        e.exceptionCode == e.exceptionCode.PURCHASED_TICKET
    }

    def "티켓 환불"()  {
        given: "테스트에 필요한 데이터 설정"
        Long ticketId = 1L
        Ticket ticket = Mock() {
            getReservationId() >> 1L
            updateStatus(Status.REFUND) >> null
        }
        ElasticSearchReservation elasticSearchReservation = Mock() {
            updateStatus(Status.NONE) >> null
        }

        and: "Mock 객체의 동작 정의"
        ticketRepository.findById(ticketId) >> Optional.of(ticket)
        elasticSearchReservationRepository.findById(ticket.getReservationId()) >> Optional.of(elasticSearchReservation)

        when: "메서드 호출"
        service.refundTicket(ticketId)

        then: "예외가 발생하지 않아야 함"
        noExceptionThrown()

        and: "상태가 업데이트되어야 함"
        1 * ticket.updateStatus(Status.REFUND)
        1 * elasticSearchReservation.updateStatus(Status.NONE)
    }

    def "좌석 선택 요청 시 락 획득 후, 상태 변경"() {
        given: "테스트에 필요한 데이터 설정"
        ElasticReservationRequestDto elasticReservationRequestDto = new ElasticReservationRequestDto()
        String email = "test@example.com"
        ElasticSearchReservation elasticSearchReservation = Mock() {
            getStatus() >> Status.NONE
            updateStatus(Status.RESERVATION) >> null
        }
        RLock rLock = Mock() {
            tryLock(0L, 180L, TimeUnit.SECONDS) >> true
        }

        and: "Mock 객체의 동작 정의"
        redissonClient.getLock(_) >> rLock
        elasticSearchReservationRepository.findById(elasticReservationRequestDto.getReservationId()) >> Optional.of(elasticSearchReservation)
        seatValidateRepository.save(_) >> null

        when: "메서드 호출"
        service.updateReservationStatusWithLocked(elasticReservationRequestDto, email)

        then: "예외가 발생하지 않아야 함"
        noExceptionThrown()

        and: "상태가 업데이트되어야 함"
        1 * elasticSearchReservation.updateStatus(Status.RESERVATION)
    }

    def "좌석 선택 요청 시 다른 사용자가 락을 획득한 상태일 때"() {
        given: "테스트에 필요한 데이터 설정"
        ElasticReservationRequestDto elasticReservationRequestDto = new ElasticReservationRequestDto()
        String email = "test@example.com"
        RLock rLock = Mock() {
            tryLock(0L, 180L, TimeUnit.SECONDS) >> false // 락 획득 실패
        }

        and: "Mock 객체의 동작 정의"
        redissonClient.getLock(_) >> rLock

        when: "메서드 호출"
        service.updateReservationStatusWithLocked(elasticReservationRequestDto, email)

        then: "예외가 발생해야 함"
        def e = thrown(GlobalException)
        e.httpStatus == e.httpStatus.LOCKED
        e.exceptionCode == e.exceptionCode.LOCKED
    }

    def "좌석 선택 요청 시 잘못된 요청이 들어왔을 때, 좌석의 상태 검사"() {
        given: "테스트에 필요한 데이터 설정"
        ElasticReservationRequestDto elasticReservationRequestDto = new ElasticReservationRequestDto()
        String email = "test@example.com"
        ElasticSearchReservation elasticSearchReservation = Mock() {
            getStatus() >> Status.RESERVATION // 상태가 NONE이 아님
        }
        RLock rLock = Mock() {
            tryLock(0L, 180L, TimeUnit.SECONDS) >> true
        }

        and: "Mock 객체의 동작 정의"
        redissonClient.getLock(_) >> rLock
        elasticSearchReservationRepository.findById(elasticReservationRequestDto.getReservationId()) >> Optional.of(elasticSearchReservation)

        when: "메서드 호출"
        service.updateReservationStatusWithLocked(elasticReservationRequestDto, email)

        then: "예외가 발생해야 함"
        def e = thrown(GlobalException)
        e.httpStatus == e.httpStatus.LOCKED
        e.exceptionCode == e.exceptionCode.LOCKED
    }

    def "사용자가 락 획득 후 구매하지 않을 경우, 락 해제 됐을 때 상태 변경"() {
        given: "테스트에 필요한 데이터 설정"
        String key = "seat_lock:1" // key의 형태가 "seat_lock:{id}"라고 가정
        ElasticSearchReservation elasticSearchReservation = Mock() {
            getStatus() >> Status.RESERVATION // 상태가 PURCHASE가 아님
            updateStatus(Status.NONE) >> null
        }

        and: "Mock 객체의 동작 정의"
        elasticSearchReservationRepository.findById("1") >> Optional.of(elasticSearchReservation)

        when: "메서드 호출"
        service.updateReservationStatusWithUnLocked(key)

        then: "예외가 발생하지 않아야 함"
        noExceptionThrown()

        and: "상태가 업데이트되어야 함"
        1 * elasticSearchReservation.updateStatus(Status.NONE)
    }

    def "사용자의 티켓 목록 가져오기"() {
        given: "테스트에 필요한 데이터 설정"
        String email = "test@example.com"
        Long userId = 1L
        User user = Mock() {
            getId() >> userId
        }
        Ticket ticket1 = new Ticket()
        Ticket ticket2 = new Ticket()
        List<Ticket> tickets = [ticket1, ticket2]
        Event event = Mock() {
            getEventName() >> "Event Name"
        }
        Seat seat = Mock() {
            getSeatName() >> "Seat Name"
        }

        and: "Mock 객체의 동작 정의"
        userRepository.findByEmail(email) >> Optional.of(user)
        ticketRepository.findAllByUserId(userId) >> tickets
        eventRepository.findById(_) >> Optional.of(event)
        seatRepository.findById(_) >> Optional.of(seat)

        when: "메서드 호출"
        List<TicketResponseDto> result = service.getMyTicketList(email)

        then: "예외가 발생하지 않아야 함"
        noExceptionThrown()

        and: "올바른 티켓 목록이 반환되어야 함"
        result.size() == 2
        result.every { it.email == email && it.eventName == event.getEventName() && it.seatName == seat.getSeatName() }
    }
}