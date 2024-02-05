package org.hh99.tmomi.domain.ticket.controller.v1;

import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class TicketController {

	private final TicketService ticketService;

	@PostMapping("/tickets")
	public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto) {
		return ResponseEntity.ok(ticketService.createTicket(ticketRequestDto));
	}

	@PostMapping("/tickets/{ticketId}/refund")
	public ResponseEntity<TicketResponseDto> updateTicketRefund(@PathVariable Long ticketId) {
		return ResponseEntity.ok(ticketService.updateTicketRefund(ticketId));
	}

}
