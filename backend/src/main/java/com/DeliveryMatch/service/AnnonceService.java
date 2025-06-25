package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.dto.AnnonceDTO;
import com.DeliveryMatch.model.Conducteur;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.ConducteurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AnnonceService {
    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private ConducteurRepository conducteurRepository;

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
        return annonceRepository.searchByDestinationAndDateDepart(destination, dateDepart);
    }

    public List<Annonce> searchAnnonces(String destination, Date dateDepart) {
        return annonceRepository.searchByDestinationAndDateDepart(destination, dateDepart);
    }

    public Annonce createAnnonce(AnnonceDTO annonceDTO) {
        if (annonceDTO.getConducteurId() == null) {
            throw new RuntimeException("ConducteurId is required");
        }
        Conducteur conducteur = conducteurRepository.findById(annonceDTO.getConducteurId())
            .orElseThrow(() -> new RuntimeException("Conducteur not found"));

        Annonce annonce = new Annonce();
        annonce.setLieuDepart(annonceDTO.getLieuDepart());
        annonce.setDestination(annonceDTO.getDestination());
        annonce.setEtapes(annonceDTO.getEtapes());
        annonce.setDimensionsMax(annonceDTO.getDimensionsMax());
        annonce.setTypeMarchandise(annonceDTO.getTypeMarchandise());
        annonce.setCapacite(annonceDTO.getCapacite());
        annonce.setStatus(annonceDTO.getStatus());
        annonce.setDateDepart(java.time.LocalDate.parse(annonceDTO.getDateDepart()).atStartOfDay());
        annonce.setConducteur(conducteur);
        annonce.setDescription(annonceDTO.getDescription());
        annonce.setTypeVehicule(annonceDTO.getTypeVehicule());

        System.out.println("Conducteur: " + conducteur.getNom());
        System.out.println("Annonce créée pour conducteur ID = " + conducteur.getId());

        Annonce savedAnnonce = annonceRepository.save(annonce);
        System.out.println(" Annonce enregistrée avec ID = " + savedAnnonce.getId() + ", conducteur_id = " + (savedAnnonce.getConducteur() != null ? savedAnnonce.getConducteur().getId() : null));
        return savedAnnonce;
    }

    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    public Optional<Annonce> getAnnonceById(Integer id) {
        return annonceRepository.findById(id);
    }
}
