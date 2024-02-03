package org.hh99.tmomi.domain.event.entity;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hh99.tmomi.domain.stage.entity.Stage;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "events")
public class Event {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String eventName;

	@Column
	private LocalDate eventStartDate;

	@Column
	private LocalDate eventEndDate;

	@Column
	private String eventImage;

	@Column
	private String eventDescription;

	@JoinColumn(name = "stage_id")
	@ManyToOne
	private Stage stage;

	public Event(EventRequestDto eventRequestDto, Stage stage) {
		this.eventName = eventRequestDto.getEventName();
		this.eventStartDate = eventRequestDto.getEventStartDate();
		this.eventEndDate = eventRequestDto.getEventEndDate();
		this.eventImage = eventRequestDto.getEventImage();
		this.eventDescription = eventRequestDto.getEventDescription();
		this.stage = stage;
	}

	public void update(EventRequestDto eventRequestDto, Stage stage) {
		this.eventName = eventRequestDto.getEventName();
		this.eventStartDate = eventRequestDto.getEventStartDate();
		this.eventEndDate = eventRequestDto.getEventEndDate();
		this.eventImage = eventRequestDto.getEventImage();
		this.eventDescription = eventRequestDto.getEventDescription();
		this.stage = stage;
	}
}
