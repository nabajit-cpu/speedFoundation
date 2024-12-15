package com.example.imageVideo.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.imageVideo.model.ImageVideoModel;
import com.example.imageVideo.repository.ImageVideoRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageVideoService {
    private final ImageVideoRepository imageVideoRepository;

    public ImageVideoModel uploadMediaDetails(Map cloudinaryDetails, String location) {
        ImageVideoModel imageVideoModel = new ImageVideoModel(
                null,
                (String) cloudinaryDetails.get("signature"),
                (String) cloudinaryDetails.get("format"),
                location,
                (String) cloudinaryDetails.get("resource_type"),
                (String) cloudinaryDetails.get("secure_url"),
                (String) cloudinaryDetails.get("original_filename"),
                (String) cloudinaryDetails.get("created_at"));

        return imageVideoRepository.save(imageVideoModel);
    }

    public List<ImageVideoModel> getAllMediaByModelDetails(ImageVideoModel imageVideoModel) {
        // List<ImageVideoModel> list = imageVideoRepository.findByLocation(imageVideoModel.getLocation());
        List<ImageVideoModel> list = imageVideoRepository.findByResourceType(imageVideoModel.getResourceType());
        return list;

    }

    public List<ImageVideoModel> getAllMedia() throws Exception {
        List<ImageVideoModel> list = imageVideoRepository.findAll();
        return list;
    }
}
