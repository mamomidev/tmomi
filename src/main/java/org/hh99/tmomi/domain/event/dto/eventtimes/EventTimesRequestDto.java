package org.hh99.tmomi.domain.event.dto.eventtimes;

import java.time.LocalDateTime;

import lombok.Getter;

@Getter
public class EventTimesRequestDto {
	private long eventId;

	private LocalDateTime eventTime;
}
