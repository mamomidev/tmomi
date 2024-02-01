package org.hh99.tmomi.domain.user.service;

import org.hh99.tmomi.domain.user.UserAuthEnum;
import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.domain.user.repository.UserRepository;
import org.hh99.tmomi.global.jwt.JwtToken;
import org.hh99.tmomi.global.jwt.JwtTokenProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public JwtToken signIn(UserRequestDto userRequestDto) {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			userRequestDto.getEmail(),
			userRequestDto.getPassword());

		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		return jwtTokenProvider.generateToken(authentication);
	}

	@Transactional
	public UserResponseDto signUp(UserRequestDto userRequestDto) {
		if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
			throw new IllegalArgumentException();
		}
		userRequestDto.setAuthor(UserAuthEnum.USER);
		return new UserResponseDto(userRepository.save(new User(userRequestDto)));
	}

}
