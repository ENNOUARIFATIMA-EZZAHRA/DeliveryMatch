package com.DeliveryMatch.service;

import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private UserRepository userRepository;

    // Lister tous les utilisateurs
    public List<User> listerTousLesUtilisateurs() {
        return userRepository.findAll();
    }

    // Activer un utilisateur
    public void activerUtilisateur(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setStatus("ENABLED");
        userRepository.save(user);
    }

    // Suspendre un utilisateur
    public void suspendreUtilisateur(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setStatus("SUSPENDED");
        userRepository.save(user);
    }

    // Vérifier un utilisateur (badge vérifié)
    public void verifierUtilisateur(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setVerified(true);
        userRepository.save(user);
    }

    // Enlever la vérification d'un utilisateur
    public void enleverVerification(Integer id) {
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        user.setVerified(false);
        userRepository.save(user);
    }
}
