package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Colis;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.ColisRepository;
import com.DeliveryMatch.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class ColisService {

    @Autowired
    private ColisRepository colisRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean isExpediteur(Integer colisId, Authentication authentication) {
        Colis colis = colisRepository.findById(colisId).orElse(null);
        if (colis == null) {
            return false;
        }
        User user = userRepository.findByEmail(authentication.getName()).orElse(null);
        return user != null && colis.getExpediteur().getId().equals(user.getId());
    }
} 