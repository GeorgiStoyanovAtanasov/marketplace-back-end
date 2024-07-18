package com.example.EventHub.Event;

import com.example.EventHub.EventStatus.EventStatus;
import com.example.EventHub.EventType.EventType;
import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.User.User;;
import jakarta.persistence.*;
import lombok.Data;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "events")
@Data
public class Event {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(unique = true)
    private String name;
    private String date;
    private int duration;
    private String description;
    private String place;
    private String time;
    private double ticketPrice;
    private int capacity;

    @Lob
    @Column(columnDefinition = "MEDIUMBLOB")
    private String image;
    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    @ManyToOne
    @JoinColumn(name = "event_type_id")
    private EventType eventType;

    @Enumerated(EnumType.STRING)
    private EventStatus eventStatus;
    @ManyToMany
    @JoinTable(
    name = "events_users",
    joinColumns = @JoinColumn(name = "event_id"),
    inverseJoinColumns = @JoinColumn(name = "user_id"))
    List<User>users;


    public EventStatus getEventStatus() {
        LocalDate localDate=LocalDate.now();
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date eventDate = dateFormat.parse(date);
            Date local = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
            if (eventDate.before(local)) {
                eventStatus=EventStatus.FINISHED;
            }
            else {
                eventStatus=EventStatus.AVAILABLE;
            }
        }catch (ParseException ex){
            System.out.println("Parsing error!" + ex);
        }
        if (users.size()>=capacity){
            eventStatus=EventStatus.FULL;
        }
        return this.eventStatus;
    }

}
