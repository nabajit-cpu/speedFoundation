package com.example.events.model;

import java.time.LocalDate;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;


@Document(collection ="participants")
public class Participant {
    @Id 
    private String id;
    private String name;
    private String phnNumber;
    private String fatherName;
    private String motherName;
    private String location;
    private LocalDate DOB;
    
    public Participant(String name, String location) {
        this.name = name;
        this.location = location;
    }
}
