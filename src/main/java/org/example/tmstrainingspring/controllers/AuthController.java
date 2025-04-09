package org.example.tmstrainingspring.controllers;

import org.example.tmstrainingspring.components.JwtUtil;
import org.example.tmstrainingspring.dtos.UserDTO;
import org.example.tmstrainingspring.entities.UserModel;
import org.example.tmstrainingspring.exceptions.ForbiddenException;
import org.example.tmstrainingspring.exceptions.NotFoundException;
import org.example.tmstrainingspring.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("login")
    public ResponseEntity<String> login(@RequestBody UserModel user) {
        UserModel savedUser = userService.findByUsername(user.getUsername());

        if (savedUser == null) {
            throw new NotFoundException("User not found");
        }

        if (passwordEncoder.matches(user.getPassword(), savedUser.getPassword())) {
            return ResponseEntity.ok(JwtUtil.generateJWT(new UserDTO(savedUser)));
        }

        throw new ForbiddenException("Password Incorrect");
    }
}
