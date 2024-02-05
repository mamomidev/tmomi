package org.hh99.tmomi.domain.event.entity;

import java.time.LocalDateTime;

import org.hh99.tmomi.domain.event.dto.eventtimes.EventTimesRequestDto;

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
@Table(name = "eventTimes")
public class EventTimes {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JoinColumn(name = "event_id")
	@ManyToOne(fetch = FetchType.LAZY)
	private Event event;

	@Column
	private LocalDateTime eventTime;

	public EventTimes(EventTimesRequestDto eventTimesRequestDto, Event event) {
		this.event = event;
		this.eventTime = eventTimesRequestDto.getEventTime();
	}

	public void update(EventTimesRequestDto eventTimesRequestDto) {
		this.eventTime = eventTimesRequestDto.getEventTime();
	}
}
