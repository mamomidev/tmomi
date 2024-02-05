package org.hh99.tmomi.global.exception;

import org.hh99.tmomi.global.exception.message.ExceptionCode;
import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class GlobalException extends RuntimeException {

	private final HttpStatus httpStatus;
	private final ExceptionCode exceptionCode;

}

