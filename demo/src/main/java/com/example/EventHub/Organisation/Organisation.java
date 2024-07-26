package com.example.EventHub.Organisation;


import com.example.EventHub.User.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "organisations")
@Data
@NoArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "Please enter the name of the organisation!")
    @Column(unique = true)
    private String name;

    public Organisation(String name) {
        this.name = name;
    }
}
