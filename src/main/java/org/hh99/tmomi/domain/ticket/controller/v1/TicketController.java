package org.hh99.tmomi.domain.ticket.controller.v1;

import java.util.List;

import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto;
import org.hh99.tmomi.domain.reservation.dto.ReservationResponseDto;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/tickets")
public class TicketController {

	private final TicketService ticketService;

	@PostMapping("/payment")
	public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
		return ResponseEntity.ok(ticketService.createTicket(ticketRequestDto));
	}

	@DeleteMapping("/{ticketId}/refund")
	public ResponseEntity<TicketResponseDto> deleteTicket(@PathVariable Long ticketId) {
		ticketService.deleteTicket(ticketId);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/events/times/{eventTimeId}/book")
	public ResponseEntity<List<ReservationResponseDto>> getReservationList(@PathVariable Long eventTimeId) {
		return ResponseEntity.ok(ticketService.getReservationList(eventTimeId));
	}

	@PostMapping("/seats")
	public ResponseEntity<Void> lockSeat(@RequestBody ReservationRequestDto reservationRequestDto) throws InterruptedException {

        ticketService.lockSeat(reservationRequestDto);

        return ResponseEntity.ok().build();
    }
}
