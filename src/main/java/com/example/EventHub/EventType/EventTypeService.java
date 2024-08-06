package com.example.EventHub.EventType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
@Service
public class EventTypeService {
    private EventTypeMapper eventTypeMapper;
    private EventTypeRepository eventTypeRepository;

    @Autowired
    public EventTypeService(EventTypeMapper eventTypeMapper, EventTypeRepository eventTypeRepository) {
        this.eventTypeMapper = eventTypeMapper;
        this.eventTypeRepository = eventTypeRepository;
    }

    public void updateEventType(Integer id, EventTypeDTO eventTypeDTO, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        } else {
            EventType eventType = eventTypeMapper.toEntity(eventTypeDTO);
            eventType.setId(id);
            eventTypeRepository.save(eventType);
        }
    }
}
