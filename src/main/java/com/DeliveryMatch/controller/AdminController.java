package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.User;
import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import com.DeliveryMatch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import java.util.List;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnonceRepository annonceRepository;
    @Autowired
    private DemandeTransportRepository demandeRepository;
    @Autowired
    private UserService userService;

    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public Map<String, Object> dashboard() {
        Map<String, Object> dashboard = new HashMap<>();
        dashboard.put("utilisateurs", userRepository.findAll());
        dashboard.put("annonces", annonceRepository.findAll());
        dashboard.put("demandes", demandeRepository.findAll());
        return dashboard;
    }

    @PostMapping("/valider/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, String>> validerUser(@PathVariable Integer id) {
        userService.validerUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur validé avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/suspendre/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, String>> suspendreUser(@PathVariable Integer id) {
        userService.suspendreUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Utilisateur suspendu avec succès");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/verifier/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, String>> verifierUser(@PathVariable Integer id) {
        userService.verifierUser(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Badge Vérifié ajouté بنجاح");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/enlever-verification/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, String>> enleverVerification(@PathVariable Integer id) {
        userService.enleverVerification(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Badge Vérifié تمت إزالته بنجاح");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/annonces")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @PostMapping("/annonces")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public Annonce createAnnonce(@RequestBody Annonce annonce) {
        return annonceRepository.save(annonce);
    }

    @PutMapping("/annonces/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public Annonce updateAnnonce(@PathVariable Integer id, @RequestBody Annonce annonce) {
        Annonce existing = annonceRepository.findById(id).orElseThrow(() -> new RuntimeException("Annonce non trouvée"));
        existing.setLieuDepart(annonce.getLieuDepart());
        existing.setDestination(annonce.getDestination());
        existing.setEtapes(annonce.getEtapes());
        existing.setDimensionsMax(annonce.getDimensionsMax());
        existing.setTypeMarchandise(annonce.getTypeMarchandise());
        existing.setCapacite(annonce.getCapacite());
        existing.setDateDepart(annonce.getDateDepart());
        existing.setStatus(annonce.getStatus());
        return annonceRepository.save(existing);
    }

    @DeleteMapping("/annonces/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<Map<String, String>> deleteAnnonce(@PathVariable Integer id) {
        annonceRepository.deleteById(id);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Annonce supprimée avec succès");
        return ResponseEntity.ok(response);
    }
}
