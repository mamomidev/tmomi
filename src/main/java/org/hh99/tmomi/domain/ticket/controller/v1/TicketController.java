package org.hh99.tmomi.domain.ticket.controller.v1;

import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
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

	@GetMapping("/events/{eventId}/times/{eventTimeId}/book")
	public ResponseEntity<TicketResponseDto> getBooks(@PathVariable Long eventId, @PathVariable Long eventTimeId) {
		// 비어있는 좌석들 List 조회
		// 좌석 이름, 좌석 번호
		return null;
	}

	@PostMapping("/seats")
	public ResponseEntity<TicketResponseDto> getSeats(@RequestBody TicketRequestDto ticketRequestDto) {
		return null;
	}

	@PostMapping("/payment")
	public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
		return ResponseEntity.ok(ticketService.createTicket(ticketRequestDto));
	}

	@PostMapping("/{ticketId}/refund")
	public ResponseEntity<TicketResponseDto> updateTicketRefund(@PathVariable Long ticketId) {
		return ResponseEntity.ok(ticketService.updateTicketRefund(ticketId));
	}

}
