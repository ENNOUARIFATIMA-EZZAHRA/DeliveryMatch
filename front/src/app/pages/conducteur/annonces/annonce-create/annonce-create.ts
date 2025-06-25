import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AnnonceService } from '../../../../service/annonce/annonce.service';
import { AuthService } from '../../../../service/authService/auth.service';

@Component({
  selector: 'app-annonce-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './annonce-create.html',
  styleUrl: './annonce-create.css'
})
export class AnnonceCreateComponent {
  annonce = {
    lieuDepart: '',
    destination: '',
    dateDepart: '',
    capacite: null,
    description: '',
    typeVehicule: ''
  };

  isSubmitting = false;
  errorMessage = '';

  constructor(
    private annonceService: AnnonceService,
    private router: Router,
    private authService: AuthService
  ) {}

  onSubmit() {
    if (this.isSubmitting) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    // Vérification stricte de tous les champs obligatoires
    if (!this.annonce.lieuDepart || this.annonce.lieuDepart.trim() === '') {
      this.errorMessage = 'Veuillez sélectionner la ville de départ.';
      this.isSubmitting = false;
      return;
    }
    if (!this.annonce.destination || this.annonce.destination.trim() === '') {
      this.errorMessage = 'Veuillez sélectionner la ville d\'arrivée.';
      this.isSubmitting = false;
      return;
    }
    if (this.annonce.lieuDepart === this.annonce.destination) {
      this.errorMessage = 'La ville de départ et d\'arrivée ne peuvent pas être identiques';
      this.isSubmitting = false;
      return;
    }
    if (!this.annonce.capacite || isNaN(this.annonce.capacite) || this.annonce.capacite <= 0) {
      this.errorMessage = 'Veuillez saisir une capacité valide (supérieure à 0)';
      this.isSubmitting = false;
      return;
    }
    if (!this.annonce.dateDepart) {
      this.errorMessage = 'Veuillez sélectionner une date de départ';
      this.isSubmitting = false;
      return;
    }
    // Vérification du format de la date (yyyy-MM-dd)
    const dateRegex = /^\d{4}-\d{2}-\d{2}$/;
    const dateDepartStr = new Date(this.annonce.dateDepart).toISOString().split('T')[0];
    if (!dateRegex.test(dateDepartStr)) {
      this.errorMessage = 'Le format de la date de départ est invalide (attendu: yyyy-MM-dd)';
      this.isSubmitting = false;
      return;
    }
    // Vérification que la date n'est pas dans le passé
    const today = new Date();
    const dateDepart = new Date(this.annonce.dateDepart);
    today.setHours(0,0,0,0);
    dateDepart.setHours(0,0,0,0);
    if (dateDepart < today) {
      this.errorMessage = 'La date de départ ne peut pas être dans le passé';
      this.isSubmitting = false;
      return;
    }
    
    const currentUser = this.authService.getCurrentUser();
    const conducteurId = currentUser ? currentUser.id : null;
    if (!conducteurId) {
      this.errorMessage = 'Utilisateur non authentifié (conducteurId manquant).';
      this.isSubmitting = false;
      return;
    }
    // Conversion correcte de la date au format yyyy-MM-dd
    const formDate = new Date(this.annonce.dateDepart);
    const dateString = formDate.toISOString().split('T')[0];

    // Préparation du payload strict pour le backend
    const annonceBackend = {
      lieuDepart: this.annonce.lieuDepart,
      destination: this.annonce.destination,
      dateDepart: dateString,
      capacite: parseFloat(this.annonce.capacite),
      conducteurId: conducteurId,
      status: 'EN_ATTENTE'
    };
    // Debug: afficher le payload dans la console
    console.log('Payload envoyé à l\'API:', annonceBackend);

    this.annonceService.createAnnonce(annonceBackend).subscribe({
      next: () => {
        this.isSubmitting = false;
        this.router.navigate(['/conducteur/annonces']);
      },
      error: (error) => {
        this.isSubmitting = false;
        this.errorMessage = error.error?.error || error.message || 'Une erreur est survenue lors de la création de l\'annonce';
      }
    });
  }

  onCancel() {
    this.router.navigate(['/conducteur/annonces']);
  }
}
