package org.hh99.tmomi.domain.user.service;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UserResponseDto signUp(UserRequestDto userRequestDto) {
		if (userRepository.findByEmail(userRequestDto.getEmail()) != null) {
			throw new IllegalArgumentException();
		}
		return new UserResponseDto(userRepository.save(new User(userRequestDto)));
	}

}
