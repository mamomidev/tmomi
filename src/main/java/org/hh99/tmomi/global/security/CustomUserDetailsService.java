package org.hh99.tmomi.global.security;

import org.hh99.tmomi.domain.user.entity.User;
import org.hh99.tmomi.domain.user.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		return userRepository.findByEmail(email)
			.map(this::creatUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다."));
	}

	private UserDetails creatUserDetails(User user) {
		return org.springframework.security.core.userdetails.User.builder()
			.username(user.getEmail())
			.password(passwordEncoder.encode(user.getPassword()))
			.roles(user.getRoles().toArray(new String[0]))
			.build();
	}
}
