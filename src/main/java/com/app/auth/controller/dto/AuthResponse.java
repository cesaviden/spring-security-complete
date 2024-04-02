package com.app.auth.controller.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({"username", "message", "token", "status"})
public record AuthResponse(String username,
        String message,
        String token,
        boolean status) {
}