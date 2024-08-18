package com.example.EventHub;

import com.example.EventHub.JWT.dtos.RegisterUserDto;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerController;
import com.example.EventHub.Manager.ManagerDTO;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.Organisation.Organisation;
import com.example.EventHub.Organisation.OrganisationDTO;
import com.example.EventHub.Organisation.OrganisationRepository;
import com.example.EventHub.Role.Role;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@Rollback
public class ManagerControllerTests {

    @Autowired
    private ManagerController managerController;

    @Autowired
    private ManagerRepository managerRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrganisationRepository organisationRepository;

    @BeforeEach
    public void setup() {
        managerRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    public void testRegisterManager() {
        RegisterUserDto registerUserDto = new RegisterUserDto();
        registerUserDto.setEmail("testmanager@example.com");
        registerUserDto.setPassword("password");

        ResponseEntity<ManagerDTO> response = managerController.registerManager(registerUserDto);
        ManagerDTO managerDTO = response.getBody();

        assertNotNull(managerDTO);
        assertNotNull(managerDTO.getId());

        Manager manager = managerRepository.findById(managerDTO.getId()).orElse(null);
        assertNotNull(manager);
        assertEquals("testmanager@example.com", manager.getUser().getEmail());
    }

    @Test
    @WithMockUser(username = "manager@example.com", roles = {"MANAGER"})
    public void testFindOrganisationByManager() {
        // Setup: Create a user and manager with an organisation
        User user = new User("John Wick", "manager@example.com", "123456", Role.MANAGER, Collections.emptyList());
        user = userRepository.save(user);

        Organisation organisation = new Organisation();
        organisation.setName("Test Organisation");
        organisation = organisationRepository.save(organisation); // Ensure organisation is saved before manager

        Manager manager = new Manager();
        manager.setUser(user);
        manager.setOrganisation(organisation);
        managerRepository.save(manager);

        // Test the findOrganisationByManager method
        ResponseEntity<OrganisationDTO> response = managerController.findOrganisationByManager();
        OrganisationDTO organisationDTO = response.getBody();

        assertNotNull(organisationDTO);
        assertEquals("Test Organisation", organisationDTO.getName());
    }

    @Test
    @WithMockUser(username = "manager@example.com", roles = {"MANAGER"})
    public void testGetIdOfManager() {
        // Setup: Create a user and manager
        User user = new User("John Wick", "manager@example.com", "123456", Role.MANAGER, Collections.emptyList());
        user = userRepository.save(user);

        Manager manager = new Manager();
        manager.setUser(user);
        managerRepository.save(manager);

        // Test the getIdOfManager method
        ResponseEntity<Integer> response = managerController.getIdOfManager();
        Integer managerId = response.getBody();

        assertNotNull(managerId);
        assertEquals(manager.getId(), managerId);
    }
}
