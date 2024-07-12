package com.example.EventHub.Event;


import com.example.EventHub.EventType.EventType;
import com.example.EventHub.EventType.EventTypeDTO;
import com.example.EventHub.EventType.EventTypeMapper;
import com.example.EventHub.Organisation.OrganisationRepository;
import com.example.EventHub.User.UserRepository;
import com.example.EventHub.EventType.EventTypeRepository;
import com.example.EventHub.User.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/event")
public class EventController {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    EventService eventService;
    @Autowired
    EventMapper eventMapper;
    @Autowired
    EventTypeMapper eventTypeMapper;
    @Autowired
    UserRepository userRepository;

    @GetMapping("/add")
    public String addEvent(Model model) {
        model.addAttribute("eventDTO", new EventDTO());
        model.addAttribute("eventTypes", eventTypeRepository.findAll());
        model.addAttribute("organisations", organisationRepository.findAll());
        return "event-form";
    }

    @PostMapping("/submit")
    public String postEvent(@Valid @ModelAttribute EventDTO eventDTO, BindingResult bindingResult, Model model) throws ParseException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("eventTypes", eventTypeRepository.findAll());
            model.addAttribute("organisations", organisationRepository.findAll());
            return "event-form";
        }
        if (eventService.errorEventStatus(eventDTO)) {
            model.addAttribute("eventTypes", eventTypeRepository.findAll());
            model.addAttribute("organisations", organisationRepository.findAll());
            model.addAttribute("notValidDate", "Please enter a valid date!");
            return "event-form";
        } else {
            Event event = eventMapper.toEntity(eventDTO);
            eventRepository.save(event);
            model.addAttribute("event", event);
            return "home";
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Map<String, List<?>>> getAllEventsAndTypes() {
        List<Event> allEvents = (List<Event>) eventRepository.findAll();
        List<EventType> allTypes = (List<EventType>) eventTypeRepository.findAll();

        List<EventDTO> eventDTOs = new ArrayList<>();
        for (int i = 0; i < allEvents.size(); i++) {
            eventDTOs.add(eventMapper.toDTO(allEvents.get(i)));
        }
        List<EventTypeDTO> eventTypeDTOs = allTypes.stream().map(eventTypeMapper::toDTO).collect(Collectors.toList());

        Map<String, List<?>> response = new HashMap<>();
        response.put("events", eventDTOs);
        response.put("eventTypes", eventTypeDTOs);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{eventName}")
    public ResponseEntity<Map<String, EventDTO>> getEventDetails(@PathVariable String eventName) {
        Map<String, EventDTO> response = new HashMap<>();
        Event event = eventRepository.findByName(eventName);
        if (event != null) {
            EventDTO eventDTO = eventMapper.toDTO(event);
            response.put("event", eventDTO);
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        }
    }

    @GetMapping("/search")
    public String searchEvents(@RequestParam(name = "name", required = false) String name,
                               @RequestParam(name = "place", required = false) String place,
                               @RequestParam(name = "type", required = false) Integer type,
                               @RequestParam(name = "date", required = false) String date,
                               @RequestParam(name = "minPrice", required = false) Double minPrice,
                               @RequestParam(name = "maxPrice", required = false) Double maxPrice,
                               Model model) {
        return eventService.searchEvents(name, place, type, date, minPrice, maxPrice, model);
    }

    @GetMapping("/update")
    public String updateProductForm(@RequestParam("id") Integer id, Model model) {
        return eventService.updateForm(id, model);
    }

    @PostMapping("/update")
    public String postUpdatedProduct(@RequestParam("id") Integer id, @Valid @ModelAttribute Event updatedEvent, BindingResult bindingResult, Model model) {
        return eventService.postUpdate(id, updatedEvent, bindingResult, model);
    }

    @PostMapping("/delete")
    public String delete(@RequestParam("id") Integer id, Model model) {
        return eventService.delete(id, model);
    }


    @PostMapping("/apply")
    public String apply(@RequestParam(name = "eventId") Integer id, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.getUserByUsername(username);
        Event event = eventRepository.findById(id).get();
        List<Event> events = user.getEvents();
        for (Event listEvent : events) {
            if (listEvent.equals(event)) {
                model.addAttribute("alreadyApplied", "You have already applied for this event!");
                model.addAttribute("event", event);
                return "event-details";
            }
        }
        event.getUsers().add(user);
        user.getEvents().add(event);
        userRepository.save(user);
        eventRepository.save(event);

        model.addAttribute("event", event);
        model.addAttribute("successfullyApplied", "You have successfully applied for the event!");
        return "event-details";
    }
}

