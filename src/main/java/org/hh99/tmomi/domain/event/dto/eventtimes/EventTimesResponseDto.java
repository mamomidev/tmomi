package org.hh99.tmomi.domain.event.dto.eventtimes;

import java.time.LocalDateTime;

import org.hh99.tmomi.domain.event.entity.EventTimes;

import lombok.Getter;

@Getter
public class EventTimesResponseDto {
	private long eventId;

	private LocalDateTime eventTime;

	public EventTimesResponseDto(EventTimes eventTimes) {
		this.eventTime = eventTimes.getEventTime();
	}
}
