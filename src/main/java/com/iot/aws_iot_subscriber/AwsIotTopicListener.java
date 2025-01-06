package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.iot.aws_iot_subscriber.websocket.MessageStorageService;


public class AwsIotTopicListener extends AWSIotTopic {

    private final MessageStorageService messageStorageService;
    public AwsIotTopicListener(String topic, AWSIotQos qos, MessageStorageService messageStorageService) {
        super(topic, qos);
        this.messageStorageService = messageStorageService;
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        String payload = message.getStringPayload();
        System.out.println("Message received: " + payload);
        messageStorageService.addMessage(payload);
    }

}

