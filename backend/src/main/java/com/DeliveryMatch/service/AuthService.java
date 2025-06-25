package com.DeliveryMatch.service;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.*;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;



@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User register(UserDTO userDTO) {
        if (userDTO.getRole() == UserRole.ADMINISTRATEUR) {
            throw new RuntimeException("Registration as ADMINISTRATOR is not allowed.");
        }
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email already exists");
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
                throw new RuntimeException("Invalid role");
        }
        
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        user.setDateInscription(LocalDateTime.now());
        user.setRole(userDTO.getRole());
        user.setStatus("ENABLED");
        user.setVerified(false);
        
        return userRepository.save(user);
    }

    public String authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(loginRequest.getMotDePass(), user.getMotDePass())) {
            throw new RuntimeException("Incorrect password");
        }
        return jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
    }
    
    public User getCurrentUser(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
