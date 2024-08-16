package com.example.EventHub.Manager;

import com.example.EventHub.JWT.dtos.RegisterUserDto;
import com.example.EventHub.JWT.services.AuthenticationService;
import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.Organisation.OrganisationDTO;
import com.example.EventHub.Organisation.OrganisationMapper;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    AuthenticationService authenticationService;
    ManagerRepository managerRepository;
    ManagerMapper managerMapper;
    UserRepository userRepository;
    OrganisationMapper organisationMapper;
    ManagerService managerService;

    @Autowired
    public ManagerController(AuthenticationService authenticationService, ManagerRepository managerRepository, ManagerMapper managerMapper, UserRepository userRepository, OrganisationMapper organisationMapper, ManagerService managerService) {
        this.authenticationService = authenticationService;
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
        this.userRepository = userRepository;
        this.organisationMapper = organisationMapper;
        this.managerService = managerService;
    }

    @PostMapping("/register")
    public ResponseEntity<ManagerDTO> registerManager(@RequestBody RegisterUserDto registerUserDto) {
        User registeredUser = authenticationService.signupManager(registerUserDto);
        Manager manager = new Manager();
        manager.setUser(registeredUser);
        managerRepository.save(manager);
        ManagerDTO managerDTO = managerMapper.toDTO(manager);
        return ResponseEntity.ok(managerDTO);
    }
    @GetMapping("/organisation")
    public ResponseEntity<OrganisationDTO> findOrganisationByManager(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        User user = userRepository.findByEmail(email).orElse(null);
        Manager manager = managerRepository.findByUser(user);
        if(manager.getOrganisation() == null){
            return null;
        }
        OrganisationDTO organisationDTO = organisationMapper.toDTO(manager.getOrganisation());
        return ResponseEntity.ok(organisationDTO);
    }
    @GetMapping("/id")
    public ResponseEntity<Integer> getIdOfManager(){
        return ResponseEntity.ok(managerService.getManagerId());
    }
}
