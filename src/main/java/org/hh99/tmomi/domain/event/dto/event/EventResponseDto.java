package org.hh99.tmomi.domain.event.dto.event;

import java.time.LocalDate;

import org.hh99.tmomi.domain.event.entity.Event;

import lombok.Getter;

@Getter
public class EventResponseDto {

	private final String eventName;
	private final LocalDate eventStartDate;
	private final LocalDate eventEndDate;
	private final String eventImage;
	private final String eventDescription;

	public EventResponseDto(Event event) {
		this.eventName = event.getEventName();
		this.eventStartDate = event.getEventStartDate();
		this.eventEndDate = event.getEventEndDate();
		this.eventImage = event.getEventImage();
		this.eventDescription = event.getEventDescription();
	}
}
