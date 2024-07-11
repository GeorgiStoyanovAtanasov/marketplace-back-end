package com.example.demo.Event;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;

@Component
public class EventMapper {
    public Event toEntity(EventDTO eventDTO){
        Event event = new Event();
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setDuration(eventDTO.getDuration());
        event.setDescription(eventDTO.getDescription());
        event.setPlace(eventDTO.getPlace());
        event.setTime(eventDTO.getTime());
        event.setTicketPrice(eventDTO.getTicketPrice());
        event.setCapacity(eventDTO.getCapacity());
        try {
            byte[] fileContent = eventDTO.getFile().getBytes();
            String encodedImage = Base64.getEncoder().encodeToString(fileContent);
            event.setImage(encodedImage);
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        } catch (IllegalStateException e) {
            System.out.println("Invalid file input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }

        event.setOrganisation(eventDTO.getOrganisation());
        event.setEventType(eventDTO.getEventType());
        event.setEventStatus(EventStatus.AVAILABLE);
        return event;
    }
}
