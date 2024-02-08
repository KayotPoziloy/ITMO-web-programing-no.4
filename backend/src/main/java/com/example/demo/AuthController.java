package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    Logger logger = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody UserDTO req) {
        authService.register(req.getLogin(), req.getPassword());
        logger.info("User {} registered", req.getLogin());
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);

    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authorization) {
        authService.check(authorization);
        logger.info("User registered");
        return new ResponseEntity<>("Success", HttpStatus.ACCEPTED);
    }
}
