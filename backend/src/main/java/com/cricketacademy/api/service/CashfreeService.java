package com.cricketacademy.api.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.HashMap;
import java.util.Map;

@Service
public class CashfreeService {

    @Value("${cashfree.appId}")
    private String appId;

    @Value("${cashfree.secretKey}")
    private String secretKey;

    @Value("${cashfree.env}")
    private String env;

    public String createOrder(String orderId, String orderAmount, String customerEmail, String customerPhone) {
        String url = env.equalsIgnoreCase("PROD")
                ? "https://api.cashfree.com/pg/orders"
                : "https://sandbox.cashfree.com/pg/orders";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-client-id", appId);
        headers.set("x-client-secret", secretKey);

        Map<String, Object> body = new HashMap<>();
        body.put("order_id", orderId);
        body.put("order_amount", orderAmount);
        body.put("order_currency", "INR");
        body.put("customer_details", Map.of(
                "customer_id", orderId,
                "customer_email", customerEmail,
                "customer_phone", customerPhone));

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                new org.springframework.core.ParameterizedTypeReference<Map<String, Object>>() {
                });

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            Map<String, Object> responseBody = response.getBody();
            if (responseBody != null) {
                Object sessionId = responseBody.get("payment_session_id");
                if (sessionId != null)
                    return sessionId.toString();
            }
        }
        throw new RuntimeException("Failed to create Cashfree order");
    }
}
