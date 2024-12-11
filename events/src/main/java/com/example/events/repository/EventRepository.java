package com.example.events.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.events.model.EventModel;

public interface  EventRepository extends MongoRepository<EventModel, String>{
    EventModel findByEventName(String id);
}

