package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.User;
import com.DeliveryMatch.model.UserRole;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.service.UserService;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.context.annotation.Import;

import java.util.Arrays;
import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@Import(com.DeliveryMatch.config.SecurityConfig.class)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserRepository userRepository;

    @MockBean
    private UserService userService;

    @MockBean
    private AnnonceRepository annonceRepository;

    @MockBean
    private DemandeTransportRepository demandeRepository;

    @MockBean
    private com.DeliveryMatch.security.JwtTokenProvider jwtTokenProvider;

    @MockBean
    private com.DeliveryMatch.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private ObjectMapper objectMapper;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User() {
            @Override
            public UserRole getRole() {
                return UserRole.ADMINISTRATEUR;
            }
        };
        testUser.setId(1);
        testUser.setNom("Test");
        testUser.setPrenom("User");
        testUser.setEmail("test@example.com");
        testUser.setDateInscription(java.time.LocalDateTime.now());
        testUser.setRole(UserRole.ADMINISTRATEUR);
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testGetDashboard() throws Exception {
        when(userRepository.findAll()).thenReturn(Arrays.asList(testUser));

        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.utilisateurs").exists());
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testValiderUser() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userService).validerUser(1);

        mockMvc.perform(post("/api/admin/valider/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Utilisateur validé"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testValiderUserNotFound() throws Exception {
        when(userRepository.existsById(999)).thenReturn(false);

        mockMvc.perform(post("/api/admin/valider/999"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").value("Utilisateur introuvable"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testSuspendreUser() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userService).suspendreUser(1);

        mockMvc.perform(post("/api/admin/suspendre/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Utilisateur suspendu"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testVerifierUser() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userService).verifierUser(1);

        mockMvc.perform(post("/api/admin/verifier/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Badge Vérifié ajouté"));
    }

    @Test
    @WithMockUser(roles = "ADMINISTRATEUR")
    void testEnleverVerification() throws Exception {
        when(userRepository.existsById(1)).thenReturn(true);
        doNothing().when(userService).enleverVerification(1);

        mockMvc.perform(post("/api/admin/enlever-verification/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Badge Vérifié supprimé"));
    }

    @Test
    @WithMockUser(roles = "USER")
    void testAccessDeniedForNonAdmin() throws Exception {
        mockMvc.perform(get("/api/admin/dashboard"))
                .andExpect(status().isForbidden());
    }
} 