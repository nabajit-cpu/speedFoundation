package com.example.imageVideo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.imageVideo.model.ImageVideoModel;

public interface ImageVideoRepository extends MongoRepository<ImageVideoModel, String>{
     List<ImageVideoModel> findByLocation(String location);
     List<ImageVideoModel> findByResourceType(String resourceType);
}
