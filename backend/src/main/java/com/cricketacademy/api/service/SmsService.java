package com.cricketacademy.api.service;

import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;
import java.util.*;

@Service
public class SmsService {

    @Value("${infobip.base.url}")
    private String baseUrl;

    @Value("${infobip.api.key}")
    private String apiKey;

    public boolean sendOtp(String phoneNumber, String otp) {
        RestTemplate restTemplate = new RestTemplate();

        Map<String, Object> message = new HashMap<>();
        message.put("from", "InfoSMS");
        message.put("destinations", List.of(Map.of("to", phoneNumber)));
        message.put("text", "Your OTP is: " + otp);

        Map<String, Object> body = Map.of("messages", List.of(message));

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "App " + apiKey);
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(baseUrl, request, String.class);
            return response.getStatusCode().is2xxSuccessful();
        } catch (Exception e) {
            // Log error in production
            return false;
        }
    }
}
