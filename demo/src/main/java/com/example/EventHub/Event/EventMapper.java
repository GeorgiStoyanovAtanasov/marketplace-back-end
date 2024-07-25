package com.example.EventHub.Event;

import com.example.EventHub.EventStatus.EventStatus;
import com.example.EventHub.EventType.EventTypeMapper;
import com.example.EventHub.EventType.EventTypeRepository;
import com.example.EventHub.Organisation.OrganisationMapper;
import com.example.EventHub.Organisation.OrganisationRepository;
import com.example.EventHub.User.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Base64;
import java.util.stream.Collectors;

@Component
public class EventMapper {
    @Autowired
    OrganisationMapper organisationMapper;
    @Autowired
    EventTypeMapper eventTypeMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    EventTypeRepository eventTypeRepository;
    public Event toEntity(EventDTO eventDTO){
        Event event = new Event();
        event.setId(eventDTO.getId());
        event.setName(eventDTO.getName());
        event.setDate(eventDTO.getDate());
        event.setDuration(eventDTO.getDuration());
        event.setDescription(eventDTO.getDescription());
        event.setPlace(eventDTO.getPlace());
        event.setTime(eventDTO.getTime());
        event.setTicketPrice(eventDTO.getTicketPrice());
//        event.setCapacity(eventDTO.getCapacity());
//        try {
//            byte[] fileContent = eventDTO.getFile().getBytes();
//            String encodedImage = Base64.getEncoder().encodeToString(fileContent);
//            event.setImage(encodedImage);
//        } catch (IOException e) {
//            System.out.println("Error reading file: " + e.getMessage());
//        } catch (IllegalStateException e) {
//            System.out.println("Invalid file input: " + e.getMessage());
//        } catch (Exception e) {
//            System.out.println("Unexpected error: " + e.getMessage());
//        }

        event.setOrganisation(organisationRepository.findByName(eventDTO.getOrganisation().getName()));
        event.setEventType(eventTypeRepository.findByTypeName(eventDTO.getEventTypeDTO().getTypeName()));
        event.setEventStatus(eventDTO.getEventStatus());

        return event;
    }
    public EventDTO toDTO(Event event) {
        return new EventDTO(
                event.getId(),
                event.getName(),
                event.getDate(),
                event.getDuration(),
                event.getDescription(),
                event.getPlace(),
                event.getTime(),
                event.getTicketPrice(),
                event.getCapacity(),
                event.getImage(),
                organisationMapper.toDTO(event.getOrganisation()),
                eventTypeMapper.toDTO(event.getEventType()),
                event.getEventStatus(),
                event.getUsers().stream().map(userMapper::toDTO).collect(Collectors.toList())
        );
    }
}
