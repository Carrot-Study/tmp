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
        logger.info("입장 이벤트 발생")
    }

    /* 퇴장 이벤트 처리 */
    @EventListener
    internal fun handleWebSocketDisconnectListener(event: SessionDisconnectEvent) {
        logger.info("퇴장 이벤트 발생")
    }
}