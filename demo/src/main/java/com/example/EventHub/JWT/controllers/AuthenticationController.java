package com.example.EventHub.JWT.controllers;

import com.example.EventHub.JWT.dtos.LoginUserDto;
import com.example.EventHub.JWT.dtos.RegisterUserDto;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.User.User;
import com.example.EventHub.JWT.responses.LoginResponse;
import com.example.EventHub.JWT.services.AuthenticationService;
import com.example.EventHub.JWT.services.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/auth")
@RestController
public class AuthenticationController {
    private final JwtService jwtService;
    private final AuthenticationService authenticationService;

    public AuthenticationController(JwtService jwtService, AuthenticationService authenticationService) {
        this.jwtService = jwtService;
        this.authenticationService = authenticationService;
    }

    @PostMapping("/register/user")
    public boolean registerUser(@RequestBody RegisterUserDto registerUserDto) {
        return authenticationService.signupUser(registerUserDto);


    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticate(@RequestBody LoginUserDto loginUserDto) {
        User authenticatedUser = authenticationService.authenticate(loginUserDto);

        String jwtToken = jwtService.generateToken(authenticatedUser);

        LoginResponse loginResponse = new LoginResponse().setToken(jwtToken).setExpiresIn(jwtService.getExpirationTime());

        return ResponseEntity.ok(loginResponse);
    }
}