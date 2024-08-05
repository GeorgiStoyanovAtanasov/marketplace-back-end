package com.example.EventHub;

import com.example.EventHub.Event.Event;
import com.example.EventHub.Event.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ActiveProfiles({"testdb"})
@ExtendWith(DbSetupExtension.class)
public class EventControllerTest {

    @Autowired
    EventRepository eventRepository;

    @Test
    public void givenGenericEntityRepository_whenSaveAndRetreiveEntity_thenOK() {
        Event event = new Event();
        event.setName("testEvent");
        eventRepository.save(event);
        Event foundEntity = eventRepository.findById(event.getId()).orElse(null);
        assertNotNull(foundEntity);
        assertEquals(event.getName(), foundEntity.getName());
    }
}
