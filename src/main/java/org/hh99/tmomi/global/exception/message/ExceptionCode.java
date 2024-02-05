package org.hh99.tmomi.global.exception.message;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	//TODO 유저,티켓,행사,공연장,좌석 에러 번호 설정?
	// 티켓
	NOT_EXIST_TICKET("티켓이 존재하지 않습니다."),

	// 공연장
	NOT_EXIST_STAGE("공연장이 존재하지 않습니다."),

	// 이벤트
	NOT_EXIST_EVENT("공연 정보가 존재하지 않습니다."),
	NOT_EXIST_EVENT_TIME("공연 시간이 존재하지 않습니다.");

	private final String message;

	ExceptionCode(String message) {
		this.message = message;
	}

}
