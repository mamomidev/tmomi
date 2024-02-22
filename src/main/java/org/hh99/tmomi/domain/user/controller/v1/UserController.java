package org.hh99.tmomi.domain.user.controller.v1;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.hh99.tmomi.domain.user.dto.UserRequestDto;
import org.hh99.tmomi.domain.user.dto.UserResponseDto;
import org.hh99.tmomi.domain.user.service.UserService;
import org.hh99.tmomi.global.elasticsearch.entity.ElasticSearchReservation;
import org.hh99.tmomi.global.elasticsearch.repository.ElasticSearchItemsRepostiory;
import org.hh99.tmomi.global.jwt.JwtToken;
import org.hh99.tmomi.global.jwt.JwtTokenProvider;
import org.springframework.data.elasticsearch.client.elc.ElasticsearchTemplate;
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
	private final ElasticsearchTemplate elasticsearchTemplate;

	@PostMapping("/signup")
	public ResponseEntity<UserResponseDto> signUp(@RequestBody UserRequestDto userRequestDto) {
		return ResponseEntity.ok(userService.signUp(userRequestDto));
	}

	@PostMapping("/signin")
	public ResponseEntity<UserResponseDto> signIn(HttpServletResponse httpServletResponse,
		@RequestBody UserRequestDto userRequestDto) throws UnsupportedEncodingException {
		JwtToken jwtToken = userService.signIn(userRequestDto);
		jwtTokenProvider.createCookieAccessToken(jwtToken.getAccessToken(), httpServletResponse);

		List<ElasticSearchReservation> searchList = new ArrayList<>();
		for (long i = 0; i < 50000; i++) {
			ElasticSearchReservation elasticSearchItems = ElasticSearchReservation.builder()
				.id(i)
				.name("test")
				.userId(10)
				.build();
			searchList.add(elasticSearchItems);
		}
		elasticSearchItemsRepostiory.saveAll(searchList);
		
		// elasticSearchItems.setId(1L);
		// elasticSearchItems.setName("modify");
		// elasticsearchTemplate.update(elasticSearchItems);

		return ResponseEntity.ok().build();
	}
}
