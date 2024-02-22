package org.hh99.tmomi.domain.user.controller.v1;

import java.io.UnsupportedEncodingException;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.service.UserService;
import org.hh99.tmomi.global.elasticsearch.ElasticSearchItems;
import org.hh99.tmomi.global.elasticsearch.ElasticSearchItemsRepostiory;
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
	private final ElasticSearchItemsRepostiory elasticSearchItemsRepostiory;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.signUp(userRequestDto));
	}

	@PostMapping("/signin")
	public ResponseEntity<UserResponseDto> signIn(HttpServletResponse httpServletResponse,
		@RequestBody UserRequestDto userRequestDto) throws UnsupportedEncodingException {
		JwtToken jwtToken = userService.signIn(userRequestDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);

		Long num = (long)(Math.random() * 100);
		ElasticSearchItems elasticSearchItems = ElasticSearchItems.builder()
			.id(num)
			.name("test")
			.userId(10)
			.build();
		elasticSearchItemsRepostiory.save(elasticSearchItems);

		return ResponseEntity.ok().build();
	}
}
