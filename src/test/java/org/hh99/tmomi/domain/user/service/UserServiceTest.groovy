package org.hh99.tmomi.domain.user.service

import org.hh99.tmomi.domain.user.dto.UserRequestDto
import org.hh99.tmomi.domain.user.repository.UserRepository
import org.hh99.tmomi.global.jwt.JwtTokenProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification
import spock.lang.Unroll

class UserServiceTest extends Specification {

    UserRepository userRepository = Mock();
    AuthenticationManagerBuilder authenticationManagerBuilder = Mock();
    JwtTokenProvider jwtTokenProvider = Mock();
    PasswordEncoder passwordEncoder = Mock();
    UserService userService

    def setup() {
        userService = new UserService(userRepository,
                authenticationManagerBuilder,
                jwtTokenProvider,
                passwordEncoder)
    }

    @Unroll
    def "SignIn"() {
        given:
        def userRequestDto = Mock(UserRequestDto)
        userRequestDto.getEmail() >> "email"
        userRequestDto.getPassword() >> "password"

        def authentication = Mock(Authentication)
        authenticationManagerBuilder.getObject().authenticate(_) >> authentication
        jwtTokenProvider.generateToken(authentication)

        when:
        userService.signIn(userRequestDto)

        then:

    }
}
