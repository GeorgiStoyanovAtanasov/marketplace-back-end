package com.example.EventHub.Organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@Service
public class OrganisationService {
    @Autowired
    OrganisationRepository organisationRepository;
    public String updateForm(Integer id, Model model) {

        Optional<Organisation> optionalOrganisation = organisationRepository.findById(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation = organisationRepository.findById(id).get();
            model.addAttribute("updateOrganisation", organisation);
            return "organisation-update-form";
        } else {
            return "id could not be found";
        }
    }

    public String postUpdate(Integer id, Organisation updatedOrganisation, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return "organisation-update-form";
        } else {
            Organisation organisation = organisationRepository.findById(id).get();
            getOrganisation(organisation, updatedOrganisation);
            organisationRepository.save(organisation);
            model.addAttribute("organisation", organisation);
            return "organisation-update-result";
        }
    }

    private Organisation getOrganisation(Organisation organisation, Organisation updatedOrganisation) {
        organisation.setOrganisationName(updatedOrganisation.getOrganisationName());
        return organisation;
    }

    public String delete(Integer id, Model model) {
        Organisation organisation= organisationRepository.findById(id).get();
        organisationRepository.delete(organisation);
        model.addAttribute("organisation", organisation);
        return "organisation-delete";
    }
}
