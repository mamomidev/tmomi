package org.hh99.tmomi.global;

import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.message.dto.ExceptionCodeDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 리소스를 못 찾을 경우
	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity handleMethodEntityNotFoundException(EntityNotFoundException ex) {
		return ResponseEntity.notFound().build();
	}

	// 다수의 리소스의 결과가 없을 경우
	@ExceptionHandler(EntityExistsException.class)
	protected ResponseEntity handleMethodEntityExistsException(EntityExistsException ex) {
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

	@ExceptionHandler(GlobalException.class)
	public ResponseEntity<ExceptionCodeDto> handleGlobalException(GlobalException ex) {
		return new ExceptionCodeDto().toResponseEntity(ex);
	}

}