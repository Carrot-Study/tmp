package com.example.carrotserver.domain.dto

import com.example.carrotserver.domain.MessageType

data class MessageDto(
    var content: String?,
    var sender: String,
    var channelId: String
) {

    var type: MessageType = MessageType.CHAT

    internal fun newConnect() {
        this.type = MessageType.JOIN
    }

    internal fun closeConnect() {
        this.type = MessageType.LEAVE
    }
}