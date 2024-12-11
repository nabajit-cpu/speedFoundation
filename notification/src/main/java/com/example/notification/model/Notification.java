package com.example.notification.model;

import lombok.Data;
import java.util.List;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "notifications")
@Data
public class Notification {

    @Id
    private Long id;

    private String notificationType;
    private List<String> emailRecipients;
    private List<String> phoneRecipients;
    private String notificationMessage;
    private String notificationSubject;

    // private NotificationStatus notificationStatus;

    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private String notificationErrorMessage;

    
}
