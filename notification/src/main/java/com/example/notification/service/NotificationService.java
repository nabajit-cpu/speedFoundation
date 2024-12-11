package com.example.notification.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.notification.model.NotificationInterface;
import com.example.notification.repository.NotificationRepository;

import java.util.Map;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class NotificationService {

    private final List<NotificationInterface> notificationMethods;

    private final NotificationRepository notificationRepository;

    public Map<String, Boolean> sendNotification(String message) {
        Map<String, Boolean> results = new HashMap<>();    
        for(NotificationInterface notificationMethod : notificationMethods){
            Boolean sentStatus = notificationMethod.sendNotification(message);
            results.put(notificationMethod.getType(), sentStatus);
        }

        return results;
        
    }

    
}
