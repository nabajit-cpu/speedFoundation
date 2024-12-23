package com.example.user.user.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.user.user.model.UserModel;
import com.example.user.user.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserModel register(UserModel user) throws Exception {

        System.out.println("Inside register service");

        UserModel userModel = userRepository.findByEmail(user.getEmail());
        if (userModel != null) {
            throw new Exception("Email already registered");

        }

        String hashedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(hashedPassword);
        return userRepository.save(user);
    }


    public UserModel uploadBio(UserModel userModel) throws Exception {
        // Retrieve the currently logged-in user's username (or another unique identifier)
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        System.out.println("email : " + email);
        // Find the current user in the database
        UserModel currentUser = userRepository.findByEmail(email);
        // Update the bio for the current user
        currentUser.setBio(userModel.getBio());

        // Save the updated user back to the database
        return userRepository.save(currentUser);

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


    public Optional<UserModel> getUser(String id) {
        return userRepository.findById(id);
    }

}
