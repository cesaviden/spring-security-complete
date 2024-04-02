package com.app.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.app.auth.controller.dto.AuthCreateUserRequest;
import com.app.auth.controller.dto.AuthLoginRequest;
import com.app.auth.controller.dto.AuthResponse;

import com.app.auth.service.UserDetailServiceImpl;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    @Autowired
    private UserDetailServiceImpl userDetailService;

    @PostMapping("/sign-up")
    public ResponseEntity<AuthResponse> signUp(@RequestBody @Valid AuthCreateUserRequest authCreateUser) {
        return new ResponseEntity<>(this.userDetailService.createUser(authCreateUser), HttpStatus.CREATED);
    }

    @PostMapping("/log-in")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthLoginRequest userRequest) {
        return new ResponseEntity<>(this.userDetailService.loginUser(userRequest), HttpStatus.OK);
    }
}
