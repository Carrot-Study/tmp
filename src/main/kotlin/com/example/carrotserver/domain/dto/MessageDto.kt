package com.example.carrotserver.domain.dto

import com.example.carrotserver.domain.MessageType
import com.fasterxml.jackson.annotation.JsonIgnore

data class MessageDto(
    @JsonIgnore
    var type: MessageType? = null,
    var content: String?,
    var sender: String
)