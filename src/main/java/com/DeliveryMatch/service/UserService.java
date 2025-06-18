package com.DeliveryMatch.service;

import com.DeliveryMatch.dto.UserDTO;
import com.DeliveryMatch.model.Expediteur;
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

        Expediteur expediteur = new Expediteur();
        expediteur.setNom(userDTO.getNom());
        expediteur.setPrenom(userDTO.getPrenom());
        expediteur.setEmail(userDTO.getEmail());
        expediteur.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        expediteur.setDateInscription(LocalDateTime.now());

        Expediteur savedUser = (Expediteur) userRepository.save(expediteur);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(Integer id, UserDTO userDTO) {
        Expediteur expediteur = (Expediteur) userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        expediteur.setNom(userDTO.getNom());
        expediteur.setPrenom(userDTO.getPrenom());
        expediteur.setEmail(userDTO.getEmail());
        if (userDTO.getMotDePass() != null && !userDTO.getMotDePass().isEmpty()) {
            expediteur.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        }

        Expediteur updatedUser = (Expediteur) userRepository.save(expediteur);
        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(Expediteur expediteur) {
        UserDTO dto = new UserDTO();
        dto.setId(expediteur.getId());
        dto.setNom(expediteur.getNom());
        dto.setPrenom(expediteur.getPrenom());
        dto.setEmail(expediteur.getEmail());
        return dto;
    }
}
