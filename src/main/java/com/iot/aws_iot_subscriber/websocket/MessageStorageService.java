package com.iot.aws_iot_subscriber.websocket;

import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentLinkedDeque;

@Service
public class MessageStorageService {
    private final ConcurrentLinkedDeque<String> messages = new ConcurrentLinkedDeque<>();

    public void addMessage(String message) {
        messages.add(message);
    }

    public String getLatestMessage() {
        return messages.peekLast();
    }

    public boolean hasMessages() {
        return !messages.isEmpty();
    }
}
