package com.example.EventHub.Organisation;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "organisations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private String name;
    @Enumerated(EnumType.STRING)
    private OrganisationPermission organisationPermission;

    public Organisation(String name, OrganisationPermission organisationPermission) {
        this.name = name;
        this.organisationPermission = organisationPermission;
    }
}
