package org.hh99.tmomi.domain.user.controller;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.service.UserService;
import org.hh99.tmomi.global.jwt.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.signUp(userRequestDto));
	}

	@PostMapping("/signin")
	public ResponseEntity<JwtToken> signIn(@RequestBody UserRequestDto userRequestDto) {
		JwtToken jwtToken = userService.signIn(userRequestDto);
		return ResponseEntity.ok(jwtToken);
	}
}
