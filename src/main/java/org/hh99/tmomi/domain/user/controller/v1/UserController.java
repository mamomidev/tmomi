package org.hh99.tmomi.domain.user.controller.v1;

import java.io.UnsupportedEncodingException;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.service.UserService;
import org.hh99.tmomi.global.jwt.JwtToken;
import org.hh99.tmomi.global.jwt.JwtTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.signUp(userRequestDto));
	}

	@PostMapping("/signin")
	public ResponseEntity signIn(HttpServletResponse httpServletResponse,
		@RequestBody UserRequestDto userRequestDto) throws UnsupportedEncodingException {
		JwtToken jwtToken = userService.signIn(userRequestDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);

		return ResponseEntity.ok().build();
	}
}
