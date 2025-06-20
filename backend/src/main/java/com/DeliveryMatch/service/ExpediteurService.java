package com.DeliveryMatch.service;

import com.DeliveryMatch.model.Expediteur;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.repository.ExpediteurRepository;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExpediteurService {
    @Autowired
    private ExpediteurRepository expediteurRepository;
    @Autowired
    private DemandeTransportRepository demandeRepository;

    // Trouver un expéditeur par son id
    public Optional<Expediteur> trouverParId(Integer id) {
        return expediteurRepository.findById(id);
    }

    // Lister les demandes d'un expéditeur
    public List<Demande> listerDemandesParExpediteur(Integer expediteurId) {
        return demandeRepository.findByExpediteurId(expediteurId);
    }

    // Nombre de demandes d'un expéditeur
    public long nombreDemandes(Integer expediteurId) {
        return listerDemandesParExpediteur(expediteurId).size();
    }
}
