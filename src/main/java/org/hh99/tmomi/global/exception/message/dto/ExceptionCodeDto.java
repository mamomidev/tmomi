package org.hh99.tmomi.global.exception.message.dto;

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
}
