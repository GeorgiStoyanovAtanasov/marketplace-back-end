package com.example.EventHub.Organisation;

import jakarta.persistence.Column;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationDTO {
    private Integer id;

    @NotEmpty(message = "Please enter the name of the organisation!")
    @Column(unique = true)
    private String name;

    @Enumerated(EnumType.STRING)
    private OrganisationPermission organisationPermission;
}
