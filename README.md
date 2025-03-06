# AWS IOT CORE Subscriber

## Overview
This project is a Java Spring Boot API that connects to an MQTT topic created in AWS IoT Core. The API retrieves real-time data sent to the topic by an ESP32 microcontroller.

## Features
- Connects to an AWS IoT Core MQTT topic
- Retrieves real-time data published by an ESP32
- Secure communication using TLS certificates

## Notes
To ensure secure communication with AWS IoT Core, set the following environment variables in your system:

```sh
export AWS_IOT_PRIVATE_KEY=<your_private_key>
export AWS_IOT_CERTIFICATE=<your_security_certificate>
export AWS_IOT_CLIENT_ENDPOINT=<your_endpoint>
export API_KEY=<your_api_key>
