package org.hh99.tmomi.domain.ticket.service

import org.hh99.tmomi.domain.reservation.Status
import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto
import org.hh99.tmomi.domain.reservation.entity.Reservation
import org.hh99.tmomi.domain.reservation.respository.ReservationRepository
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto
import org.hh99.tmomi.domain.ticket.entity.Ticket
import org.hh99.tmomi.domain.ticket.repository.TicketRepository
import org.redisson.api.RLock
import org.redisson.api.RedissonClient
import org.springframework.util.CollectionUtils
import spock.lang.Specification

import java.util.concurrent.TimeUnit

class TicketServiceTest extends Specification {
    TicketService service
    TicketRepository ticketRepository = Mock(TicketRepository)
    ReservationRepository reservationRepository = Mock(ReservationRepository)
    RedissonClient redissonClient = Mock(RedissonClient)

    def setup() {
        service = new TicketService(ticketRepository, reservationRepository, redissonClient)
    }

    def "createTicket"() {
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

    def "deleteTicket"() {
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

    def "getReservationList"() {
        given:
        def eventTimeId = 1L
        reservationRepository.findAllByEventTimesIdAndStatus(eventTimeId, Status.NONE) >> Arrays.asList(Mock(Reservation))

        when:
        def result = service.getReservationList(eventTimeId)

        then:
        CollectionUtils.isEmpty(result) == false
    }

    def "updateReservationStatusWithLocked"() {
        given:
        def reservationRequestDto = Mock(ReservationRequestDto)
        def lockName = "seat_lock:" + reservationRequestDto.getId()
        def rLock = Mock(RLock) {
            tryLock(_, _, _) >> GroovyMock(Boolean)
        }

        reservationRepository.findById(reservationRequestDto.getId()) >> Optional.of(Mock(Reservation))

        def waitTime = 0L
        def leaseTime = 180L
        rLock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)
        Mock(Reservation).updateStatus(Status.RESERVATION)

        when:
        service.updateReservationStatusWithLocked(reservationRequestDto)

        then:
        noExceptionThrown()
    }
}