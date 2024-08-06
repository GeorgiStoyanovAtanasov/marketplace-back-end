package com.example.EventHub.EventType;

import org.springframework.stereotype.Component;

@Component
public class EventTypeMapper {
    public EventTypeDTO toDTO(EventType eventType) {
        if (eventType == null) {
            return null;
        }
        return new EventTypeDTO(eventType.getId(), eventType.getTypeName());
    }

    public EventType toEntity(EventTypeDTO eventTypeDTO) {
        if (eventTypeDTO == null) {
            return null;
        }
        EventType eventType = new EventType();
        eventType.setId(eventTypeDTO.getId());
        eventType.setTypeName(eventTypeDTO.getTypeName());
        return eventType;
    }
}
