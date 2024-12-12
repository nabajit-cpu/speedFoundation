package com.example.notification.kafka.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class NotificationKafkaProducer{
    private static final String TOPIC = "events-acknowledgement";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produceEvent(String message){
        kafkaTemplate.send(TOPIC,message);
        System.out.println("Notification acknowledgement sent to events-service !! ");
    }


}