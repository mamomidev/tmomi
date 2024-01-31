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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityNotFoundException;
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
			.orElseThrow(() -> new EntityNotFoundException());
		Event event = eventRepository.findById(ticketRequestDto.getEventId())
			.orElseThrow(() -> new EntityNotFoundException());
		EventTimes eventTimes = eventTimesRepository.findById(ticketRequestDto.getEventTimesId())
			.orElseThrow(() -> new EntityNotFoundException());
		Seat seat = seatRepository.findById(ticketRequestDto.getSeatId())
			.orElseThrow(() -> new EntityNotFoundException());
		Ticket ticket = new Ticket(ticketRequestDto, user, event, eventTimes, seat);

		return new TicketResponseDto(ticketRepository.save(ticket));
	}

	@Transactional
	public TicketResponseDto updateTicket(Long ticketId, TicketRequestDto ticketRequestDto) {
		Ticket ticket = ticketRepository.findById(ticketId).orElseThrow(() -> new EntityNotFoundException());
		ticket.update(ticketRequestDto);

		return new TicketResponseDto(ticket);
	}

}
