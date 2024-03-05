package org.hh99.tmomi.domain.ticket.service

import org.hh99.tmomi.domain.event.repository.EventRepository
import org.hh99.tmomi.domain.reservation.Status
import org.hh99.tmomi.domain.reservation.respository.ElasticSearchReservationRepository
import org.hh99.tmomi.domain.stage.repository.SeatRepository
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto
import org.hh99.tmomi.domain.ticket.entity.Ticket
import org.hh99.tmomi.domain.ticket.repository.TicketRepository
import org.hh99.tmomi.domain.user.repository.UserRepository
import org.hh99.tmomi.global.exception.GlobalException
import org.hh99.tmomi.global.redis.SeatValidateRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.util.CollectionUtils
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class TicketServiceTest extends Specification {
    TicketService service
    TicketRepository ticketRepository = Mock(TicketRepository)
    UserRepository userRepository = Mock(UserRepository)
    EventRepository eventRepository = Mock(EventRepository)
    SeatRepository seatRepository = Mock(SeatRepository)
    ElasticSearchReservationRepository reservationRepository = Mock(ElasticSearchReservationRepository)
    RedissonClient redissonClient = Mock(RedissonClient)
    SeatValidateRepository seatValidateRepository = Mock(SeatValidateRepository)

    def setup() {
        service = new TicketService(ticketRepository, userRepository, eventRepository, reservationRepository,
                seatRepository, redissonClient, seatValidateRepository)
    }

    def "티켓 생성"() {
        given:
        def ticketRequestDto = Mock(TicketRequestDto)
        reservationRepository.findById(ticketRequestDto.getReservationId()) >> Optional.of(Mock(Reservation))
        Mock(Reservation).updateStatus(Status.PURCHASE)
        ticketRepository.save(_ as Ticket) >> Mock(Ticket)

        when:
        def result = service.createTicket(ticketRequestDto)

        then:
        result instanceof TicketResponseDto
    }

    def "티켓 삭제"() {
        given:
        def ticketId = 1L
        ticketRepository.findById(ticketId) >> Optional.of(Mock(Ticket))
        reservationRepository.findById(Mock(Ticket).getReservationId()) >> Optional.of(Mock(Reservation))
        Mock(Reservation).updateStatus(Status.NONE)
        ticketRepository.delete(Mock(Ticket));

        when:
        service.deleteTicket(ticketId)

        then:
        noExceptionThrown()
    }

    def "예매 목록 조회"() {
        given:
        def eventTimeId = 1L
        reservationRepository.findAllByEventTimesIdAndStatus(eventTimeId, Status.NONE) >> Arrays.asList(Mock(Reservation))

        when:
        def result = service.getReservationList(eventTimeId)

        then:
        CollectionUtils.isEmpty(result) == false
    }

    def "예매 상태 및 락 업데이트"() {
        given:
        def reservationRequestDto = Mock(ReservationRequestDto)
        def lockName = "seat_lock:" + reservationRequestDto.getId()
        def rLock = Mock(RLock) {
            tryLock(_, _, _) >> true
        }
        redissonClient.getLock(lockName) >> rLock

        // reservationRepository Mock 설정
        reservationRepository.findById(reservationRequestDto.getId()) >> Optional.of(Mock(Reservation))

        def waitTime = 0L
        def leaseTime = 180L

        // rLock 초기화
        rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)

        Mock(Reservation).updateStatus(Status.RESERVATION)

        when:
        service.updateReservationStatusWithLocked(reservationRequestDto)

        then:
        noExceptionThrown()
    }

    def "예매 상태 및 락 업데이트시 에러"() {
        given:
        def reservationRequestDto = Mock(ReservationRequestDto)
        def lockName = "seat_lock:" + reservationRequestDto.getId()
        def rLock = Mock(RLock) {
            tryLock(_, _, _) >> false
        }
        redissonClient.getLock(lockName) >> rLock
        // reservationRepository Mock 설정
        reservationRepository.findById(reservationRequestDto.getId()) >> Optional.of(Mock(Reservation))
        def waitTime = 0L
        def leaseTime = 180L
        // rLock 초기화
        rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)
        when:
        service.updateReservationStatusWithLocked(reservationRequestDto)

        then:
        thrown(GlobalException)
    }

    def "예매 상태 및 락 해제 업데이트"() {
        given:
        def key = "redisKey:1"
        def id = 1L

        reservationRepository.findById(id) >> Optional.of(Mock(Reservation) {
            getStatus() >> Status.RESERVATION
        })

        Mock(Reservation).updateStatus(Status.NONE)

        when:
        service.updateReservationStatusWithUnLocked(key)

        then:
        noExceptionThrown()
    }
}