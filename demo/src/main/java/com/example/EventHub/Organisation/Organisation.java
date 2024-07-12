package com.example.EventHub.Organisation;


import com.example.EventHub.User.User;
import jakarta.persistence.*;

import jakarta.validation.constraints.NotEmpty;


@Entity
@Table(name = "organisations")
public class Organisation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotEmpty(message = "Please enter the name of the organisation!")
    @Column(name = "organisation_name")
    private String organisationName;
    @OneToOne
    private User userId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }


}
