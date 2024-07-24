package com.example.EventHub.Organisation;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrganisationDTO {
    private Integer id;

    @NotEmpty(message = "Please enter the name of the organisation!")
    @Column(unique = true)
    private String organisation_name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOrganisation_name() {
        return organisation_name;
    }

    public void setOrganisation_name(String organisation_name) {
        this.organisation_name = organisation_name;
    }
}
