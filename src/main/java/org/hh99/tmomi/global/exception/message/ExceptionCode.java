package org.hh99.tmomi.global.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	// 티켓
	NOT_EXIST_TICKET("티켓 정보가 존재하지 않습니다."),
	PURCHASED_TICKET("이미 구매된 티켓 입니다."),

	// 공연장
	NOT_EXIST_STAGE("공연장 정보가 존재하지 않습니다."),

	// 행사
	NOT_EXIST_EVENT("공연 정보가 존재하지 않습니다."),
	NOT_EXIST_EVENT_TIME("공연 시간이 존재하지 않습니다."),

	// 유저
	NOT_EXIST_USER("유저 정보가 존재하지 않습니다."),
	EXIST_USER("이미 존재하는 유저입니다."),
	// 좌석
	NOT_EXIST_SEAT("좌석 정보가 존재하지 않습니다."),

	// 등급
	NOT_EXIST_RANK("등급 정보가 존재하지 않습니다."),

	// 예매
	NOT_EXIST_RESERVATION("예매 정보가 존재하지 않습니다."),

	// 락
	LOCKED("이미 선택된 좌석입니다."),
	NOT_SELECT_LOCKED("선택한 좌석이 아닙니다."),

	// 대기열
	NOT_EXIST_QUEUE_IN_USER("대기열에 유저가 존재하지 않습니다.");

	private final String message;

	ExceptionCode(String message) {
		this.message = message;
	}

}
