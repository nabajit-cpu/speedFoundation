package com.example.user.user.model;


import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "users")
public class UserModel {

    private String name;
    private String email;
    private String password;

 
    // Getter for name
    public String getName() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name = name;
    }

    // Getter for name
    public String getPassword() {
        return name;
    }

    // Setter for name
    public void setPassword(String name) {
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
}
