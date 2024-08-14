package com.example.EventHub.Organisation;


import com.example.EventHub.Event.Event;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerDTO;
import com.example.EventHub.Manager.ManagerMapper;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.NoSuchElementException;
import java.util.Optional;


@RequestMapping("/organisation")
@RestController
public class OrganisationController {
    private OrganisationRepository organisationRepository;
    private OrganisationMapper organisationMapper;
    private ManagerRepository managerRepository;

    @Autowired
    public OrganisationController(OrganisationRepository organisationRepository, OrganisationMapper organisationMapper, ManagerRepository managerRepository, UserRepository userRepository, ManagerMapper managerMapper) {
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.managerRepository = managerRepository;
    }

    @PostMapping("/submit")
    public boolean addOrganisation(@RequestBody OrganisationDTO organisationDTO, @RequestParam Integer id) {
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if(optionalManager.isEmpty()){
           return false;
        }else{
            Manager manager = optionalManager.get();
            Organisation organisation = organisationMapper.toEntity(organisationDTO);
            Organisation savedOrganisation = organisationRepository.save(organisation);
            manager.setOrganisation(savedOrganisation);
            managerRepository.save(manager);
            return true;
        }
    }

    @GetMapping("/all")
    public Iterable<Organisation> allOrganisations() {
        Iterable<Organisation> allOrganisations = organisationRepository.findAll();
        return allOrganisations;
    }



    @PutMapping("/update")
    public void postUpdatedOrganisation(@RequestParam("id") Integer id, @RequestBody OrganisationDTO updatedOrganisation) {
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation = optionalOrganisation.get();
            organisation.setName(updatedOrganisation.getName());
            organisationRepository.save(organisation);
        }else {
            throw new NoSuchElementException();
        }
    }



    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation=optionalOrganisation.get();
            organisationRepository.delete(organisation);
        }else {
            throw new NoSuchElementException("id is not found");
        }
    }
}
