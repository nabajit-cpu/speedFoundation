package com.example.user.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.user.exceptions.InvalidCredentialsException;
import com.example.user.user.model.LoginRequest;
import com.example.user.user.model.UserModel;
import com.example.user.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public UserModel register(UserModel user) throws Exception {
     
        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public List<UserModel> getUsers() {

        List<UserModel> list = new ArrayList<>();
        try {
            list = userRepository.findAll();

        } catch (Exception e) {
            System.out.println("Error in getUsers");
        }
        System.out.println("UserModel" + list);
        return list;

    }

    public UserModel login(LoginRequest loginRequest) throws InvalidCredentialsException{

        String email = loginRequest.getEmail();
        String password = loginRequest.getPassword();

        UserModel user = userRepository.findByEmail(email);

        if(email == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;


        
    }

}
