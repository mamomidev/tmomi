package org.hh99.tmomi.domain.event.entity;

import java.time.LocalDate;

import org.hh99.tmomi.domain.event.dto.event.EventRequestDto;
import org.hh99.tmomi.domain.stage.entity.Stage;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "stage_id")
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
