package com.example.user.user.model;

import java.sql.Date;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserModel {

    private String name;
    private String email;
    private String password;
    private String role; // Admin, Member
    private String profilePicture;
    private String bio;
    private Date registrationDate;

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
}
