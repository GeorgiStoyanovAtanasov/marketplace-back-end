package com.example.EventHub.EventType;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/event-type")
public class EventTypeController {
    @Autowired
    EventTypeRepository eventTypeRepository;

    @PostMapping("/add")
    public void postProduct(@Valid @ModelAttribute EventType eventType, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        } else {
            eventTypeRepository.save(eventType);
        }
    }
    @GetMapping("/all")
    public Iterable<EventType> allEventsTypes(){
        Iterable<EventType> allEventTypes = eventTypeRepository.findAll();
        return allEventTypes;
    }
}
