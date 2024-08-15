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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
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

    private User testUser;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();

        testUser = new User();
        testUser.setEmail("test@example.com");
        testUser.setFullName("Test User");
        testUser.setPassword("password");

        userRepository.save(testUser);
    }

    @Test
    @WithMockUser(username = "test@example.com")
    public void testAuthenticatedUser_UserFound() {
        // Act
        UserDTO result = userController.authenticatedUser();

        // Assert
        assertNotNull(result, "The result should not be null when the user is found");
        assertEquals("test@example.com", result.getEmail(), "The email should match the mock user's email");
        assertEquals("Test User", result.getFullName(), "The full name should match the mock user's full name");
    }

    @Test
    @WithMockUser(username = "nonexistent@example.com")
    public void testAuthenticatedUser_UserNotFound() {
        // Act & Assert
        Exception exception = assertThrows(NoSuchElementException.class, () -> {
            userController.authenticatedUser();
        });

        assertEquals("No such user is found!", exception.getMessage(), "The exception message should match the expected text");
    }
}
