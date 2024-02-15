package org.hh99.tmomi.global.redis;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Component;

@Component
public class RedisKeyExpiredListener extends KeyExpirationEventMessageListener {

	public RedisKeyExpiredListener(RedisMessageListenerContainer listenerContainer) {
		super(listenerContainer);
	}

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println("########## onMessage pattern " + new String(pattern) + " | " + message.toString());
	}
}