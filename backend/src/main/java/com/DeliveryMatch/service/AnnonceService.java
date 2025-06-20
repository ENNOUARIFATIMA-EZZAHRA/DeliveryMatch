package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.repository.AnnonceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    // Ajouter une nouvelle annonce
    public Annonce ajouterAnnonce(Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    // Lister toutes les annonces
    public List<Annonce> listerAnnonces() {
        return annonceRepository.findAll();
    }

    // Trouver une annonce par son id
    public Optional<Annonce> trouverParId(Integer id) {
        return annonceRepository.findById(id);
    }

    // Supprimer une annonce par son id
    public void supprimerAnnonce(Integer id) {
        annonceRepository.deleteById(id);
    }

    // Lister les annonces par destination et date
    public List<Annonce> listerParDestinationEtDate(String destination, Date dateDepart) {
        return annonceRepository.findByDestinationContainingIgnoreCaseAndDateDepart(destination, dateDepart);
    }
}
