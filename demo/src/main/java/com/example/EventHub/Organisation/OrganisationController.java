package com.example.EventHub.Organisation;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/organisation")
public class OrganisationController {
    @Autowired
    OrganisationRepository organisationRepository;
    @Autowired
    OrganisationService organisationService;
    @GetMapping("/add")
    public String addOrganisation(Model model){
        model.addAttribute("organisation", new Organisation());
        model.addAttribute("organisations", organisationRepository.findAll());
        return "organisation-form";
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
    public String allOrganisations(Model model){
        Iterable<Organisation> allOrganisations = organisationRepository.findAll();
        model.addAttribute("allOrganisation", allOrganisations);
        return "all-organisations";
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
