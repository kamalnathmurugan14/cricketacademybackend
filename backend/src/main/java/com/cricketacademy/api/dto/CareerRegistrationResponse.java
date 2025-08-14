package com.cricketacademy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CareerRegistrationResponse {
    private boolean success;
    private String message;
    private String status;
    private Long applicationId;
    private String redirectUrl;
}
