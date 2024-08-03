package com.example.EventHub.EventType;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RestController
@RequestMapping("/event-type")
public class EventTypeController {

    private EventTypeRepository eventTypeRepository;
    private EventTypeMapper eventTypeMapper;
    private EventTypeService eventTypeService;

    @Autowired
    public EventTypeController(EventTypeRepository eventTypeRepository, EventTypeMapper eventTypeMapper, EventTypeService eventTypeService){
        this.eventTypeRepository = eventTypeRepository;
        this.eventTypeMapper = eventTypeMapper;
        this.eventTypeService=eventTypeService;
    }

    @PostMapping("/add")
    public void postProduct(@Valid @ModelAttribute EventTypeDTO eventTypeDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        } else {
            EventType eventType = eventTypeMapper.toEntity(eventTypeDTO);
            eventTypeRepository.save(eventType);
        }
    }
    @GetMapping("/all")
    public Iterable<EventType> allEventsTypes(){
        Iterable<EventType> allEventTypes = eventTypeRepository.findAll();
        return allEventTypes;
    }
    @DeleteMapping("/delete")
    public void deleteEventType(@RequestParam("id") Integer id){
        Optional<EventType> foundEventType = eventTypeRepository.findById(id);
        if(foundEventType.isPresent()){
            eventTypeRepository.deleteById(id);
        }
    }

    @PutMapping("/update")
    public void updateEventType(@RequestParam("id") Integer id, @RequestBody EventTypeDTO eventTypeDTO, BindingResult bindingResult){
        eventTypeService.updateEventType(id, eventTypeDTO, bindingResult);
    }
}
