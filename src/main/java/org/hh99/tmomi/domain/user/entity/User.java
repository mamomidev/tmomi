package org.hh99.tmomi.domain.user.entity;

import org.hh99.tmomi.domain.user.UserAuthEnum;
import org.hh99.tmomi.domain.user.dto.UserRequestDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "users")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String password;

	@Column
	private String phone;

	@Column
	private UserAuthEnum author;

	@Column
	private String email;

	public User(UserRequestDto userRequestDto) {
		this.email = userRequestDto.getEmail();
		this.password = userRequestDto.getPassword();
		this.phone = userRequestDto.getPhone();
		this.author = userRequestDto.getAuthor();
	}
}
