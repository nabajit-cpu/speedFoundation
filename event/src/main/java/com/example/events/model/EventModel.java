package com.example.events.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.example.events.model.Participant;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;


@Document(collection="events")
@Data
public class EventModel {
    @Id
    private String id;

    @Indexed(unique=true)
    private String eventName;

    @Indexed(unique=true)
    private String organizerId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate eventDate;
    private String eventLocation;
    private String eventDescription;
    
    private List<Activity> eventActivities;
    private List<Participant> eventParticipants;

    public EventModel(){
        eventActivities = new ArrayList<>();
        eventParticipants = new ArrayList<>();
    }

    public void addParticipant(Participant participant){
        eventParticipants.add(participant);
    }


    
}
