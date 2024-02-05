package org.hh99.tmomi.global.exception;

import org.hh99.tmomi.global.exception.message.ExceptionCode;

import lombok.Getter;

@Getter
public class ExceptionResponse {

	private final String code;
	private final String message;

	public ExceptionResponse(ExceptionCode exceptionCode) {
		this.code = exceptionCode.getCode();
		this.message = exceptionCode.getMessage();
	}
}
