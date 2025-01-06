package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.iot.aws_iot_subscriber.websocket.MessageStorageService;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

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

    public String getLatestMessagePayload() {
        String payload = messageStorageService.getLatestMessage();
        if (payload == null) {
            return "Waiting for data..." + "\n" + "Please reload the page...";
        }
        return messageStorageService.getLatestMessage();
    }

    public double getLatestFilterValue(String key) {
        String jsonString = messageStorageService.getLatestMessage();
        if (jsonString == null || jsonString.isEmpty()) {
            throw new IllegalStateException("No messages available in the queue");
        }
        JSONObject jsonObject = new JSONObject(jsonString);
        return jsonObject.getDouble(key);

    }

    public boolean isConnected() {
        return connected;
    }
}


