package com.DeliveryMatch.service;

import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.Expediteur;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDTO register(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new RuntimeException("Email déjà utilisé");
        }

        Expediteur user = new Expediteur();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        user.setDateInscription(LocalDateTime.now());
        
        User savedUser = userRepository.save(user);
        return convertToDTO((Expediteur) savedUser);
    }

    public UserDTO login(String email, String password) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        if (!passwordEncoder.matches(password, user.getMotDePass())) {
            throw new RuntimeException("Mot de passe incorrect");
        }
        
        return convertToDTO((Expediteur) user);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        if (userDTO.getMotDePass() != null && !userDTO.getMotDePass().isEmpty()) {
            user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        }
        
        User updatedUser = userRepository.save(user);
        return convertToDTO((Expediteur) updatedUser);
    }

    public void validerUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setStatus("ENABLED");
        userRepository.save(user);
    }

    public void suspendreUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setStatus("SUSPENDED");
        userRepository.save(user);
    }

    public void verifierUser(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setVerified(true);
        userRepository.save(user);
    }

    public void enleverVerification(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setVerified(false);
        userRepository.save(user);
    }

    private UserDTO convertToDTO(Expediteur user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        // dto.setDateInscription(LocalDateTime.now()); // On ne set pas la date d'inscription sur le DTO directement
        return dto;
    }
}
