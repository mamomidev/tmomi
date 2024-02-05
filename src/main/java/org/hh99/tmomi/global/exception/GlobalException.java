package org.hh99.tmomi.global.exception;

import org.hh99.tmomi.global.message.ExceptionCode;

public class GlobalException extends RuntimeException {

	private final ExceptionCode exceptionCode;

	public GlobalException(ExceptionCode exceptionCode) {
		super(exceptionCode.getMessage());
		this.exceptionCode = exceptionCode;
	}

	public ExceptionCode getCode() {
		return exceptionCode;
	}
}
