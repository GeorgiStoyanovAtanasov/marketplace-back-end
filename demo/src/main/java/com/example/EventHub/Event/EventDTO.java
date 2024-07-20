package com.example.EventHub.Event;

import com.example.EventHub.EventStatus.EventStatus;
import com.example.EventHub.EventType.EventType;
import com.example.EventHub.EventType.EventTypeDTO;
import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.Organisation.OrganisationDTO;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserDTO;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class EventDTO {
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
    private OrganisationDTO organisation;
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
        this.organisation = dto;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public double getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(double ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public OrganisationDTO getOrganisation() {
        return organisation;
    }

    public void setOrganisation(OrganisationDTO organisationDTO) {
        this.organisation = organisationDTO;
    }

    public EventTypeDTO getEventTypeDTO() {
        return eventTypeDTO;
    }

    public void setEventTypeDTo(EventTypeDTO eventTypeDTO) {
        this.eventTypeDTO = eventTypeDTO;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
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

