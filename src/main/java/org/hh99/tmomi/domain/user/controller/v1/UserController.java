package org.hh99.tmomi.domain.user.controller.v1;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.service.UserService;
import org.hh99.tmomi.global.jwt.JwtToken;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.signUp(userRequestDto));
	}

	@PostMapping("/signin")
	public ResponseEntity signIn(HttpServletResponse httpServletResponse,
		@RequestBody UserRequestDto userRequestDto) throws UnsupportedEncodingException {
		JwtToken jwtToken = userService.signIn(userRequestDto);
		Cookie cookie = new Cookie("Authorization",
			URLEncoder.encode("Bearer " + jwtToken.getAccessToken(), "utf-8").replaceAll("\\+", "%20"));
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
		httpServletResponse.addCookie(cookie);

		return ResponseEntity.ok().build();
	}
}
