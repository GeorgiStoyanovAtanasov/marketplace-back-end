package com.example.EventHub.Manager;

import com.example.EventHub.JWT.dtos.RegisterUserDto;
import com.example.EventHub.JWT.services.AuthenticationService;
import com.example.EventHub.User.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/manager")
public class ManagerController {
    AuthenticationService authenticationService;
    ManagerRepository managerRepository;
    ManagerMapper managerMapper;

    @Autowired
    public ManagerController(AuthenticationService authenticationService, ManagerRepository managerRepository, ManagerMapper managerMapper) {
        this.authenticationService = authenticationService;
        this.managerRepository = managerRepository;
        this.managerMapper = managerMapper;
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
}
