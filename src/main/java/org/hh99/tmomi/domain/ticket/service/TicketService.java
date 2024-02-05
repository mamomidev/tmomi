package org.hh99.tmomi.domain.ticket.service;

import org.hh99.tmomi.domain.event.entity.Event;
import org.hh99.tmomi.domain.event.entity.EventTimes;
import org.hh99.tmomi.domain.event.repository.EventRepository;
import org.hh99.tmomi.domain.event.repository.EventTimesRepository;
import org.hh99.tmomi.domain.stage.entity.Seat;
import org.hh99.tmomi.domain.stage.repository.SeatRepository;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.entity.Ticket;
import org.hh99.tmomi.domain.ticket.repository.TicketRepository;
import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.domain.user.repository.UserRepository;
import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TicketService {

	private final UserRepository userRepository;
	private final EventRepository eventRepository;
	private final EventTimesRepository eventTimesRepository;
	private final SeatRepository seatRepository;
	private final TicketRepository ticketRepository;

	@Transactional
	public TicketResponseDto createTicket(TicketRequestDto ticketRequestDto) {
		User user = userRepository.findById(ticketRequestDto.getUserId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_USER));
		Event event = eventRepository.findById(ticketRequestDto.getEventId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT));
		EventTimes eventTimes = eventTimesRepository.findById(ticketRequestDto.getEventTimesId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_EVENT_TIME));
		Seat seat = seatRepository.findById(ticketRequestDto.getSeatId())
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_SEAT));

		Ticket ticket = new Ticket(ticketRequestDto, user, event, eventTimes, seat);

		return new TicketResponseDto(ticketRepository.save(ticket));
	}

	@Transactional
	public TicketResponseDto updateTicketRefund(Long ticketId) {
		Ticket ticket = ticketRepository.findById(ticketId)
			.orElseThrow(() -> new GlobalException(HttpStatus.NOT_FOUND, ExceptionCode.NOT_EXIST_TICKET));
		ticket.refund();

		return new TicketResponseDto(ticket);
	}

}
