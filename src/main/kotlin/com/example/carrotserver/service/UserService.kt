package com.example.carrotserver.service

import com.example.carrotserver.domain.dto.UserDto
import com.example.carrotserver.domain.entity.User
import com.example.carrotserver.repository.UserRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserService(private val userRepository: UserRepository) {
    @Transactional
    internal fun signUp(userDto: UserDto) {
        val findUser: User? = userRepository.findByMail(userDto.mail)

        if (findUser != null) return

        val user = User(mail = userDto.mail, password = userDto.password)
        userRepository.save(user)
    }

    @Transactional
    internal fun signIn(userDto: UserDto): Long {
        val findUser: User? = userRepository.findByMail(userDto.mail)

        if (findUser?.mail == userDto.mail && findUser.password == userDto.password) {
            return 1L
        }

        return 0L
    }
}