package com.DeliveryMatch.controller;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.*;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import com.DeliveryMatch.security.PasswordValidator;
import com.DeliveryMatch.security.LoginAttemptService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> register(@RequestBody UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            return ResponseEntity.badRequest().body("Email déjà utilisé");
        }

        if (userDTO.getMotDePass() == null || userDTO.getMotDePass().trim().isEmpty()) {
            return ResponseEntity.badRequest().body("Le mot de passe ne peut pas être vide");
        }

        if (!passwordValidator.isValid(userDTO.getMotDePass())) {
            return ResponseEntity.badRequest().body(passwordValidator.getPasswordRequirements());
        }

        if (userDTO.getRole() == null) {
            return ResponseEntity.badRequest().body("Le rôle est obligatoire");
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
                return ResponseEntity.badRequest().body("Rôle invalide");
        }

        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        user.setDateInscription(new Date());
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
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
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
            // يمكنك إضافة التوكن إلى قائمة سوداء هنا إذا كنت تريد
            return ResponseEntity.ok("Déconnexion réussie");
        }
        return ResponseEntity.badRequest().body("Token non trouvé");
    }

    @PutMapping("/update")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> updateUser(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        String email = jwtTokenProvider.getEmailFromToken(jwtTokenProvider.resolveToken(request));
        User user = userRepository.findByEmail(email)
                .orElse(null);

        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
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
        User user = userRepository.findById(id).orElse(null);
        if (user == null) {
            return ResponseEntity.badRequest().body("Utilisateur non trouvé");
        }
        // يمكنك إضافة منطق لتعطيل/تفعيل المستخدم هنا
        return ResponseEntity.ok("Statut de l'utilisateur modifié avec succès");
    }
}
