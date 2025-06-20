package com.DeliveryMatch.service;

import com.DeliveryMatch.dto.LoginRequest;
import com.DeliveryMatch.model.*;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }
        user.setMotDePass(passwordEncoder.encode(user.getMotDePass()));
        user.setDateInscription(new Date());
        user.setStatus("ENABLED");
        user.setVerified(false);
        return userRepository.save(user);
    }

    public String authenticate(LoginRequest loginRequest) {
        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        if (!passwordEncoder.matches(loginRequest.getMotDePass(), user.getMotDePass())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        return jwtTokenProvider.generateToken(user.getEmail(), user.getRole().name());
    }
}
