package com.example.EventHub.Event;

import com.example.EventHub.EventType.EventTypeDTO;

import java.util.List;

public class EventsResponse {
    private List<EventDTO> events;
    private List<EventTypeDTO> eventTypes;

    public EventsResponse() {}

    public EventsResponse(List<EventDTO> events, List<EventTypeDTO> eventTypes) {
        this.events = events;
        this.eventTypes = eventTypes;
    }

    // Getters and setters
}


