package com.example.imageVideo.controller;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.api.exceptions.BadRequest;
import com.example.imageVideo.model.ImageVideoModel;
import com.example.imageVideo.service.CloudinaryService;
import com.example.imageVideo.service.ImageVideoService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@RestController
@Data
@RequestMapping("/imageVideo")
@RequiredArgsConstructor
public class ImageVideoController {
    private final ImageVideoService imageVideoService;
    private final CloudinaryService cloudinaryService;

    @GetMapping("/test")
    public String test() {
        return "testing";
    }

    @PostMapping("/uploadMedia")
    public ResponseEntity<ImageVideoModel> uploadMedia(@RequestBody MultipartFile file, @RequestParam String location)
            throws IOException {
        Map result = cloudinaryService.upload(file);
        ImageVideoModel savedImageVideoModel = imageVideoService.uploadMediaDetails(result, location);
        return ResponseEntity.ok(savedImageVideoModel);
    }

    @GetMapping("/getAllMediaByModelDetails")
    public ResponseEntity<?> getAllMediaByModelDetails(@RequestBody ImageVideoModel imageVideoModel) throws Exception {
        try {
            List<ImageVideoModel> list = imageVideoService.getAllMediaByModelDetails(imageVideoModel);
            if(list.isEmpty()){
                return ResponseEntity.ok("No details of: " + imageVideoModel.getLocation());
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());

        }
    }
    @GetMapping("/getAllMedia")
    public ResponseEntity<?> getAllMedia() throws Exception {
        try {
            List<ImageVideoModel> list = imageVideoService.getAllMedia();
            if(list.isEmpty()){
                return ResponseEntity.ok("No available images/videos");
            }
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

}
