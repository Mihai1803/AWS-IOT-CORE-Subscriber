package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMqttClient;
import com.amazonaws.services.iot.client.AWSIotTopic;

import com.iot.aws_iot_subscriber.utilities.SampleUtil;
import com.iot.aws_iot_subscriber.utilities.SampleUtil.KeyStorePasswordPair;

public class AwsIotClient {

    private final String clientEndpoint = System.getenv("AWS_IOT_CLIENT_ENDPOINT");
    private final String clientId = "mcu_data_receeiver";
    private String certificateFile = System.getenv("AWS_IOT_CERTIFICATE");
    private String privateKeyFile = System.getenv("AWS_IOT_PRIVATE_KEY");;
    private AWSIotMqttClient client;

    public AwsIotClient() {

        if (certificateFile == null || privateKeyFile == null) {
            throw new IllegalStateException("AWS IoT Certificate or Private Key is not set. Please ensure the environment variables are configured.");
        }
        KeyStorePasswordPair pair = SampleUtil.getKeyStorePasswordPair(certificateFile, privateKeyFile);
        client = new AWSIotMqttClient(clientEndpoint, clientId, pair.keyStore, pair.keyPassword);
    }

    public void connect() throws AWSIotException {
        client.connect();
    }

    public void subscribe(AWSIotTopic topic) throws AWSIotException {
        client.subscribe(topic);
    }

}
