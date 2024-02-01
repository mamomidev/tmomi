package org.hh99.tmomi.domain.user.dto;

import org.hh99.tmomi.domain.user.UserAuthEnum;
import org.hh99.tmomi.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UserResponseDto {

	private final String email;
	private final String phone;
	private final UserAuthEnum author;

	public UserResponseDto(User user) {
		this.email = user.getEmail();
		this.phone = user.getPhone();
		this.author = user.getAuthor();
	}
}
