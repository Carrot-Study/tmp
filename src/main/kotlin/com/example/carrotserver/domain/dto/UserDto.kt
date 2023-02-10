package com.example.carrotserver.domain.dto

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotEmpty

data class UserDto(
    @field:Email
    var mail: String,
    @field:NotEmpty
    var password: String
)