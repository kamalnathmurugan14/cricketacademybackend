package com.cricketacademy.api.controller;

import com.cricketacademy.api.service.CashfreeService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    private final CashfreeService cashfreeService;

    @Autowired
    public PaymentController(CashfreeService cashfreeService) {
        this.cashfreeService = cashfreeService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<?> createOrder(@RequestBody CreateOrderRequest request) {
        try {
            String paymentSessionId = cashfreeService.createOrder(
                    request.getOrderId(),
                    request.getOrderAmount(),
                    request.getCustomerEmail(),
                    request.getCustomerPhone()
            );
            return ResponseEntity.ok(new CreateOrderResponse(paymentSessionId));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(java.util.Map.of("message", "Failed to create Cashfree order: " + e.getMessage()));
        }
    }

    @Data
    public static class CreateOrderRequest {
        private String orderId;
        private String orderAmount;
        private String customerEmail;
        private String customerPhone;
    }

    @Data
    public static class CreateOrderResponse {
        private String paymentSessionId;
        public CreateOrderResponse(String paymentSessionId) {
            this.paymentSessionId = paymentSessionId;
        }
    }
}
