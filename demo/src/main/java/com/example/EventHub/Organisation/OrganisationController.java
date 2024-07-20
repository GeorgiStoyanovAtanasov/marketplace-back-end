package com.example.EventHub.Organisation;


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

    @GetMapping("/add")
    public String addOrganisation(Model model){
        model.addAttribute("organisation", new Organisation());
        model.addAttribute("organisations", organisationRepository.findAll());
        return "organisation-form";
    }
    @PostMapping("/add")
    public void addOrganisation(@RequestBody OrganisationDTO organisationDTO, Manager manager){
        Organisation organisation = organisationMapper.toEntity(organisationDTO);
        organisationRepository.save(organisation);
        manager.setOrganisation(organisation);
        managerRepository.save(manager);

    }
    @PostMapping("/submit")
    public String postProduct(@Valid @ModelAttribute Organisation organisation, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "organisation-form";
        } else {
            organisationRepository.save(organisation);
            model.addAttribute("organisation", organisation);
            return "organisation-result";
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
