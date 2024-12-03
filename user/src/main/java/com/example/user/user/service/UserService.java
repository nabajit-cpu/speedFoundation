package com.example.user.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.user.exceptions.InvalidCredentialsException;
import com.example.user.user.model.AuthRequest;
import com.example.user.user.model.UserModel;
import com.example.user.user.repository.UserRepository;

@Service
public class UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel register(UserModel user) throws Exception {

        UserModel userModel = userRepository.findByEmail(user.getEmail());
        if (userModel != null) {
            throw new Exception("Email already registered");

        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }

    public UserModel login(AuthRequest authRequest) throws InvalidCredentialsException {

        String email = authRequest.getUsername();
        String password = authRequest.getPassword();

        UserModel user = userRepository.findByEmail(email);

        if (email == null || !passwordEncoder.matches(password, user.getPassword())) {
            throw new InvalidCredentialsException("Invalid email or password");
        }

        return user;

    }

    public UserModel uploadBio(UserModel user)throws Exception{
        UserModel userModel = userRepository.save(user);
        return userModel;
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

   

   
}
