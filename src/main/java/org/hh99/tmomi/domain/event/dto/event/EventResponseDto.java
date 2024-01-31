package org.hh99.tmomi.domain.event.dto.event;

import java.time.LocalDate;

import org.hh99.tmomi.domain.event.entity.Event;

import lombok.Getter;

@Getter
public class EventResponseDto {

	private String eventName;

	private LocalDate eventStartDate;

	private LocalDate eventEndDate;

	private String eventImage;

	private String eventDescription;

	public EventResponseDto(Event event) {
		this.eventName = event.getEventName();
		this.eventStartDate = event.getEventStartDate();
		this.eventEndDate = event.getEventEndDate();
		this.eventImage = event.getEventImage();
		this.eventDescription = event.getEventDescription();
	}
}
