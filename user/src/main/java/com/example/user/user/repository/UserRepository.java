package com.example.user.user.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.user.user.model.UserModel;

public interface UserRepository extends MongoRepository<UserModel, Long> {

    UserModel findByEmail(String email);

}