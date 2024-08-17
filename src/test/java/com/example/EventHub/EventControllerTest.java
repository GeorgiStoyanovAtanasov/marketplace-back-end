package com.example.EventHub;

import com.example.EventHub.Event.*;
import com.example.EventHub.EventPermission.EventPermission;
import com.example.EventHub.EventStatus.EventStatus;
import com.example.EventHub.EventType.EventType;
import com.example.EventHub.EventType.EventTypeDTO;
import com.example.EventHub.EventType.EventTypeMapper;
import com.example.EventHub.EventType.EventTypeRepository;
import com.example.EventHub.Manager.Manager;
import com.example.EventHub.Manager.ManagerRepository;
import com.example.EventHub.Organisation.*;
import com.example.EventHub.Role.Role;
import com.example.EventHub.User.User;
import com.example.EventHub.User.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@ExtendWith(MockitoExtension.class)
@ExtendWith(DbSetupExtension.class)
public class EventControllerTest {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private EventTypeRepository eventTypeRepository;
    @Autowired
    private EventService eventService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ManagerRepository managerRepository;

    @InjectMocks
    @Autowired
    private EventController eventController;

    @BeforeEach
    public void setUp() {
        organisationRepository.deleteAll();
        eventTypeRepository.deleteAll();
        eventRepository.deleteAll();

        Organisation organisation = new Organisation("My Organisation", OrganisationPermission.ACCEPT);
        organisationRepository.save(organisation);

        EventType eventType = new EventType("Conference");
        eventTypeRepository.save(eventType);
        Event event = new Event("TestEvent", "2024-08-09", 6, "some description", "some place", "12:30", 0.0, 100, "some image".getBytes(), organisation, eventType, EventStatus.AVAILABLE, Collections.emptyList(), EventPermission.ACCEPT);
        eventRepository.save(event);
    }

    @Test
    public void testPostEvent_errorEventStatusFalse() {
        // Arrange
        EventDTO eventDTO = createEventDTO();
        eventDTO.setDate(generateFutureDate());

        // Act
        boolean result = eventController.postEvent(eventDTO);

        // Assert
        assertTrue(result);
        List<Event> events = (List<Event>) eventRepository.findAll();
        assertFalse(events.isEmpty(), "Database should contain the saved event");
    }

    @Test
    public void testPostEvent_errorEventStatusTrue() {
        // Arrange
        eventRepository.deleteAll();
        EventDTO eventDTO = createEventDTO();

        // Act
        boolean result = eventController.postEvent(eventDTO);

        // Assert
        assertFalse(result);
        List<Event> events = (List<Event>) eventRepository.findAll();
        assertTrue(events.isEmpty(), "Database shouldn't contain the saved event");
    }

    @Test
    public void testGetAllEventsAndTypes() {
        // Act
        ResponseEntity<Map<String, List<?>>> response = eventController.getAllEventsAndTypes(EventPermission.ACCEPT);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody().get("events"));
        assertNotNull(response.getBody().get("eventTypes"));
    }

    @Test
    public void testGetEventDetails_EventFound() {
        // Arrange
        List<Event> events = (List<Event>) eventRepository.findAll();
        // Act
        ResponseEntity<Map<String, EventDTO>> response = eventController.getEventDetails(events.get(0).getName());

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(events.get(0).getName(), response.getBody().get("event").getName());
    }

    @Test
    public void testGetEventDetails_EventNotFound() {
        // Arrange
        String eventName = "NonExistentEventName";
        assertNull(eventRepository.findByName(eventName), "Event should not exist in the database");

        // Act
        ResponseEntity<Map<String, EventDTO>> response = eventController.getEventDetails(eventName);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertTrue(response.getBody().isEmpty(), "Response body should be empty when event is not found");
    }

    @Test
    public void testSearchEvents() {
        //Arrange
        List<EventType> eventTypes = (List<EventType>) eventTypeRepository.findAll();
        //Act
        ResponseEntity<Map<String, List<?>>> response = eventController.searchEvents(
                "TestEvent",
                "some place",
                eventTypes.get(0).getId(),
                "2024-08-09",
                0.0,
                10.0,
                EventPermission.ACCEPT
        );
        //Assert
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Map<String, List<?>> body = response.getBody();

        assertNotNull(body);
        assertFalse(body.get("events").isEmpty());
        assertFalse(body.get("eventTypes").isEmpty());
    }

    @Test
    public void testDeleteEvent_Success() {
        // Arrange

        // Act
        eventController.delete("TestEvent");

        // Assert
        Event deletedEvent = eventRepository.findByName("TestEvent");
        assertNull(deletedEvent);
    }

    @Test
    public void testDeleteEvent_NotFound() {
        // Arrange
        String nonExistentEventName = "NonExistentEvent";

        // Act & Assert
        assertThrows(IllegalArgumentException.class, () -> {
            eventService.delete(nonExistentEventName);
        });
    }
    @Test
    @WithMockUser(username = "manager@example.com", roles = {"MANAGER"})
    public void testGetEventsForOrganisation() {
        // Arrange
        User user = new User("John Wick", "manager@example.com", "123456", Role.MANAGER, Collections.emptyList());
        user = userRepository.save(user);
        Organisation organisation = new Organisation("testOrganisation", OrganisationPermission.ACCEPT);
        organisation = organisationRepository.save(organisation);
        EventType eventType = new EventType("conference");
        eventType = eventTypeRepository.save(eventType);
        String validBase64String = "SGVsbG8gV29ybGQ="; // This is "Hello World" in Base64
        byte[] decodedImage = Base64.getDecoder().decode(validBase64String);
        Event event = new Event("testEventfgh", generateFutureDate(), 6, "dhujkrvfgb", "dhrfbg", "12:30", 0, 80, decodedImage, organisation, eventType, EventStatus.AVAILABLE, Collections.emptyList(), EventPermission.ACCEPT);
        event = eventRepository.save(event);
        Manager manager = new Manager();
        manager.setUser(user);
        manager.setOrganisation(organisation);
        managerRepository.save(manager);

        // Act
        List<EventDTO> eventDTOS = eventController.getEventsForOrganisation();

        // Assert
        assertEquals(event.getName(), eventDTOS.get(0).getName());
    }


    private EventDTO createEventDTO() {
        EventDTO eventDTO = new EventDTO();
        eventDTO.setName("Sample Event");
        eventDTO.setDate(generatePastDate());
        eventDTO.setDuration(120);
        eventDTO.setDescription("Sample Description");
        eventDTO.setPlace("Sample Place");
        eventDTO.setTime("10:00 AM");
        eventDTO.setTicketPrice(50.0);
        eventDTO.setCapacity(100);
        eventDTO.setImage(Base64.getEncoder().encodeToString("Sample Image".getBytes()));

        OrganisationDTO organisationDTO = new OrganisationDTO();
        organisationDTO.setName("My Organisation");
        eventDTO.setOrganisation(organisationDTO);

        EventTypeDTO eventTypeDTO = new EventTypeDTO();
        eventTypeDTO.setTypeName("Conference");
        eventDTO.setEventTypeDTO(eventTypeDTO);

        return eventDTO;
    }
    public static String generateFutureDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate futureDate = currentDate.plusDays(1);
        return futureDate.format(formatter);
    }
    public static String generatePastDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate currentDate = LocalDate.now();
        LocalDate pastDate = currentDate.minusDays(1);
        return pastDate.format(formatter);
    }
}