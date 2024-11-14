package com.example.user.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.user.user.exceptions.InvalidCredentialsException;
import com.example.user.user.model.LoginRequest;
import com.example.user.user.model.UserModel;
import com.example.user.user.service.CloudinaryService;
import com.example.user.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CloudinaryService cloudinaryService;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel user) throws Exception {
        System.out.println("Hello register");
        UserModel usermodel = userService.register(user);
        return ResponseEntity.ok(usermodel);
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody LoginRequest loginRequest) {

        try {
            UserModel user = userService.login(loginRequest);
            return ResponseEntity.ok(user);
        } catch (InvalidCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }

    }

    @PostMapping("/uploadImage")
    public ResponseEntity<Map> uploadImage(@RequestBody MultipartFile file) throws  IOException{
        Map result = cloudinaryService.upload(file);
        return ResponseEntity.ok(result);
    }
    @PostMapping("/submitUserDetails")
    public UserModel submitUserDetails(@RequestBody UserModel user) throws Exception{
        UserModel userModel = userService.submitUserDetails(user);
        // return ResponseEntity.ok(userModel);
        return userModel;
    }

    @GetMapping("/users")
    public List<UserModel> getUsers() throws Exception {
        return userService.getUsers();

    }
}
