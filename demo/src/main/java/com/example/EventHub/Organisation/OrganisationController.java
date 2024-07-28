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
    @GetMapping("/update")
    public String updateOrganisationForm(@RequestParam("id") Integer id, Model model) {
        return organisationService.updateForm(id, model);
    }

    @PostMapping("/update")
    public String postUpdatedOrganisation(@RequestParam("id") Integer id, @ModelAttribute Organisation updatedOrganisation, BindingResult bindingResult, Model model) {
        return organisationService.postUpdate(id, updatedOrganisation, bindingResult, model);
    }
    @GetMapping("/delete")
    public String delete(@RequestParam("id") Integer id, Model model) {
        return organisationService.delete(id, model);
    }

}
