package org.hh99.tmomi.global.exception;

import org.hh99.tmomi.global.exception.message.ExceptionCode;

import lombok.Getter;

@Getter
public class ExceptionResponse {

	private final String message;

	public ExceptionResponse(ExceptionCode exceptionCode) {
		this.message = exceptionCode.getMessage();
	}
}
