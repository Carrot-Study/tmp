package com.example.carrotserver.controller

import com.example.carrotserver.domain.dto.MessageDto
import com.example.carrotserver.domain.dto.UserDto
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.messaging.simp.annotation.SendToUser
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * [ STOMP 테스트 정리 ]
 * - 서로 다른 EndPoint에 접속해도 소켓 연결 가능
 * - 클라이언트 구독 경로 : @SendTo 어노테이션에 지정된 주소
 *      - /topic/tmp, /queue/tmp ...
 * - 클라이언트 전송 경로 : setApplicationDestinationPrefixes 지정 주소 + @MessageMapping 지정 주소
 *      - /app/test1, /app/test2 ...
 *
 * [ 추가적으로 공부할 내용 ]
 * - sendTo는 정상 작동하는데 sendToUser 작동하지 않는 이유?
 * - 외부 메세지 브로커 연동 (RabbitMQ, Redis pub/sub 등), Spring 자체 브로커는 인메모리 브로커 (모니터링 불편)
 * - 브로커 릴레이 개념과 전반적 흐름에 대해 복습
 * - https://velog.io/@koseungbin/WebSocket 쭉 따라가보기
 */

@RestController
class ChatController(private val simpMessageSendingOperations: SimpMessageSendingOperations) {
    private val logger = LoggerFactory.getLogger(ChatController::class.java)
    @MessageMapping("/test")    /* Destination Queue: /app/test */
    @SendTo("/queue/tmp")       /* 클라이언트 구독 경로: /queue/tmp */
    internal fun test(@Payload messageDto: MessageDto): String {
        logger.info(messageDto.toString())
        return "${messageDto.sender} : ${messageDto.content}"
    }

    @MessageMapping("/test2")
    @SendToUser("/queue/tmp2")
    internal fun test2(message: String): String {
        return message
    }

    @MessageMapping("/test3")
    @SendTo("/topic/tmp3")
    internal fun test3(message: String): String {
        return message
    }

    /**
     * - 채팅방에 유저가 접속할 때 실행되는 메서드
     * - session <-- 유저 추가
     */
    @MessageMapping("/addUser")  /* 메세지 발행: /app/addUser */
    internal fun addUser(@Payload messageDto: MessageDto, headerAccessor: SimpMessageHeaderAccessor) {
        logger.info("/app/addUser/" + messageDto.channelId + "<-- $messageDto")
        simpMessageSendingOperations.convertAndSend("/topic/channel/" + messageDto.channelId, messageDto)
    }

    /**
     * 채팅방 생성 메서드
     * - 두 유저의 id를 받아서 채팅방 생성 ---> 원래는 채팅방 참여 유저 전체의 정보를 가져와야 한다? (유저 객체 초기화를 위해)
     * { "users" : [
     *     {
     *        "mail": "...",
     *        "password": "..."
     *     },
     *     {
     *        "mail": "...",
     *        "password': "..."
     *     },
     *     ...
     *   ]
     * }
     */
    @PostMapping("/create")
    internal fun createChatRoom(@RequestBody jsonMap: Map<String, List<UserDto>>) {
        val users = jsonMap["users"] ?: return

        // 참여한 모든 유저를 같은 경로로 구독시켜야 함 (프론트?)

        // 채팅방 만들기
        for (user in users) {

        }
    }
}