package com.example.events.kafka.consumer;


import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor


public class EventKafkaConsumer {
    @KafkaListener(topics="events-acknowledgement", groupId="events-service")
    public void consumeEvent(ConsumerRecord<String, String> record){
        String message = record.value();
        System.out.println("Acknowledgement message from notification: " + message);
    }
}
