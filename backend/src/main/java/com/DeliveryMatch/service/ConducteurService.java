package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Conducteur;
import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.repository.ConducteurRepository;
import com.DeliveryMatch.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ConducteurService {
    @Autowired
    private ConducteurRepository conducteurRepository;
    @Autowired
    private AnnonceRepository annonceRepository;

    // Trouver un conducteur par son id
    public Optional<Conducteur> trouverParId(Integer id) {
        return conducteurRepository.findById(id);
    }

    // Lister les annonces d'un conducteur
    public List<Annonce> listerAnnoncesParConducteur(Integer conducteurId) {
        return annonceRepository.findAll().stream()
                .filter(a -> a.getConducteur() != null && a.getConducteur().getId().equals(conducteurId))
                .toList();
    }

    // Calculer la note moyenne d'un conducteur
    public float calculerNoteMoyenne(Conducteur conducteur) {
        return conducteur.getNoteMoyenne();
    }

    // Nombre d'annonces d'un conducteur
    public long nombreAnnonces(Integer conducteurId) {
        return listerAnnoncesParConducteur(conducteurId).size();
    }
}
