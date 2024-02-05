package org.hh99.tmomi.global.message.dto;

import org.hh99.tmomi.global.exception.GlobalException;
import org.hh99.tmomi.global.message.ExceptionCode;
import org.springframework.http.ResponseEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionCodeDto {

	private String code;
	private String message;

	public ResponseEntity<ExceptionCodeDto> toResponseEntity(GlobalException ex) {
		ExceptionCode exceptionCode = ex.getExceptionCode();

		return ResponseEntity.status(ex.getHttpStatus()).body(ExceptionCodeDto.builder()
			.code(exceptionCode.getCode())
			.message(exceptionCode.getMessage())
			.build());
	}
}
