package org.hh99.tmomi.domain.event.dto.eventtimes;

import java.time.LocalDateTime;

import org.hh99.tmomi.domain.event.entity.EventTimes;

import lombok.Getter;

@Getter
public class EventTimesResponseDto {
	private final long eventTimesId;
	private final LocalDateTime eventTime;

	public EventTimesResponseDto(EventTimes eventTimes) {
		this.eventTimesId = eventTimes.getId();
		this.eventTime = eventTimes.getEventTime();
	}
}
