package com.example.user.user.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cloudinary.Cloudinary;

import io.github.cdimascio.dotenv.Dotenv;

@Configuration
public class CloudinaryConfiguration {

    @Bean
    public Cloudinary cloudinary() {
        // Load .env file from the parent directory
        Dotenv dotenv = Dotenv.configure()
                             .directory("../")  // Point to the parent directory
                             .load();

        return new Cloudinary(dotenv.get("CLOUDINARY_URL"));
    }
}
