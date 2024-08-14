package com.example.EventHub.Organisation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.NoSuchElementException;
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

}
