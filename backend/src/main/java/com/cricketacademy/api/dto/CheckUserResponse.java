package com.cricketacademy.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckUserResponse {
    private boolean exists;
    private String message;
}
