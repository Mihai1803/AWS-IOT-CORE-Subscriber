package com.iot.aws_iot_subscriber;

import com.amazonaws.services.iot.client.AWSIotException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/iot")
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<?> getData(
            @RequestParam(value = "filter", required = false) String filter,
            @RequestHeader("X-API-KEY") String apiKey
    ) throws AWSIotException {

        if (!validApiKey.equals(apiKey)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid API Key");
        }

        initiateService();

        if (filter != null) {
            String value = awsIotService.getLatestFilterValue(filter);
            return ResponseEntity.ok(Map.of(filter, value));
        }

        return ResponseEntity.ok(awsIotService.getLatestMessagePayload());
    }


}
