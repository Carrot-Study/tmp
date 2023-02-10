package com.example.carrotserver.controller

import com.example.carrotserver.domain.dto.MessageDto
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.web.bind.annotation.RestController

@RestController
class ChatController {
    /**
     * [ STOMP 흐름 ]
     * - 1. 클라이언트는 enableSimpleBroker로 등록한 주소로 시작하는 주소를 구독한다. (/topic/(*))
     * - 2. 클라이언트는 setApplicationDestinationPrefixes로 등록한 주소로 시작하는 주소로 발행한다.
     * - 3. @MessageMapping : prefix + destination queue (/app/chat)
     * - 4. @SendTo : 이 곳에 지정된 path를 구독한 모든 구독자들에게 메세지 브로드캐스팅

     *  SendTo는 1:N로 메세지를 뿌릴 때 사용하고 보통 경로가 /topic으로 시작된다
     *  SendToUser는 1:!로 메세지를 뿌릴 때 사용하고 보통 경로가 /queue로 시작한다.
     */

    @MessageMapping("/chat")    /* Destination Queue: /app/chat */
    @SendTo("/topic/public")    /* /topic/public 구독 중인 클라이언트에게 전송 */
    internal fun sendMessage(@Payload messageDto: MessageDto): MessageDto? {
        return messageDto
    }

    /**
     * - 채팅방에 유저가 접속할 때 실행되는 메서드
     * - session <-- 유저 추가
     */
    @MessageMapping("/addUser")
    internal fun addUser(@Payload messageDto: MessageDto, headerAccessor: SimpMessageHeaderAccessor): MessageDto? {
        headerAccessor.sessionAttributes?.set("user", messageDto.sender) ?: return null
        return messageDto
    }
}