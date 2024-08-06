package com.example.EventHub;

import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.Organisation.*;
import com.example.EventHub.Role.Role;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles({"testdb"})
@ExtendWith(DbSetupExtension.class)
public class OrganisationControllerTest {
    private OrganisationController organisationController;

    private UserRepository userRepository;

    private ManagerRepository managerRepository;

    private OrganisationRepository organisationRepository;

    @Autowired
    public OrganisationControllerTest(OrganisationController organisationController, UserRepository userRepository, ManagerRepository managerRepository, OrganisationRepository organisationRepository, OrganisationMapper organisationMapper) {
        this.organisationController = organisationController;
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.organisationRepository = organisationRepository;
    }

    private OrganisationDTO organisationDTO;

    @BeforeEach
    void setUp() {
        organisationDTO = new OrganisationDTO(1, "Test Org");
    }

    @Test
    @WithMockUser(username = "test@example.com")
    @Rollback
    void testAddOrganisation_Success() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("Test");
        user.setPassword("123456");
        userRepository.save(user);

        Manager manager = new Manager();
        manager.setUser(user);
        managerRepository.save(manager);

        organisationController.addOrganisation(organisationDTO, manager.getId());

        Organisation savedOrganisation = organisationRepository.findByName("Test Org");
        assertNotNull(savedOrganisation, "Organisation should be saved in the database");

        Manager updatedManager = managerRepository.findByUser(user);
        assertEquals(savedOrganisation, updatedManager.getOrganisation(), "Manager should be associated with the new organisation");
    }

    @Test
    @WithMockUser(username = "nonexistent@example.com")
    void testAddOrganisation_ManagerNotFound() {
        boolean actual = organisationController.addOrganisation(organisationDTO, 0);
        assertEquals(false, actual);
    }


}

