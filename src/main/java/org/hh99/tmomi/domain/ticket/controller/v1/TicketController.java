package org.hh99.tmomi.domain.ticket.controller.v1;

import org.hh99.tmomi.domain.reservation.dto.ElasticReservationRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
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
	public ResponseEntity<TicketResponseDto> createTicket(@RequestBody TicketRequestDto ticketRequestDto,
		@AuthenticationPrincipal
		UserDetails userDetails) {
		return ResponseEntity.ok(ticketService.createTicket(ticketRequestDto, userDetails.getUsername()));
	}

	@DeleteMapping("/{ticketId}/refund")
	public ResponseEntity<TicketResponseDto> refundTicket(@PathVariable Long ticketId) {
		ticketService.refundTicket(ticketId);
		return ResponseEntity.ok().build();
	}

	@PostMapping("/seats")
	public ResponseEntity<Void> lockSeat(@RequestBody ElasticReservationRequestDto elasticReservationRequestDto,
		@AuthenticationPrincipal
		UserDetails userDetails) throws
		InterruptedException {
		ticketService.updateReservationStatusWithLocked(elasticReservationRequestDto, userDetails.getUsername());
		return ResponseEntity.ok().build();
	}
}
