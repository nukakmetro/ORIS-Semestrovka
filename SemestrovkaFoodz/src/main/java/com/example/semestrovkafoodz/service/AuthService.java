package com.example.semestrovkafoodz.service;

import com.example.semestrovkafoodz.dtos.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.semestrovkafoodz.entities.UserEntity;
import com.example.semestrovkafoodz.exceptions.AppError;
import com.example.semestrovkafoodz.utils.JwtTokenUtils;

import java.security.SignatureException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;

    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        } catch (BadCredentialsException e) {
            return new ResponseEntity<>(new AppError(HttpStatus.UNAUTHORIZED.value(), "Неправильный логин или пароль"), HttpStatus.UNAUTHORIZED);
        }
        UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(authRequest.getUsername());
        JwtResponse jwtResponse = jwtTokenUtils.generateToken(userDetails);

        return ResponseEntity.ok(jwtResponse);
    }

    public ResponseEntity<?> createNewUser(@RequestBody RegistrationUserDto registrationUserDto) {
        if (!registrationUserDto.getPassword().equals(registrationUserDto.getConfirmPassword())) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пароли не совпадают"), HttpStatus.BAD_REQUEST);
        }
        if (userDetailsServiceImpl.findByUsername(registrationUserDto.getUsername()).isPresent()) {
            return new ResponseEntity<>(new AppError(HttpStatus.BAD_REQUEST.value(), "Пользователь с указанным именем уже существует"), HttpStatus.BAD_REQUEST);
        }
        UserEntity userEntity = userDetailsServiceImpl.createNewUser(registrationUserDto);
        return ResponseEntity.ok(new UserDto(userEntity.getUserId(), userEntity.getUsername()));
    }

    public ResponseEntity<?> updateToken(@RequestBody TokenDto refreshToken) {
        JwtResponse jwtResponse = jwtTokenUtils.updateToken(refreshToken);
        return ResponseEntity.ok(jwtResponse);
    }
}
