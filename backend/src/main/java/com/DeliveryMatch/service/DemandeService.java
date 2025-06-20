package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DemandeService {
    @Autowired
    private DemandeTransportRepository demandeRepository;

    // Envoyer une nouvelle demande
    public Demande envoyerDemande(Demande demande) {
        return demandeRepository.save(demande);
    }

    // Lister les demandes d'un expéditeur
    public List<Demande> listerDemandesParExpediteur(Integer expediteurId) {
        return demandeRepository.findByExpediteurId(expediteurId);
    }

    // Trouver une demande par son id
    public Optional<Demande> trouverParId(Integer id) {
        return demandeRepository.findById(id);
    }

    // Supprimer une demande par son id
    public void supprimerDemande(Integer id) {
        demandeRepository.deleteById(id);
    }

    // Accepter une demande
    public Demande accepterDemande(Integer id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        demande.setStatus("ACCEPTEE");
        return demandeRepository.save(demande);
    }

    // Refuser une demande
    public Demande refuserDemande(Integer id) {
        Demande demande = demandeRepository.findById(id).orElseThrow(() -> new RuntimeException("Demande non trouvée"));
        demande.setStatus("REFUSEE");
        return demandeRepository.save(demande);
    }
}
