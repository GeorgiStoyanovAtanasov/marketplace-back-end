package com.example.EventHub;

import com.example.EventHub.EventPermission.EventPermission;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.Organisation.*;
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

import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles({"testdb"})
@ExtendWith(DbSetupExtension.class)
public class OrganisationControllerTest {
    private OrganisationController organisationController;
    private OrganisationService organisationService;

    private UserRepository userRepository;

    private ManagerRepository managerRepository;

    private OrganisationRepository organisationRepository;
    private OrganisationMapper organisationMapper;

    @Autowired
    public OrganisationControllerTest(OrganisationController organisationController, UserRepository userRepository, ManagerRepository managerRepository, OrganisationRepository organisationRepository, OrganisationMapper organisationMapper, OrganisationService organisationService) {
        this.organisationController = organisationController;
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.organisationRepository = organisationRepository;
        this.organisationMapper = organisationMapper;
        this.organisationService = organisationService;
    }

    private OrganisationDTO organisationDTO;
    private Organisation organisation;

    @BeforeEach
    void setUp() {
        organisationDTO = new OrganisationDTO(1, "Test Org", OrganisationPermission.WAITING);
        organisation = new Organisation(1, "Updated Org", OrganisationPermission.WAITING);
        managerRepository.deleteAll();
        organisationRepository.deleteAll();
        organisationDTO = new OrganisationDTO(1, "Test Org", OrganisationPermission.ACCEPT);
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

        boolean result = organisationController.addOrganisation(organisationDTO);

        Organisation savedOrganisation = organisationRepository.findByName("Test Org");
        assertNotNull(savedOrganisation, "Organisation should be saved in the database");

        Manager updatedManager = managerRepository.findByUser(user);
        assertEquals(savedOrganisation, updatedManager.getOrganisation(), "Manager should be associated with the new organisation");
        assertTrue(result);
    }

    @Test
    void testAddOrganisation_ManagerNotFound() {
        boolean actual = organisationController.addOrganisation(organisationDTO);
        assertFalse(actual);
    }

    @Test
    void testAllOrganisations() {
        Organisation organisation = new Organisation();
        organisation.setName("suhdkfgf");
        organisation.setOrganisationPermission(OrganisationPermission.ACCEPT);
        organisationRepository.save(organisation);
        List<Organisation> organisations = (List<Organisation>) organisationController.allOrganisations();
        assertEquals(1, organisations.size());
    }

    @Test
    public void testDeleteOrganisationSuccess() {
        Organisation organisation = new Organisation("Test Organisation", OrganisationPermission.WAITING);
        organisationRepository.save(organisation);
        // Ensure the organisation exists before deletion
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertTrue(optionalOrganisation.isPresent());

        // Call the delete method
        organisationController.delete(organisation.getId());

        // Ensure the organisation is deleted
        optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertFalse(optionalOrganisation.isPresent());
    }

    @Test
    public void testDeleteOrganisationNotFound() {
        Integer nonExistentId = -1;

        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            organisationController.delete(nonExistentId);
        });

        assertEquals("id is not found", exception.getMessage());
    }

    @Test
    public void testAcceptOrganisationSuccess() {
        Organisation organisation = new Organisation("Test Organisation", OrganisationPermission.WAITING);
        organisationRepository.save(organisation);
        // Ensure the organisation exists and has the initial WAITING permission
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertTrue(optionalOrganisation.isPresent());
        assertEquals(OrganisationPermission.WAITING, optionalOrganisation.get().getOrganisationPermission());

        // Call the accept method
        organisationController.acceptOrganisation(organisation.getId());

        // Retrieve the organisation again to check the updated permission
        optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertTrue(optionalOrganisation.isPresent());
        assertEquals(OrganisationPermission.ACCEPT, optionalOrganisation.get().getOrganisationPermission());
    }

    @Test
    public void testAcceptOrganisationNotFound() {
        // Test with a non-existent organisation ID
        Integer nonExistentId = -1;

        // Call the accept method, which should not throw any exception
        // as the method does not explicitly handle the case where the organisation is not found
        organisationController.acceptOrganisation(nonExistentId);

        // Verify that the repository still contains only the initial organisation
        long count = organisationRepository.count();
        assertEquals(0, count);
    }

    @Test
    public void testRejectOrganisationSuccess() {
        Organisation organisation = new Organisation("Test Organisation", OrganisationPermission.WAITING);
        organisationRepository.save(organisation);
        // Ensure the organisation exists and has the initial WAITING permission
        Optional<Organisation> optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertTrue(optionalOrganisation.isPresent());
        assertEquals(OrganisationPermission.WAITING, optionalOrganisation.get().getOrganisationPermission());

        // Call the reject method
        organisationController.rejectOrganisation(organisation.getId());

        // Retrieve the organisation again to check the updated permission
        optionalOrganisation = organisationRepository.findById(organisation.getId());
        assertTrue(optionalOrganisation.isPresent());
        assertEquals(OrganisationPermission.REJECT, optionalOrganisation.get().getOrganisationPermission());
    }

    @Test
    public void testRejectOrganisationNotFound() {
        // Test with a non-existent organisation ID
        Integer nonExistentId = -1;

        // Call the reject method, which should not throw any exception
        // as the method does not explicitly handle the case where the organisation is not found
        organisationController.rejectOrganisation(nonExistentId);

        // Verify that the repository still contains only the initial organisation
        long count = organisationRepository.count();
        assertEquals(0, count);
    }

    @Test
    public void testGetAllWaitingOrganisationsWithNameAndPermission() {
        addTestOrganisations();
        // Given
        String name = "Organisation 1";
        OrganisationPermission permission = OrganisationPermission.WAITING;

        // When
        List<OrganisationDTO> organisationDTOS = organisationController.getAllWaitingOrganisations(name, permission);

        // Then
        assertEquals(1, organisationDTOS.size());
        assertEquals("Organisation 1", organisationDTOS.get(0).getName());
        assertEquals(OrganisationPermission.WAITING, organisationDTOS.get(0).getOrganisationPermission());
    }

    @Test
    public void testGetAllWaitingOrganisationsWithNameOnly() {
        addTestOrganisations();
        // Given
        String name = "Organisation";
        OrganisationPermission permission = null;

        // When
        List<OrganisationDTO> organisationDTOS = organisationController.getAllWaitingOrganisations(name, permission);

        // Then
        assertEquals(3, organisationDTOS.size());
        assertTrue(organisationDTOS.stream().anyMatch(dto -> dto.getName().equals("Organisation 1")));
        assertTrue(organisationDTOS.stream().anyMatch(dto -> dto.getName().equals("Organisation 2")));
        assertTrue(organisationDTOS.stream().anyMatch(dto -> dto.getName().equals("Organisation 3")));
    }

    @Test
    public void testGetAllOrganisation() {

        Iterable<Organisation> allOrganisations = organisationRepository.findAll();

        assertNotNull(allOrganisations);

    }

    @Test
    @Rollback
    void testPostUpdate_Success() {
        organisationRepository.save(organisation);
        organisationController.postUpdatedOrganisation(organisationRepository.findByName(organisation.getName()).getId(), organisationDTO);
        Optional<Organisation> updatedOrganisation = Optional.ofNullable(organisationRepository.findByName(organisationDTO.getName()));
        assertTrue(updatedOrganisation.isPresent(), "Organisation should be present after update");
        assertEquals("Test Org", updatedOrganisation.get().getName(), "Organisation name should be updated");
    }

    @Test
    @Rollback
    void testPostUpdate_OrganisationNotFound() {
        assertThrows(NoSuchElementException.class, () -> organisationController.postUpdatedOrganisation(0, organisationDTO), "Expected IllegalArgumentException for non-existent ID");
    }

    @Test
    @Rollback
    void testDelete_Success() {
        Organisation savedOrganisation = organisationRepository.save(new Organisation());

        organisationController.delete(savedOrganisation.getId());

        Optional<Organisation> deletedOrganisation = organisationRepository.findById(savedOrganisation.getId());
        assertTrue(deletedOrganisation.isEmpty(), "Organisation should be deleted");
    }

    @Test
    @Rollback
    void testDelete_OrganisationNotFound() {
        assertThrows(NoSuchElementException.class, () -> organisationController.delete(0), "Expected NoSuchElementException for non-existent ID");
    }

    @Test
    public void testGetAllWaitingOrganisationsWithPermissionOnly() {
        addTestOrganisations();
        // Given
        String name = null;
        OrganisationPermission permission = OrganisationPermission.WAITING;

        // When
        List<OrganisationDTO> organisationDTOS = organisationController.getAllWaitingOrganisations(name, permission);

        // Then
        assertEquals(2, organisationDTOS.size());
        assertTrue(organisationDTOS.stream().anyMatch(dto -> dto.getName().equals("Organisation 1")));
        assertTrue(organisationDTOS.stream().anyMatch(dto -> dto.getName().equals("Organisation 3")));
    }

    @Test
    public void testGetAllWaitingOrganisationsWithNoMatch() {
        addTestOrganisations();
        // Given
        String name = "Nonexistent Organisation";
        OrganisationPermission permission = OrganisationPermission.WAITING;

        // When
        List<OrganisationDTO> organisationDTOS = organisationController.getAllWaitingOrganisations(name, permission);

        // Then
        assertEquals(0, organisationDTOS.size());
    }

    public void addTestOrganisations() {
        Organisation org1 = new Organisation("Organisation 1", OrganisationPermission.WAITING);
        Organisation org2 = new Organisation("Organisation 2", OrganisationPermission.ACCEPT);
        Organisation org3 = new Organisation("Organisation 3", OrganisationPermission.WAITING);
        organisationRepository.save(org1);
        organisationRepository.save(org2);
        organisationRepository.save(org3);
    }
}


