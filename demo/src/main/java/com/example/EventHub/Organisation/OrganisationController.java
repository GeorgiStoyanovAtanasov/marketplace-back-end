package com.example.EventHub.Organisation;


import com.example.EventHub.Event.Event;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.User.User;
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
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    OrganisationService organisationService;
    @Autowired
    private OrganisationMapper organisationMapper;
    @Autowired
    private ManagerRepository managerRepository;


    @PostMapping("/submit")
    public void addOrganisation(@Valid @RequestBody OrganisationDTO organisationDTO, Manager manager, BindingResult bindingResult){
        if (bindingResult.hasErrors()) {
            throw new IllegalArgumentException();
        }
         else {
            Organisation organisation = organisationMapper.toEntity(organisationDTO);
            organisationRepository.save(organisation);
            manager.setOrganisation(organisation);
            managerRepository.save(manager);
        }
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
