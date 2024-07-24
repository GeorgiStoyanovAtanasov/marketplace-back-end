package com.example.EventHub.Organisation;

import org.springframework.stereotype.Component;

@Component
public class OrganisationMapper {
    public OrganisationDTO toDTO(Organisation organisation) {
        if (organisation == null) {
            return null;
        }
        return new OrganisationDTO(organisation.getId(), organisation.getOrganisation_name());
    }

    public Organisation toEntity(OrganisationDTO organisationDTO) {
        if (organisationDTO == null) {
            return null;
        }
        Organisation organisation = new Organisation();
        organisation.setId(organisationDTO.getId());
        organisation.setOrganisation_name(organisationDTO.getOrganisation_name());
        return organisation;
    }
}

