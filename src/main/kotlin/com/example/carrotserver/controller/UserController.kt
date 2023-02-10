package com.example.carrotserver.controller

import com.example.carrotserver.domain.dto.UserDto
import com.example.carrotserver.service.UserService
import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import java.util.regex.Pattern

@RestController
class UserController(private val userService: UserService) {
    /* 회원가입 */
    @PostMapping("/signUp")
    internal fun signUp(@RequestBody @Valid user: UserDto): Long {
        /* 비밀번호 정합성 검사 (8자 이상, 적어도 하나의 문자, 숫자, 특수문자 포함) */
        val reg = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,}$";
        if (!Pattern.matches(reg, user.password))
            return 0L

        userService.signUp(user)
        return 1L
    }

    /* 로그인 */
    @PostMapping("/signIn")
    internal fun signIn(@RequestBody @Valid user: UserDto): Long = userService.signIn(user)

}