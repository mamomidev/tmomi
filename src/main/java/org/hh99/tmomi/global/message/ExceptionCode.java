package org.hh99.tmomi.global.message;

import lombok.Getter;

@Getter
public enum ExceptionCode {

	//TODO 유저,티켓,행사,공연장,좌석 에러 번호 설정?
	NOT_EXIST_TICKET("000", "티켓이 존재하지 않습니다.");

	private final String code;
	private final String message;

	ExceptionCode(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
