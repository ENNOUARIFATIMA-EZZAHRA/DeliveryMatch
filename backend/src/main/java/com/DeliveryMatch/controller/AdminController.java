package com.DeliveryMatch.controller;

import com.DeliveryMatch.model.Annonce;
import com.DeliveryMatch.model.Demande;
import com.DeliveryMatch.model.User;
import com.DeliveryMatch.repository.AnnonceRepository;
import com.DeliveryMatch.repository.DemandeTransportRepository;
import com.DeliveryMatch.repository.UserRepository;
import com.DeliveryMatch.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.*;

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

    // dashboard
    @GetMapping("/dashboard")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> getDashboard() {
        Map<String, Object> data = new HashMap<>();
        data.put("utilisateurs", userRepository.findAll());
        data.put("annonces", annonceRepository.findAll());
        data.put("demandes", demandeRepository.findAll());
        return ResponseEntity.ok(data);
    }

    // check if user exists
    private boolean checkUserExists(Integer id) {
        return id != null && userRepository.existsById(id);
    }

    @PostMapping("/valider/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> validerUser(@PathVariable Integer id) {
        if (!checkUserExists(id)) return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur introuvable"));
        userService.validerUser(id);
        return ResponseEntity.ok(Map.of("message", "Utilisateur validé"));
    }

    @PostMapping("/suspendre/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> suspendreUser(@PathVariable Integer id) {
        if (!checkUserExists(id)) return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur introuvable"));
        userService.suspendreUser(id);
        return ResponseEntity.ok(Map.of("message", "Utilisateur suspendu"));
    }

    @PostMapping("/verifier/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> verifierUser(@PathVariable Integer id) {
        if (!checkUserExists(id)) return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur introuvable"));
        userService.verifierUser(id);
        return ResponseEntity.ok(Map.of("message", "Badge Vérifié ajouté"));
    }

    @PostMapping("/enlever-verification/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> enleverVerification(@PathVariable Integer id) {
        if (!checkUserExists(id)) return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur introuvable"));
        userService.enleverVerification(id);
        return ResponseEntity.ok(Map.of("message", "Badge Vérifié supprimé"));
    }

    // annonces
    @GetMapping("/annonces")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public List<Annonce> getAllAnnonces() {
        return annonceRepository.findAll();
    }

    @PutMapping("/annonces/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> updateAnnonce(@PathVariable Integer id, @RequestBody Annonce annonce) {
        Optional<Annonce> optionalAnnonce = annonceRepository.findById(id);
        if (optionalAnnonce.isEmpty())
            return ResponseEntity.badRequest().body(Map.of("error", "Annonce non trouvée"));

        Annonce existing = optionalAnnonce.get();
        existing.setLieuDepart(annonce.getLieuDepart());
        existing.setDestination(annonce.getDestination());
        existing.setEtapes(annonce.getEtapes());
        existing.setDimensionsMax(annonce.getDimensionsMax());
        existing.setTypeMarchandise(annonce.getTypeMarchandise());
        existing.setCapacite(annonce.getCapacite());
        existing.setDateDepart(annonce.getDateDepart());
        existing.setStatus(annonce.getStatus());
        return ResponseEntity.ok(annonceRepository.save(existing));
    }

    @DeleteMapping("/annonces/{id}")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> deleteAnnonce(@PathVariable Integer id) {
        if (!annonceRepository.existsById(id))
            return ResponseEntity.badRequest().body(Map.of("error", "Annonce introuvable"));
        annonceRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message", "Annonce supprimée"));
    }

    // statistics
    @GetMapping("/profile/stats")
    @PreAuthorize("hasRole('ADMINISTRATEUR')")
    public ResponseEntity<?> getStats() {
        Map<String, Object> stats = new HashMap<>();

        long totalUsers = userRepository.count();
        long conducteurs = userRepository.countConducteurs();
        long expediteurs = userRepository.countExpediteurs();
        long admins = userRepository.countAdmins();

        long totalAnnonces = annonceRepository.count();
        long annoncesActives = annonceRepository.findAll().stream().filter(a -> "ACTIVE".equals(a.getStatus())).count();

        long totalDemandes = demandeRepository.count();
        long demandesAcceptees = demandeRepository.findAll().stream().filter(d -> "ACCEPTEE".equals(d.getStatus())).count();

        double tauxAcceptation = totalDemandes > 0 ? (double) demandesAcceptees / totalDemandes * 100 : 0;

        stats.put("totalUsers", totalUsers);
        stats.put("totalAnnonces", totalAnnonces);
        stats.put("totalDemandes", totalDemandes);
        stats.put("tauxAcceptation", Math.round(tauxAcceptation * 100.0) / 100.0);

        stats.put("userChart", Map.of(
                "labels", List.of("Conducteurs", "Expéditeurs", "Administrateurs"),
                "data", List.of(conducteurs, expediteurs, admins),
                "backgroundColor", List.of("#FF6384", "#36A2EB", "#FFCE56")
        ));

        stats.put("annonceChart", Map.of(
                "labels", List.of("Actives", "Inactives"),
                "data", List.of(annoncesActives, totalAnnonces - annoncesActives),
                "backgroundColor", List.of("#4BC0C0", "#FF9F40")
        ));

        stats.put("demandeChart", Map.of(
                "labels", List.of("Acceptées", "Non acceptées"),
                "data", List.of(demandesAcceptees, totalDemandes - demandesAcceptees),
                "backgroundColor", List.of("#4BC0C0", "#FF9F40")
        ));

        return ResponseEntity.ok(stats);
    }
}
