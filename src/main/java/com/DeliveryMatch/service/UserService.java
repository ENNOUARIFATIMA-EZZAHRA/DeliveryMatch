package com.DeliveryMatch.service;

import com.DeliveryMatch.dto.UserDTO;
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

        User user = new User();
        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setMotDePass(passwordEncoder.encode(userDTO.getMotDePass()));
        user.setDateInscription(LocalDateTime.now());

        User savedUser = userRepository.save(user);
        return convertToDTO(savedUser);
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
        return convertToDTO(updatedUser);
    }

    private UserDTO convertToDTO(User user) {
        UserDTO dto = new UserDTO();
        dto.setId(user.getId());
        dto.setNom(user.getNom());
        dto.setPrenom(user.getPrenom());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
