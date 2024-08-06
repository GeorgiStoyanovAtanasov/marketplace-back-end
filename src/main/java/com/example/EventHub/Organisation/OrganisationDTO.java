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
    private String name;
}
