package com.example.EventHub.Organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class OrganisationService {

    OrganisationRepository organisationRepository;

    OrganisationMapper organisationMapper;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper=organisationMapper;
    }

    public void postUpdate(Integer id, OrganisationDTO updatedOrganisation) {
        Organisation organisation = organisationMapper.toEntity(updatedOrganisation);
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(id);
        if (optionalOrganisation.isPresent()) {
            organisation.setId(id);
            organisationRepository.save(organisation);
        }else {
            throw new IllegalArgumentException();
        }
    }

    public String delete(Integer id, Model model) {
        Organisation organisation = organisationRepository.findById(id).get();
        organisationRepository.delete(organisation);
        model.addAttribute("organisation", organisation);
        return "organisation-delete";
    }
    public List<OrganisationDTO> findOrganisationsByNameAndPermission(String name, OrganisationPermission organisationPermission){
        List<Organisation> organisations = organisationRepository.findByNameAndPermission(name, organisationPermission);
        List<OrganisationDTO> organisationDTOS = new ArrayList<>();
        for (int i = 0; i < organisations.size(); i++) {
            organisationDTOS.add(organisationMapper.toDTO(organisations.get(i)));
        }
        return organisationDTOS;
    }
}
