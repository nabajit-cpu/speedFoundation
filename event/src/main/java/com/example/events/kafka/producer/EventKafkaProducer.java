package com.example.events.kafka.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Service
@Data
@RequiredArgsConstructor
public class EventKafkaProducer {

    private static final String TOPIC = "events-notification";

    private final KafkaTemplate<String, String> kafkaTemplate;

    public void produceEvent(String eventData) {
        kafkaTemplate.send(TOPIC, eventData);
        System.out.println("Event sent to notification service");
    }
}
