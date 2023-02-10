package com.example.carrotserver.config

import org.springframework.context.annotation.Configuration
import org.springframework.messaging.simp.config.MessageBrokerRegistry
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker
import org.springframework.web.socket.config.annotation.StompEndpointRegistry
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer

@Configuration
@EnableWebSocketMessageBroker
class WebSocketConfig: WebSocketMessageBrokerConfigurer {
    /* Websocket 연결을 위한 엔드포인트 지정해주는 메서드 */
    override fun registerStompEndpoints(registry: StompEndpointRegistry) {
        registry
            .addEndpoint("/chat")
            .setAllowedOrigins("*")
    }

    /* 엔드포인트에 대한 prefix 지정 */
    override fun configureMessageBroker(registry: MessageBrokerRegistry) {
        registry.enableSimpleBroker("/topic", "/queue")   /* Server -> Client Endpoint (Client 구독 경로) */
        registry.setApplicationDestinationPrefixes("/app")       /* Client -> Server Endpoint */
    }
}