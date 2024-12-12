package com.example.events.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.example.events.model.Participant;

public interface  ParticipantRepository extends MongoRepository<Participant, String>{
    Participant findParticipantById(String id);
}
