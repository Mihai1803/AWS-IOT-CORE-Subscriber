package com.iot.aws_iot_subscriber.websocket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;


@Component
public class WebSocketMessageHandler extends TextWebSocketHandler {

    private MessageStorageService messageStorageService;

    public WebSocketMessageHandler(MessageStorageService messageStorageService) {
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        String payload = message.getPayload();
        System.out.println("WebSocket message: " + payload);

        messageStorageService.addMessage(payload);
    }
}
