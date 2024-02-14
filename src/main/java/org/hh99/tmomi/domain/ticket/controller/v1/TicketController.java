package org.hh99.tmomi.domain.ticket.controller.v1;

import org.hh99.tmomi.domain.reservation.dto.ReservationRequestDto;
import org.hh99.tmomi.domain.reservation.dto.ReservationResponseDto;
import org.hh99.tmomi.domain.ticket.dto.TicketRequestDto;
import org.hh99.tmomi.domain.ticket.dto.TicketResponseDto;
import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

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

	@GetMapping("/events/{eventId}/times/{eventTimeId}/book")
	public ResponseEntity<List<ReservationResponseDto>> getReservationList(@PathVariable Long eventId, @PathVariable Long eventTimeId) {
		return ResponseEntity.ok(ticketService.getReservationList(eventTimeId));
	}

	@PostMapping("/seats")
	public ResponseEntity<Void> lockSeat(@RequestBody ReservationRequestDto reservationRequestDto) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = null;
        if (authentication != null && authentication.isAuthenticated()) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            userEmail = userDetails.getUsername();
        }
        ticketService.lockSeat(reservationRequestDto, userEmail);

        return ResponseEntity.ok().build();
    }
}
