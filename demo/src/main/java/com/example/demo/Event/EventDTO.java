package com.example.demo.Event;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

public class EventDTO {
        @NotEmpty(message = "The name of the event can not be empty!")
        private String name;
        private String date;
        @NotNull(message = "Please enter the duration of the event!")
        private int duration;
        @NotEmpty(message = "Please enter description!")
        private String description;
        @NotEmpty(message = "Please enter the place of the event!")
        private String place;
        @NotEmpty(message = "Please enter the start time of the event!")
        private String time;
        @NotNull(message = "Please enter the price of the ticket for the event!")
        private double ticketPrice;
        @NotNull(message = "Please enter the capacity of the event!")
        private int capacity;
        private MultipartFile file;
        private Organisation organisation;
        private EventType eventType;

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

    public Organisation getOrganisation() {
            return organisation;
        }

        public void setOrganisation(Organisation organisation) {
            this.organisation = organisation;
        }

        public EventType getEventType() {
            return eventType;
        }

        public void setEventType(EventType eventType) {
            this.eventType = eventType;
        }

    }

