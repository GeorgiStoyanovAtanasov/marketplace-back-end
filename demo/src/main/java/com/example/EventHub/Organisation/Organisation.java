package com.example.EventHub.Organisation;


import com.example.EventHub.User.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Entity
@Table(name = "organisations")
@Data
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "Please enter the name of the organisation!")
    private String name;



}
