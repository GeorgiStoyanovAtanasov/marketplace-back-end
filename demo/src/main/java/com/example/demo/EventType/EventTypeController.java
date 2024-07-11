package com.example.EventHub.EventType;

import com.example.EventHub.Event.Event;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/event-type")
public class EventTypeController {
    @Autowired
    EventTypeRepository eventTypeRepository;
    @GetMapping("/add")
    public String addEvent(Model model){
        model.addAttribute("eventType", new EventType());
        model.addAttribute("eventTypes", eventTypeRepository.findAll());
        return "event-type-form";
    }
    @PostMapping("/submit")
    public String postProduct(@Valid @ModelAttribute EventType eventType, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "event-type-form";
        } else {
            eventTypeRepository.save(eventType);
            model.addAttribute("eventType", eventType);
            return "event-type-result";
        }
    }
    @GetMapping("/all")
    public String allEvents(Model model){
        Iterable<EventType> allEventTypes = eventTypeRepository.findAll();
        model.addAttribute("allEventTypes", allEventTypes);
        return "all-event-types";
    }
}
