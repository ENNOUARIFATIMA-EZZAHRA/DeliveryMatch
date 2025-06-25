package com.DeliveryMatch.controller;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.*;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import com.DeliveryMatch.security.PasswordValidator;
import com.DeliveryMatch.security.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordValidator passwordValidator;

    @Autowired
    private LoginAttemptService loginAttemptService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserDTO userDTO) {
        try {
            if (userDTO.getEmail() == null || userDTO.getEmail().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Email is required");
            }
            
            if (userDTO.getNom() == null || userDTO.getNom().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Name is required");
            }
            
            if (userDTO.getPrenom() == null || userDTO.getPrenom().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("First name is required");
            }
            
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            if (userDTO.getMotDePass() == null || userDTO.getMotDePass().trim().isEmpty()) {
                return ResponseEntity.badRequest().body("Password cannot be empty");
            }

            if (!passwordValidator.isValid(userDTO.getMotDePass())) {
                return ResponseEntity.badRequest().body(passwordValidator.getPasswordRequirements());
            }

            if (userDTO.getRole() == null) {
                return ResponseEntity.badRequest().body("Role is required");
            }

            User user;
            switch (userDTO.getRole()) {
                case EXPEDITEUR:
                    user = new Expediteur();
                    break;
                case CONDUCTEUR:
                    user = new Conducteur();
                    break;
                case ADMINISTRATEUR:
                    user = new Administrateur();
                    break;
                default:
                    return ResponseEntity.badRequest().body("Invalid role");
            }

            user.setNom(userDTO.getNom());
            user.setPrenom(userDTO.getPrenom());
            user.setEmail(userDTO.getEmail());
            user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
            user.setDateInscription(LocalDateTime.now());
            user.setRole(userDTO.getRole());
            
            userRepository.save(user);

            String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
            
            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            
            Map<String, Object> userResponse = new HashMap<>();
            userResponse.put("id", user.getId());
            userResponse.put("nom", user.getNom());
            userResponse.put("prenom", user.getPrenom());
            userResponse.put("email", user.getEmail());
            userResponse.put("dateInscription", user.getDateInscription());
            userResponse.put("role", user.getRole());
            
            response.put("user", userResponse);
            
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        if (loginRequest.getEmail() == null || loginRequest.getEmail().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("L'email ne peut pas être vide");
        }

        if (loginRequest.getMotDePass() == null || loginRequest.getMotDePass().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le mot de passe ne peut pas être vide");
        }

        if (loginAttemptService.isBlocked(loginRequest.getEmail())) {
            long remainingTime = loginAttemptService.getRemainingBlockTime(loginRequest.getEmail());
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS)
                .body("Compte bloqué. Réessayez dans " + (remainingTime / 1000 / 60) + " minutes");
        }

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElse(null);

        if (user == null || !passwordEncoder.matches(loginRequest.getMotDePass(), user.getMotDePass())) {
            loginAttemptService.recordFailedAttempt(loginRequest.getEmail());
            return ResponseEntity.badRequest().body("Email ou mot de passe incorrect");
        }

        loginAttemptService.resetAttempts(loginRequest.getEmail());
        String token = jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("nom", user.getNom());
        userResponse.put("prenom", user.getPrenom());
        userResponse.put("email", user.getEmail());
        userResponse.put("dateInscription", user.getDateInscription());
        userResponse.put("role", user.getRole());
        
        response.put("user", userResponse);
        
        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {

            return ResponseEntity.ok("Déconnexion réussie");
        }
        return ResponseEntity.badRequest().body("Token non trouvé");
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UserDTO userDTO, HttpServletRequest request) {
        String currentEmail = jwtTokenProvider.getEmailFromToken(jwtTokenProvider.resolveToken(request));
        User user = userRepository.findByEmail(currentEmail)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Handle email update
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(currentEmail)) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already in use");
            }
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getMotDePass() != null && !userDTO.getMotDePass().trim().isEmpty()) {
            if (!passwordValidator.isValid(userDTO.getMotDePass())) {
                return ResponseEntity.badRequest().body(passwordValidator.getPasswordRequirements());
            }
            user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        }

        if (userDTO.getNom() != null) user.setNom(userDTO.getNom());
        if (userDTO.getPrenom() != null) user.setPrenom(userDTO.getPrenom());

        userRepository.save(user);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("nom", user.getNom());
        userResponse.put("prenom", user.getPrenom());
        userResponse.put("email", user.getEmail());
        userResponse.put("dateInscription", user.getDateInscription());
        userResponse.put("role", user.getRole());
        
        return ResponseEntity.ok(userResponse);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> deleteUser(@PathVariable Integer id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok("Utilisateur supprimé avec succès");
    }

    @PutMapping("/{id}/toggle-status")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> toggleUserStatus(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        user.setStatus(user.getStatus().equals("ENABLED") ? "SUSPENDED" : "ENABLED");
        userRepository.save(user);
        return ResponseEntity.ok("Statut de l'utilisateur modifié avec succès");
    }

    @PutMapping("/{id}/verify")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> verifyUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        user.setVerified(true);
        userRepository.save(user);
        return ResponseEntity.ok("Utilisateur vérifié avec succès");
    }

    @PutMapping("/{id}/unverify")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> unverifyUser(@PathVariable Integer id) {
        User user = userRepository.findById(id)
                .orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        user.setVerified(false);
        userRepository.save(user);
        return ResponseEntity.ok("Vérification de l'utilisateur supprimée avec succès");
    }

    @GetMapping("/profile")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> getProfile(HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromToken(jwtTokenProvider.resolveToken(request));
        User user = userRepository.findByEmail(email).orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        
        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("nom", user.getNom());
        userResponse.put("prenom", user.getPrenom());
        userResponse.put("email", user.getEmail());
        userResponse.put("dateInscription", user.getDateInscription());
        userResponse.put("role", user.getRole());
        
        return ResponseEntity.ok(userResponse);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> updateUserById(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }

        // Vérifier si l'email est déjà utilisé par un autre utilisateur
        if (userDTO.getEmail() != null && !userDTO.getEmail().equals(user.getEmail())) {
            if (userRepository.existsByEmail(userDTO.getEmail())) {
                return ResponseEntity.badRequest().body("Email already in use");
            }
            user.setEmail(userDTO.getEmail());
        }

        if (userDTO.getNom() != null) user.setNom(userDTO.getNom());
        if (userDTO.getPrenom() != null) user.setPrenom(userDTO.getPrenom());
        if (userDTO.getMotDePass() != null && !userDTO.getMotDePass().trim().isEmpty()) {
            user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        }
        if (userDTO.getRole() != null) user.setRole(userDTO.getRole());

        userRepository.save(user);

        Map<String, Object> userResponse = new HashMap<>();
        userResponse.put("id", user.getId());
        userResponse.put("nom", user.getNom());
        userResponse.put("prenom", user.getPrenom());
        userResponse.put("email", user.getEmail());
        userResponse.put("dateInscription", user.getDateInscription());
        userResponse.put("role", user.getRole());

        return ResponseEntity.ok(userResponse);
    }
}

