package com.example.user.user.model;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Document(collection = "users")
public class UserModel implements UserDetails {

    private String name;
    private String email;
    private String password;
    private String role; // Admin, Member
    private String profilePicture;
    private String bio;
    private Date registrationDate;

    // Default Constructor
    public UserModel() {
    }

    // Parameterized Constructor (optional)
    public UserModel(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for email
    public String getEmail() {
        return email;
    }

    // Setter for email
    public void setEmail(String email) {
        this.email = email;
    }

    // Getter for password
    public String getPassword() {
        return password;
    }

    // Setter for password
    public void setPassword(String password) {
        this.password = password;
    }

    // Getter for role
    public String getRole() {
        return role;
    }

    // Setter for role
    public void setRole(String role) {
        this.role = role;
    }

    // Getter for profilePicture
    public String getProfilePicture() {
        return profilePicture;
    }

    // Setter for profilePicture
    public void setProfilePicture(String profilePicture) {
        this.profilePicture = profilePicture;
    }

    // Getter for bio
    public String getBio() {
        return bio;
    }

    // Setter for bio
    public void setBio(String bio) {
        this.bio = bio;
    }

    // Getter for registrationDate
    public Date getRegistrationDate() {
        return registrationDate;
    }

    // Setter for registrationDate
    public void setRegistrationDate(Date registrationDate) {
        this.registrationDate = registrationDate;
    }

   
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
