package com.example.user.user.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;


@Service
public class  CloudinaryService{
    @Autowired
    private Cloudinary cloudinary;

    public Map upload(MultipartFile file) throws IOException {
        return cloudinary.uploader().upload(file.getBytes(),
            ObjectUtils.asMap("resource_type", "auto"));
    }

    public Map delete(String publicId) throws IOException {
        return cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
    }
}
