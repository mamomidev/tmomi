package org.hh99.tmomi.domain.user.entity;

import java.util.ArrayList;
import java.util.Collection;

import org.hh99.tmomi.domain.user.UserAuthEnum;
import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;

@Entity
@Getter
@Table(name = "users")
public class User implements UserDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column
	private String email;

	@Column
	private String password;

	@Column
	private String phone;

	@Column
	@Enumerated(EnumType.STRING)
	private UserAuthEnum author;

	public User() {

	}

	public User(UserRequestDto userRequestDto) {
		this.email = userRequestDto.getEmail();
		this.password = userRequestDto.getPassword();
		this.phone = userRequestDto.getPhone();
		this.author = userRequestDto.getAuthor();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		UserAuthEnum auth = author;
		String authority = auth.getAuthority();

		SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(simpleGrantedAuthority);

		return authorities;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
