package org.hh99.tmomi.global.jwt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private static final String[] WHITELIST = {
		"/api/v1/signin", // 로그인
		"/api/v1/signup"  // 회원가입
	};
	private final AntPathMatcher antPathMatcher = new AntPathMatcher();

	@Override
	public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		IOException,
		ServletException {

		String path = request.getRequestURI();
		if (Arrays.stream(WHITELIST).anyMatch(pattern -> antPathMatcher.match(pattern, path))) {
			chain.doFilter(request, response);
			return;
		}

		String token = resolveToken(request);
		try {
			jwtTokenProvider.validateToken(token);
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (ExpiredJwtException e) {    // 토큰 만료시
			try {
				jwtTokenProvider.validateRefreshToken(token);

				String newAccessToken = jwtTokenProvider.createNewAccessToken(token);
				jwtTokenProvider.validateToken(newAccessToken);
				Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
				SecurityContextHolder.getContext().setAuthentication(authentication);

				JwtToken jwtToken = jwtTokenProvider.generateToken(authentication);
				// 쿠키에 넣기
				Cookie cookie = new Cookie("Authorization",
					URLEncoder.encode("Bearer " + jwtToken.getAccessToken(), "utf-8").replaceAll("\\+", "%20"));
				cookie.setPath("/");
				cookie.setMaxAge(60 * 60);  // 쿠키 유효 시간 : 1시간
				response.addCookie(cookie);

			} catch (ExpiredJwtException ex) { // refresh 토큰 만료시
				response.getWriter().write("로그인이 만료되었습니다.");
			}
		}

		chain.doFilter(request, response);
	}

	private String resolveToken(HttpServletRequest request) {

		Cookie[] cookies = request.getCookies();
		if (cookies == null) {
			return null;
		}

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("Authorization")) {
				try {
					String token = URLDecoder.decode(cookie.getValue(), "UTF-8");
					if (StringUtils.hasText(token) && token.startsWith("Bearer ")) {
						return token.substring(7);
					}
					return URLDecoder.decode(cookie.getValue(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					return null;
				}
			}
		}
		return null;
	}
}
