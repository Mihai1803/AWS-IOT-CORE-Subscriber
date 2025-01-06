package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.iot.aws_iot_subscriber.websocket.MessageStorageService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AwsIotService {

    private final MessageStorageService messageStorageService;
    private final AwsIotClient client;
    private final AWSIotTopic topic;
    private final String topicName = "mcu/data";
    private final AWSIotQos qos = AWSIotQos.QOS0;
    private boolean connected = false;



    public AwsIotService(MessageStorageService messageStorageService) {
        this.topic = new AwsIotTopicListener(this.topicName, this.qos, messageStorageService);
        this.client = new AwsIotClient();
        this.messageStorageService = messageStorageService;
    }

    public void initiate() throws AWSIotException {
        client.connect();
        client.subscribe(topic);
        connected = true;
    }

    public Map<String, Object> getLatestMessagePayload() {
        String payload = messageStorageService.getLatestMessage();
        if (payload == null) {
            return Map.of("message", "Waiting for data. Please reload the page...");
        }
        JSONObject jsonObject = new JSONObject(payload);
        return jsonObject.toMap();
    }

    public String getLatestFilterValue(String key) {
        String jsonString = messageStorageService.getLatestMessage();
        System.out.println(jsonString);
        if (jsonString == null || jsonString.isEmpty()) {
            throw new IllegalStateException("No messages available in the queue");
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getString(key);
    }

    public boolean isConnected() {
        return connected;
    }
}


