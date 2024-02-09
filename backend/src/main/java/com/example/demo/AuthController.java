package com.example.demo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
        try {
            authService.register(req.getLogin(), req.getPassword());
            logger.info("Пользователь {} успешно зарегистрирован", req.getLogin());

            String token = authService.createToken(req.getLogin());
            return ResponseEntity.ok(token);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/api/login")
    public ResponseEntity<String> login(@RequestHeader("Authorization") String authorization) {
        try {
            authService.check(authorization);
            logger.info("Пользователь есть");
            String token = authService.check(authorization);
            return ResponseEntity.ok(token);
        } catch (ResponseStatusException e) {
            return new ResponseEntity<>(e.getReason(), e.getStatusCode());
        }
    }
}
