package org.hh99.tmomi.global.redis;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "seat_validate", timeToLive = 180)
public class SeatValidate {

	@Id
	private String reservationId;

	private String email;
}