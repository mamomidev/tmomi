package org.hh99.tmomi.domain.user.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

	private final UserRepository userRepository;

	private final AuthenticationManagerBuilder authenticationManagerBuilder;
	private final JwtTokenProvider jwtTokenProvider;
	private final PasswordEncoder passwordEncoder;

	@Transactional
	public JwtToken signIn(UserRequestDto userRequestDto, HttpServletResponse httpServletResponse) throws
		UnsupportedEncodingException {
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
			userRequestDto.getEmail(),
			userRequestDto.getPassword());
		Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

		JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
		Cookie cookie = new Cookie("Authorization",
			URLEncoder.encode("Bearer " + jwtToken.getAccessToken(), "utf-8").replaceAll("\\+", "%20"));
		cookie.setPath("/");
		cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
		httpServletResponse.addCookie(cookie);

		return jwtToken;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(user -> org.springframework.security.core.userdetails.User.builder()
				.username(user.getEmail())
				.password(passwordEncoder.encode(user.getPassword()))
				.roles(user.getAuthorities().toString())
				.build())
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
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
