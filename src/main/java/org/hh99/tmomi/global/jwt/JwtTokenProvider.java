package org.hh99.tmomi.global.jwt;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import org.hh99.tmomi.global.redis.RefreshToken;
import org.hh99.tmomi.global.redis.RefreshTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtTokenProvider {

	private final Key key;
	private final RefreshTokenRepository refreshTokenRepository;

	public JwtTokenProvider(@Value("${jwt.secret.key}") String secretKey,
		RefreshTokenRepository refreshTokenRepository) {
		this.refreshTokenRepository = refreshTokenRepository;
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public JwtToken generateToken(Authentication authentication) {

		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		String accessToken = createAccessToken(authentication, authorities);
		String refreshToken = createRefreshToken();

		refreshTokenRepository.save(RefreshToken.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build());

		return JwtToken.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.build();
	}

	public String createAccessToken(Authentication authentication, String authorities) {
		long now = (new Date()).getTime();
		Date accessTokenExpiresln = new Date(now + 86400000);
		return Jwts.builder()
			.setSubject(authentication.getName())
			.claim("auth", authorities)
			.setExpiration(accessTokenExpiresln)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public String createRefreshToken() {
		return Jwts.builder()
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {

		Claims claims = parseClaims(accessToken);

		if (claims.get("auth") == null) {
			throw new RuntimeException("권한 정보가 없는 토큰입니다.");
		}

		Collection<? extends GrantedAuthority> authorities = Arrays.stream(claims.get("auth").toString().split(","))
			.map(SimpleGrantedAuthority::new)
			.collect(Collectors.toList());

		UserDetails principal = new User(claims.getSubject(), "", authorities);
		return new UsernamePasswordAuthenticationToken(principal, "", authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token);
			return true;
		} catch (SecurityException | MalformedJwtException e) {
			log.info("Invalid JWT Token", e);
		} catch (ExpiredJwtException e) {
			log.info("Expired JWT Token", e);
		} catch (UnsupportedJwtException e) {
			log.info("Unsupported JWT Token", e);
		} catch (IllegalArgumentException e) {
			log.info("JWT claims string is empty.", e);
		}
		return false;
	}

	private Claims parseClaims(String accessToken) {
		try {
			return Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(accessToken)
				.getBody();
		} catch (ExpiredJwtException e) {
			return e.getClaims();
		}
	}

}
