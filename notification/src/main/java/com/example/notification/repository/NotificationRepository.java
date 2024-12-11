package com.example.notification.repository;

import java.util.List;
import com.example.notification.model.Notification;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotificationRepository extends MongoRepository<Notification, String>{
}
