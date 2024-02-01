package org.hh99.tmomi.domain.user.dto;

import org.hh99.tmomi.domain.user.UserAuthEnum;

import lombok.Getter;

@Getter
public class UserRequestDto {

	private String password;
	private String phone;
	private UserAuthEnum author;
	private String email;
}
