package com.example.user.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

    public UserModel register(UserModel user) throws Exception {



        System.out.println("service - inside register ");
        UserModel userModel = userRepository.findByEmail(user.getEmail());
        if(userModel!=null){
            throw new Exception("Email already registered");
       
        }
        System.out.println("service - before hash ");
       
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

        if(email == null  || password==null){
            throw new InvalidCredentialsException("Empty credential");
        }

        UserModel user = userRepository.findByEmail(email);

        if(user == null || !passwordEncoder.matches(password, user.getPassword())){
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;


        
    }

}
