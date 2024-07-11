package com.example.EventHub.Event;



import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


import java.text.ParseException;
import java.util.List;
import java.util.Optional;


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
    public String allEvents(Model model) throws ParseException {
        Iterable<Event> allEvents = eventRepository.findAll();
        model.addAttribute("allEvents", allEvents);
        model.addAttribute("allTypes", eventTypeRepository.findAll());
        return "all-events";
    }

    @GetMapping("/{eventId}")
    public String getEventDetails(@PathVariable Integer eventId, Model model) {
        Optional<Event> optionalEvent = eventRepository.findById(eventId);
        if (optionalEvent.isPresent()) {
            Event event = optionalEvent.get();
            model.addAttribute("event", event);
            return "event-details";
        } else {
            return "id could not be find";
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

