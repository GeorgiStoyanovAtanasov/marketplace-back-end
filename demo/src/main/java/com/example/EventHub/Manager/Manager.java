package com.example.EventHub.Manager;

import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.User.User;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Entity
@Table(name = "managers")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Integer id;

    @OneToOne
    private Organisation organisation;

    @OneToOne
    private User user;
}
