package org.hh99.tmomi.domain.user.dto;

import org.hh99.tmomi.domain.user.UserAuthEnum;

import lombok.Getter;
import lombok.Setter;

@Getter
public class UserRequestDto {

	private String email;
	private String phone;
	private String password;
	@Setter
	private UserAuthEnum author;
}
