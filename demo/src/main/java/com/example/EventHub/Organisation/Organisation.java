package com.example.EventHub.Organisation;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "organisations")
@Data
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;



}
