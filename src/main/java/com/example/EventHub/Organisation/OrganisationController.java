package com.example.EventHub.Organisation;


import com.example.EventHub.Event.Event;
import com.example.EventHub.Manager.*;
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

import java.util.List;
import java.util.Optional;


@RequestMapping("/organisation")
@RestController
public class OrganisationController {
    private OrganisationRepository organisationRepository;
    private OrganisationService organisationService;
    private OrganisationMapper organisationMapper;
    private ManagerRepository managerRepository;
    private ManagerService managerService;

    @Autowired
    public OrganisationController(OrganisationRepository organisationRepository, OrganisationService organisationService, OrganisationMapper organisationMapper, ManagerRepository managerRepository, UserRepository userRepository, ManagerMapper managerMapper, ManagerService managerService) {
        this.organisationRepository = organisationRepository;
        this.organisationService = organisationService;
        this.organisationMapper = organisationMapper;
        this.managerRepository = managerRepository;
        this.managerService = managerService;
    }

    @PostMapping("/submit")
    public boolean addOrganisation(@RequestBody OrganisationDTO organisationDTO) {
        Integer id = managerService.getManagerId();
        Optional<Manager> optionalManager = managerRepository.findById(id);
        if(optionalManager.isEmpty()){
           return false;
        }else{
            Manager manager = optionalManager.get();
            Organisation organisation = organisationMapper.toEntity(organisationDTO);
            organisation.setOrganisationPermission(OrganisationPermission.WAITING);
            Organisation savedOrganisation = organisationRepository.save(organisation);
            manager.setOrganisation(savedOrganisation);
            managerRepository.save(manager);
            return true;
        }
    }

    @GetMapping("/all")
    public Iterable<Organisation> allOrganisations() {
        return organisationRepository.findAllByOrganisationPermission(OrganisationPermission.ACCEPT);
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
    @PostMapping("/accept")
    public void acceptOrganisation(@RequestParam(name = "id") Integer id){
        Optional<Organisation> organisation = organisationRepository.findById(id);
        if(organisation.isPresent()){
            organisation.get().setOrganisationPermission(OrganisationPermission.ACCEPT);
            organisationRepository.save(organisation.get());
        }
    }
    @PostMapping("/reject")
    public void rejectOrganisation(@RequestParam(name = "id") Integer id){
        Optional<Organisation> organisation = organisationRepository.findById(id);
        if(organisation.isPresent()){
            organisation.get().setOrganisationPermission(OrganisationPermission.REJECT);
            organisationRepository.save(organisation.get());
        }
    }
    @GetMapping("/waiting")
    public List<OrganisationDTO> getAllWaitingOrganisations(@RequestParam(value = "name", required = false) String name, @RequestParam(value = "permission", required = false) OrganisationPermission organisationPermission){
        return organisationService.findOrganisationsByNameAndPermission(name, organisationPermission);
    }
}
