package com.example.notification.kafka.consumer;

import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.notification.kafka.producer.NotificationKafkaProducer;
import com.example.notification.service.NotificationService;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class NotificationKafkaConsumer {

    private final NotificationService notificationService;
    private final KafkaTemplate<String,String> kafkaTemplate;
    private final NotificationKafkaProducer notificationKafkaProducer;

    @KafkaListener(topics = "events-notification", groupId = "notification-service")
    public void consumeUserEvent(ConsumerRecord<String, String> record) {
        String message = record.value();
        System.out.println("Received event: " + message);

        // Simulate sending a notification
        Map<String, Boolean> results = notificationService.sendNotification(message);

        String acknowledgmentMessage = String.format(
            "Acknowledgment: Event '%s' processed successfully: %b", 
            message, results.toString()
        );
        notificationKafkaProducer.produceEvent(acknowledgmentMessage);
   
    }
}
