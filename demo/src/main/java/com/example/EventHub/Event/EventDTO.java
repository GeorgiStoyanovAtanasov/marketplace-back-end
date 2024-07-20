package com.example.EventHub.Event;

import com.example.EventHub.EventStatus.EventStatus;
import com.example.EventHub.EventType.EventType;
import com.example.EventHub.EventType.EventTypeDTO;
import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.Organisation.OrganisationDTO;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserDTO;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
@Data
public class EventDTO {
    private Integer id;
    @NotEmpty(message = "The name of the event can not be empty!")
    private String name;
    private String date;
    @NotNull(message = "Please enter the duration of the event!")
    private int duration;
    @NotEmpty(message = "Please enter description!")
    private String description;
    private String image;
    @NotEmpty(message = "Please enter the place of the event!")
    private String place;
    @NotEmpty(message = "Please enter the start time of the event!")
    private String time;
    @NotNull(message = "Please enter the price of the ticket for the event!")
    private double ticketPrice;
    @NotNull(message = "Please enter the capacity of the event!")
    private int capacity;
    private MultipartFile file;
    private OrganisationDTO organisationDTO;
    private EventTypeDTO eventTypeDTO;
    private EventStatus eventStatus;


    public EventDTO() {
    }

    public EventDTO(Integer id, String name, String date, int duration, String description, String place, String time, double ticketPrice, int capacity, String image, OrganisationDTO dto, EventTypeDTO dto1, EventStatus eventStatus, List<UserDTO> users) {
        this.name = name;
        this.date = date;
        this.duration = duration;
        this.description = description;
        this.place = place;
        this.time = time;
        this.ticketPrice = ticketPrice;
        this.capacity = capacity;
        this.image = image;
        this.organisationDTO = dto;
        this.eventTypeDTO = dto1;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setEventTypeDTO(EventTypeDTO eventTypeDTO) {
        this.eventTypeDTO = eventTypeDTO;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }
}

