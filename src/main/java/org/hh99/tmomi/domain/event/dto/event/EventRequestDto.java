package org.hh99.tmomi.domain.event.dto.event;

import java.time.LocalDate;

import lombok.Getter;

@Getter
public class EventRequestDto {

	private String eventName;

	private LocalDate eventStartDate;

	private LocalDate eventEndDate;

	private String eventImage;

	private String eventDescription;
}
