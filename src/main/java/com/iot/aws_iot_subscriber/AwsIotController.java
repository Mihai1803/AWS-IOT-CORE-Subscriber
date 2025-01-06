package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


@RestController
@RequestMapping("/api/iot")
public class AwsIotController {
    private final AwsIotService awsIotService;

    private final String validApiKey = System.getenv("API_KEY");;

    public AwsIotController(AwsIotService awsIotService) {
        this.awsIotService = awsIotService;
    }

    public void initiateService() throws AWSIotException {
        if (!awsIotService.isConnected()) {
            awsIotService.initiate();
        }
    }

    @GetMapping("/data")
    @ResponseStatus(HttpStatus.OK)
    public String getData(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestHeader("X-API-KEY") String apiKey
    ) throws AWSIotException {

        if (!validApiKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }

        initiateService();
        if (filter != null) {
            return filter + ": " + awsIotService.getLatestFilterValue(filter);
        }
        return awsIotService.getLatestMessagePayload();
    }
}
