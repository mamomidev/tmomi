package org.hh99.tmomi.domain.user.service

import org.hh99.tmomi.domain.user.UserAuthEnum
import org.hh99.tmomi.domain.user.dto.UserRequestDto
import org.hh99.tmomi.domain.user.entity.User
import org.hh99.tmomi.domain.user.repository.UserRepository
import org.hh99.tmomi.global.jwt.JwtToken
import org.hh99.tmomi.global.jwt.JwtTokenProvider
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
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

//    def "SignIn"() {
//        given:
//        def userRequestDto = Mock(UserRequestDto)
//        userRequestDto.getEmail() >> "email"
//        userRequestDto.getPassword() >> "password"
//
//        def authentication = Mock(Authentication)
//        authenticationManagerBuilder.getObject().authenticate(_) >> authentication
//        def jwtToken = Mock(JwtToken)
//        jwtTokenProvider.generateToken(authentication) >> jwtToken
//
//        when:
//        def result = userService.signIn(userRequestDto)
//
//        then:
//        result == jwtToken
//    }

    def "SignUp" () {
        given:
        def userRequestDto = new UserRequestDto(email: "test@example.com", password: "password")
        def savedUser = new User(email: userRequestDto.email, password: "encodedPassword", author: UserAuthEnum.USER)
        userRepository.findByEmail(userRequestDto.email) >> Optional.empty()
        userRepository.save(_ as User) >> savedUser
        passwordEncoder.encode(userRequestDto.password) >> "encodedPassword"

        when:
        def result = userService.signUp(userRequestDto)

        then:
        1 * userRepository.findByEmail(userRequestDto.email)
        1 * userRepository.save(_ as User)
        1 * passwordEncoder.encode(userRequestDto.password)

        result.email == userRequestDto.email
        result.author == UserAuthEnum.USER

    }

}
