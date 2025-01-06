package com.iot.aws_iot_subscriber.websocket;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class WebSocketConfig {

    private WebSocketMessageHandler webSocketMessageHandler;

    public WebSocketConfig(WebSocketMessageHandler webSocketMessageHandler) {
        this.webSocketMessageHandler = webSocketMessageHandler;
    }


    public void registerWebSocketHandler(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketMessageHandler, "/ws").setAllowedOrigins("*");
    }
}
