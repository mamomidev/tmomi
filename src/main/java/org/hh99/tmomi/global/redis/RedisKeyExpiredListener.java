package org.hh99.tmomi.global.redis;

import org.hh99.tmomi.domain.ticket.service.TicketService;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

	private final TicketService ticketService;
	public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer, TicketService ticketService) {
		super(listenerContainer);
        this.ticketService = ticketService;
    }

	@Override
	public void onMessage(Message message, byte[] pattern) {
		ticketService.unlockSeat(message.toString());
	}
}