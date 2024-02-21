package org.hh99.tmomi.domain.user.service


import org.hh99.tmomi.domain.user.dto.UserRequestDto
import org.hh99.tmomi.domain.user.dto.UserResponseDto
import org.hh99.tmomi.domain.user.entity.User
import org.hh99.tmomi.domain.user.repository.UserRepository
import org.hh99.tmomi.global.jwt.JwtTokenProvider
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.crypto.password.PasswordEncoder
import spock.lang.Specification

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

    def "회원가입" () {
        given:
        def userRequestDto = Mock(UserRequestDto)
        userRepository.findByEmail(*_) >> Optional.empty()
        userRepository.save(_) >> Mock(User)

        when:
        def result = userService.signUp(userRequestDto)

        then:
        result instanceof UserResponseDto
    }
}
