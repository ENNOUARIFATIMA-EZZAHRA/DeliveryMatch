import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { AnnonceService } from '../../../../service/annonce/annonce.service';
import { AuthService } from '../../../../service/authService/auth.service';
import { Annonce } from '../../../../models/annonce.model';

@Component({
  selector: 'app-annonce-edit',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './annonce-edit.html',
  styleUrl: './annonce-edit.css'
})
export class AnnonceEditComponent implements OnInit {
  annonce: Partial<Annonce> = {
    lieuDepart: '',
    destination: '',
    dateDepart: '',
    capacite: 0,
    description: '',
    typeVehicule: ''
  };

  isSubmitting = false;
  errorMessage = '';
  loading = true;
  annonceId: number | null = null;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private annonceService: AnnonceService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    this.annonceId = Number(this.route.snapshot.paramMap.get('id'));
    if (this.annonceId) {
      this.loadAnnonce();
    } else {
      this.errorMessage = 'ID d\'annonce invalide';
      this.loading = false;
    }
  }

  loadAnnonce(): void {
    if (!this.annonceId) return;

    this.annonceService.getAnnonceById(this.annonceId).subscribe({
      next: (annonce) => {
        // Vérifier si l'utilisateur connecté est le propriétaire de l'annonce
        const currentUser = this.authService.getCurrentUser();
        if (!currentUser || annonce.conducteur?.id !== currentUser.id) {
          this.errorMessage = 'Vous n\'êtes pas autorisé à modifier cette annonce';
          this.loading = false;
          return;
        }

        // Vérifier si l'annonce est modifiable
        if (!['EN_ATTENTE', 'ACTIVE'].includes(annonce.status)) {
          this.errorMessage = 'Cette annonce ne peut plus être modifiée';
          this.loading = false;
          return;
        }

        this.annonce = {
          lieuDepart: annonce.lieuDepart,
          destination: annonce.destination,
          dateDepart: annonce.dateDepart,
          capacite: annonce.capacite,
          description: annonce.description,
          typeVehicule: annonce.typeVehicule
        };
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement de l\'annonce:', error);
        this.errorMessage = 'Impossible de charger les informations de l\'annonce';
        this.loading = false;
      }
    });
  }

  onSubmit(): void {
    if (this.isSubmitting || !this.annonceId) return;

    this.isSubmitting = true;
    this.errorMessage = '';

    // Validation
    if (!this.annonce.lieuDepart || !this.annonce.destination) {
      this.errorMessage = 'Veuillez remplir tous les champs obligatoires';
      this.isSubmitting = false;
      return;
    }

    if (this.annonce.lieuDepart === this.annonce.destination) {
      this.errorMessage = 'La ville de départ et d\'arrivée ne peuvent pas être identiques';
      this.isSubmitting = false;
      return;
    }

    if (!this.annonce.capacite || this.annonce.capacite <= 0) {
      this.errorMessage = 'La capacité doit être supérieure à 0';
      this.isSubmitting = false;
      return;
    }

    // Préparer les données pour l'envoi
    const annonceData = {
      lieuDepart: this.annonce.lieuDepart,
      destination: this.annonce.destination,
      dateDepart: this.annonce.dateDepart ? new Date(this.annonce.dateDepart).toISOString().split('T')[0] : '',
      capacite: this.annonce.capacite,
      typeVehicule: this.annonce.typeVehicule,
      description: this.annonce.description
    };

    // Envoyer les modifications
    this.annonceService.updateAnnonce(this.annonceId, annonceData).subscribe({
      next: () => {
        this.isSubmitting = false;
        alert('Annonce modifiée avec succès');
        this.router.navigate(['/conducteur/annonce', this.annonceId]);
      },
      error: (error) => {
        this.isSubmitting = false;
        this.errorMessage = error.message || 'Une erreur est survenue lors de la modification de l\'annonce';
      }
    });
  }

  onCancel(): void {
    this.router.navigate(['/conducteur/annonce', this.annonceId]);
  }
}
