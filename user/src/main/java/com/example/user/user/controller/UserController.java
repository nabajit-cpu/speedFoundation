package com.example.user.user.controller;

import com.example.user.user.exceptions.InvalidCredentialsException;
import com.example.user.user.model.LoginRequest;
import com.example.user.user.model.UserModel;
import com.example.user.user.service.UserService;

import java.io.InvalidClassException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel user) throws Exception {
        UserModel usermodel = userService.register(user);
        return ResponseEntity.ok(usermodel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        try {
            UserModel user = userService.login(loginRequest);
            return ResponseEntity.ok(user);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }

    }

    @GetMapping("/users")
    public List<UserModel> getUsers() throws Exception {
        return userService.getUsers();

    }
}
