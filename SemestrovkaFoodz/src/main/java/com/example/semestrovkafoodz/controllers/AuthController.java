package com.example.semestrovkafoodz.controllers;

import com.example.semestrovkafoodz.dtos.TokenDto;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.semestrovkafoodz.dtos.JwtRequest;
import com.example.semestrovkafoodz.dtos.RegistrationUserDto;
import com.example.semestrovkafoodz.service.AuthService;

@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/auth")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/registration")
    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        return authService.createNewUser(registrationUserDto);
    }

    @PostMapping("/updateToken")
    public ResponseEntity<?> updateToken(@RequestBody TokenDto refreshToken) {

        return authService.updateToken(refreshToken);

    }
}