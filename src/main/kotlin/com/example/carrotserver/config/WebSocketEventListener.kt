package com.example.carrotserver.config

import com.example.carrotserver.domain.MessageType
import com.example.carrotserver.domain.dto.MessageDto
import org.slf4j.LoggerFactory
import org.springframework.context.event.EventListener
import org.springframework.messaging.simp.SimpMessageHeaderAccessor
import org.springframework.messaging.simp.SimpMessageSendingOperations
import org.springframework.stereotype.Component
import org.springframework.web.socket.messaging.SessionConnectEvent
import org.springframework.web.socket.messaging.SessionDisconnectEvent

@Component
class WebSocketEventListener(private val messagingTemplate: SimpMessageSendingOperations) {
    private val logger = LoggerFactory.getLogger(messagingTemplate::class.java)

    /* 입장 이벤트 처리 */
    @EventListener
    internal fun handleWebSocketConnectListener(event: SessionConnectEvent) {
        val headers = event.message.headers
        val user = SimpMessageHeaderAccessor.getUser(headers)

        if (user == null) {
            logger.info("입장 이벤트 발생")
            return
        }

        val message = "${user.name}님이 대화방에 접속하셨습니다."
        val messageDto = MessageDto(MessageType.JOIN, message, user.name)

        logger.info(messageDto.toString())
        messagingTemplate.convertAndSend("/topic/public", messageDto)
    }

    /* 퇴장 이벤트 처리 */
    @EventListener
    internal fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        val headers = event.message.headers
        val user = SimpMessageHeaderAccessor.getUser(headers)

        if (user == null) {
            logger.info("퇴장 이벤트 발생")
            return
        }

        val message = "${user.name}님이 대화방에서 퇴장하였습니다."
        val messageDto = MessageDto(MessageType.LEAVE, message, user.name)

        logger.info(messageDto.toString())
        messagingTemplate.convertAndSend("/topic/public", messageDto)
    }
}