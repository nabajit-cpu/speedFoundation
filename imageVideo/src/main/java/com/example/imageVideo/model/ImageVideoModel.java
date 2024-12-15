package com.example.imageVideo.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "imageVideo")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageVideoModel {
    @Id
    private String id;
    private String signature;
    private String format;
    private String location;
    private String resourceType;
    private String secureUrl;
    private String originalFilename;
    private String createdAt;

}
