package com.example.events.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.events.model.EventModel;
import com.example.events.model.Participant;
import com.example.events.service.EventService;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/event")
@RequiredArgsConstructor
public class EventController {
    
    private final EventService eventService;

 
    @PostMapping("/createEvent")
    public ResponseEntity<?> createEvent(@RequestBody EventModel event) throws Exception{
        try {
            EventModel createdEvent = eventService.createEvent(event);
            return ResponseEntity.ok(createdEvent);
        } catch (Exception ex) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }
    @PutMapping("/updateEvent")
    public ResponseEntity<?> updateEvent(@RequestParam String id,@RequestBody EventModel eventDetails ) throws Exception{
        try {
            EventModel updatedEvent = eventService.updateEvent(id,eventDetails);
            return ResponseEntity.ok(updatedEvent);
        } catch (Exception ex) {
            // Return the exception message in the response
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
        }
    }

    @PostMapping("/addParticipant")
    public ResponseEntity<?> addParticipant() throws Exception{
        try{

            return ResponseEntity.ok(eventService.addParticipant(new Participant("Rahul","Dhing")));
        }catch(Exception ex){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());

        }
    }
    @GetMapping("/getEvents")
    public ResponseEntity<?> getEvents(){
        try {
            return ResponseEntity.ok(eventService.getEvents());
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
            
        }
    }

    @DeleteMapping("/deleteEvent")
    public ResponseEntity<?> deleteEvent(@RequestParam String id){
        try {
            eventService.deleteEvent(id);
            return ResponseEntity.ok("Event successfully deleted");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }


    // @PostMapping("/registerEvent")
    // public ResponseEntity<?> registerEvent


}
