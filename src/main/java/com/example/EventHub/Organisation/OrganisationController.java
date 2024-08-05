package com.example.EventHub.Organisation;


import com.example.EventHub.Event.Event;
import com.example.EventHub.Manager.Manager;
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

import java.util.Optional;


@RequestMapping("/organisation")
@RestController
public class OrganisationController {
    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private OrganisationMapper organisationMapper;
    private ManagerRepository managerRepository;
    private UserRepository userRepository;

    @Autowired
    public OrganisationController(OrganisationRepository organisationRepository, OrganisationService organisationService, OrganisationMapper organisationMapper, ManagerRepository managerRepository, UserRepository userRepository) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.organisationMapper = organisationMapper;
        this.managerRepository = managerRepository;
        this.userRepository=userRepository;
    }

    @PostMapping("/submit")
    public void addOrganisation(@RequestBody OrganisationDTO organisationDTO){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).get();
        Manager manager = managerRepository.findByUser(user);
            Organisation organisation = organisationMapper.toEntity(organisationDTO);
            organisationRepository.save(organisation);
            manager.setOrganisation(organisation);
            managerRepository.save(manager);
    }

    @GetMapping("/all")
    public Iterable<Organisation> allOrganisations(){
        Iterable<Organisation> allOrganisations = organisationRepository.findAll();
        return allOrganisations;
    }


    @PutMapping("/update")
    public void postUpdatedOrganisation(@RequestParam("id") Integer id, @RequestBody OrganisationDTO updatedOrganisation) {
        organisationService.postUpdate(id, updatedOrganisation);
    }
    @DeleteMapping("/delete")
    public void delete(@RequestParam("id") Integer id) {
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(id);
        if (optionalOrganisation.isPresent()) {
            Organisation organisation=optionalOrganisation.get();
            organisationRepository.delete(organisation);
        }else {
            throw new IllegalArgumentException("id is not found");
        }
    }
}
