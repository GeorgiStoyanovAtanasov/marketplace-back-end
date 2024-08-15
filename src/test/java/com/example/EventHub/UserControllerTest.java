package com.example.EventHub;

import com.example.EventHub.User.User;
import com.example.EventHub.User.UserDTO;
import com.example.EventHub.User.UserRepository;
import com.example.EventHub.User.UserController;
import com.example.EventHub.User.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("testdb")
@ExtendWith(DbSetupExtension.class)
public class UserControllerTest {
    private UserRepository userRepository;

    private UserMapper userMapper;

    private UserController userController;

    @Autowired
    public UserControllerTest(UserRepository userRepository, UserMapper userMapper, UserController userController) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userController = userController;
    }

    @BeforeEach
    public void setUp() {
        // Clean up the test database before each test
        userRepository.deleteAll();

        // Create and save a test user
        User user = new User();
        user.setEmail("test@example.com");
        user.setFullName("Test User");
        user.setPassword("password"); // Make sure the password is encoded appropriately
        userRepository.save(user);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testAuthenticatedUser_UserFound() {
        // Act
        UserDTO result = userController.authenticatedUser();

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        assertEquals("Test User", result.getFullName());
    }

    @Test
    @WithMockUser(username = "nonexistent@example.com")
    public void testAuthenticatedUser_UserNotFound() {
        // Act & Assert
        NoSuchElementException exception = assertThrows(NoSuchElementException.class, () -> {
            userController.authenticatedUser();
        });

        assertEquals("No such user is found!", exception.getMessage());
    }
}
