package com.example.events.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.events.kafka.producer.EventKafkaProducer;
import com.example.events.model.EventModel;
import com.example.events.model.Participant;
import com.example.events.repository.EventRepository;

import lombok.Data;

@Service
@Data
public class EventService {
   
    private final EventRepository eventRepository;

    // kafka Producer -> events to notification
    private final EventKafkaProducer eventProducer;


    public EventModel createEvent(EventModel event) throws Exception{
        if(eventRepository.findByEventName(event.getEventName())!=null){
            throw new Exception("Same Event Name already exist! Change the Event Name");
        }
        
        EventModel eventModel = eventRepository.save(event);
        if(eventModel!=null){
            String eventData = eventModel.getEventName();
            // produce kafka event
            eventProducer.produceEvent(eventData);
        }

        return eventModel;
    }

    public EventModel updateEvent(String id, EventModel event) throws Exception {
        // Check if another event with the same name exists
        EventModel existingEvent = eventRepository.findByEventName(event.getEventName());
        if (existingEvent != null && existingEvent.getEventName().equals(event.getEventName())) {
            throw new Exception("An event with the same name already exists! Please use a different name.");
        }
    
        // Find the event by ID
        Optional<EventModel> optionalEvent = eventRepository.findById(id);
        if (optionalEvent.isEmpty()) {
            throw new Exception("Event with ID " + id + " not found!");
        }
    
        // Update the event details
        EventModel editEvent = optionalEvent.get();
        editEvent.setOrganizerId(event.getOrganizerId());
        editEvent.setEventName(event.getEventName());
        editEvent.setEventDate(event.getEventDate());
        editEvent.setEventLocation(event.getEventLocation());
        editEvent.setEventDescription(event.getEventDescription());
        editEvent.setEventParticipants(event.getEventParticipants());
        editEvent.setEventActivities(event.getEventActivities());
        // Add more fields as needed
    
        // Save and return the updated event
        return eventRepository.save(editEvent);
    }
    
    public EventModel addParticipant(Participant p) throws Exception{
        EventModel event  = eventRepository.findByEventName("Create");
        if(event==null){
            throw new Exception("Event is not found");
        }
        event.addParticipant(p);
        return eventRepository.save(event);
    }

    public List<EventModel> getEvents() {
        return eventRepository.findAll();
    }

    public void deleteEvent(String id) throws Exception{
        if(!eventRepository.existsById(id)){
            throw new Exception("Event with ID " + id + " not found");
        }
        eventRepository.deleteById(id);
    }
}
