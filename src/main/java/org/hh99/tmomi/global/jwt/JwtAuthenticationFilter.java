package org.hh99.tmomi.global.jwt;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
		"/api/v1/signup",  // 회원가입
		"/health",
		"/"
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
		if (token == null && path.equals("/api/v1/sse-connection")) {
			chain.doFilter(request, response);
			return;
		}

		try {
			jwtTokenProvider.validateToken(token);
			Authentication authentication = jwtTokenProvider.getAuthentication(token);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (ExpiredJwtException e) {    // 토큰 만료시
			String newAccessToken = jwtTokenProvider.reissuanceAccessToken(token,
				jwtTokenProvider.validateRefreshToken(token));
			jwtTokenProvider.createCookieAccessToken(newAccessToken, response);
			Authentication authentication = jwtTokenProvider.getAuthentication(newAccessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);
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
