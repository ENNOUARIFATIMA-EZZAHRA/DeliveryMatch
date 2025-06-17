package com.DeliveryMatch.service;

import com.DeliveryMatch.model.User;
import com.DeliveryMatch.model.UserRole;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        testUser = new User();
        testUser.setId(1L);
        testUser.setFirstName("John");
        testUser.setLastName("Doe");
        testUser.setEmail("john.doe@example.com");
        testUser.setPassword("password123");
        testUser.setRole(UserRole.USER);
    }

    @Test
    void createUser_Success() {
        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User createdUser = userService.createUser(testUser);

        assertNotNull(createdUser);
        assertEquals(testUser.getEmail(), createdUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserByEmail_Success() {
        when(userRepository.findByEmail(testUser.getEmail())).thenReturn(Optional.of(testUser));

        Optional<User> foundUser = userService.getUserByEmail(testUser.getEmail());

        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
    }

    @Test
    void getUserByEmail_NotFound() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        Optional<User> foundUser = userService.getUserByEmail("nonexistent@example.com");

        assertFalse(foundUser.isPresent());
    }

    @Test
    void updateUser_Success() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        User updatedUser = userService.updateUser(testUser);

        assertNotNull(updatedUser);
        assertEquals(testUser.getEmail(), updatedUser.getEmail());
        verify(userRepository, times(1)).save(any(User.class));
    }
} 