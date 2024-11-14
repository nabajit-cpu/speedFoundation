package com.example.user.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {

        return new Cloudinary(Dotenv.load().get("CLOUDINARY_URL"));

    }
}
