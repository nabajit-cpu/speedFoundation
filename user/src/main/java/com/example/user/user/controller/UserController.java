package com.example.user.user.controller;

import com.example.user.user.model.UserModel;
import com.example.user.user.service.UserService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
        UserModel usermodel =  userService.register(user);
        return ResponseEntity.ok(usermodel);
    }

    @GetMapping("/users")
    public List<UserModel> getUsers(){
        
       
        List<UserModel> users = new ArrayList<>();
        try {
            users = userService.getUsers();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return users;
    }
}
