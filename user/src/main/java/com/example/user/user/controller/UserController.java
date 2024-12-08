package com.example.user.user.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.user.security.JwtHelper;
import com.example.user.security.JwtRequest;
import com.example.user.security.JwtResponse;
import com.example.user.user.model.UserModel;
import com.example.user.user.service.CloudinaryService;
import com.example.user.user.service.CustomUserDetailsService;
import com.example.user.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

     @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private CloudinaryService cloudinaryService;

      @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtHelper jwtHelper;

    @PostMapping("/register")
    public ResponseEntity<UserModel> registerUser(@RequestBody UserModel user) throws Exception {
        UserModel usermodel = userService.register(user);
        return ResponseEntity.ok(usermodel);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request) {

        this.doAuthenticate(request.getEmail(), request.getPassword());

       UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtHelper.generateToken(userDetails);

        JwtResponse response = JwtResponse.builder()
                .jwtToken(token)
                .username(userDetails.getUsername()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, password);
        try {
            authenticationManager.authenticate(authentication);


        } catch (BadCredentialsException e) {
            throw new BadCredentialsException(" Invalid Username or Password  !!");
        }

    }

    @ExceptionHandler(BadCredentialsException.class)
    public String exceptionHandler() {
        return "Credentials Invalid !!";
    }

    @PostMapping("/uploadImage")
    public ResponseEntity<Map> uploadImage(@RequestBody MultipartFile file) throws IOException {
        Map result = cloudinaryService.upload(file);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/uploadBio")
    public ResponseEntity<UserModel> uploadBio(@RequestBody UserModel user) throws Exception {
        UserModel userModel = userService.uploadBio(user);
        return ResponseEntity.ok(userModel);

    }

    @GetMapping("/users")
    public List<UserModel> getUsers() throws Exception {
        return userService.getUsers();

    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestBody String jwtToken) {
        jwtHelper.invalidateToken(jwtToken);

        return new ResponseEntity<>("Logged out successfully!", HttpStatus.OK);
    }
}
