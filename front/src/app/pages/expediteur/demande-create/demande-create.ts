import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import {  DemandeService } from '../../../service/demande/demande.service';
import { AnnonceService } from '../../../service/annonce/annonce.service';
import { AuthService } from '../../../service/authService/auth.service';
import { Annonce } from '../../../models/annonce.model';

export interface CreateDemandeRequest {
  annonceId: number;
  poids: number;
  dimensionsColis: string;
  description: string;
  adresseDepart: string;
  adresseArrivee: string;
  status: string;
  dateDemande: string;
}

@Component({
  selector: 'app-demande-create',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './demande-create.html',
  styleUrl: './demande-create.css'
})
export class DemandeCreateComponent implements OnInit {
  demande: CreateDemandeRequest = {
    annonceId: 0,
    poids: 0,
    dimensionsColis: '',
    description: '',
    adresseDepart: '',
    adresseArrivee: '',
    status: 'EN_ATTENTE',
    dateDemande: new Date().toISOString(),
  };

  annonce: Annonce | null = null;
  isSubmitting = false;
  errorMessage = '';
  calculatedPrice = 0;
  dateNow = new Date().toLocaleString('fr-FR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
    hour: '2-digit',
    minute: '2-digit'
  });

  constructor(
    private demandeService: DemandeService,
    private annonceService: AnnonceService,
    private authService: AuthService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadAnnonce();
  }

  loadAnnonce() {
    const idParam = this.route.snapshot.paramMap.get('id');
    if (idParam) {
      this.demande.annonceId = parseInt(idParam, 10);
      this.annonceService.getAnnonceById(this.demande.annonceId).subscribe({
        next: (annonce) => {
          this.annonce = annonce;
        },
        error: (error) => {
          console.error('Erreur lors du chargement de l\'annonce:', error);
          this.errorMessage = 'Impossible de charger les informations de l\'annonce';
        }
      });
    } else {
      this.errorMessage = 'Aucune annonce sélectionnée';
    }
  }

  calculatePrice(): void {
    this.calculatedPrice = this.calculateWeightPrice();
  }

  calculateWeightPrice(): number {
    if (!this.demande.poids) return 0;
    
    // Prix par kg supplémentaire (exemple: 2 MAD par kg)
    const pricePerKg = 2;
    return this.demande.poids * pricePerKg;
  }

  isValidDemande(): boolean {
    if (!this.annonce) return false;
    
    // Vérifier que le poids ne dépasse pas la capacité
    if ((this.demande.poids ?? 0) > (this.annonce.capacite ?? 0)) {
      return false;
    }
    
    // Vérifier que tous les champs requis sont remplis
    if (!this.demande.poids || !this.demande.description || 
        !this.demande.adresseDepart || !this.demande.adresseArrivee) {
      return false;
    }
    
    return true;
  }

  onSubmit() {
    if (this.isSubmitting || !this.isValidDemande()) return;
    this.isSubmitting = true;
    this.errorMessage = '';
    this.demande.status = 'EN_ATTENTE';
    this.demande.dateDemande = new Date().toISOString();
    this.demande.annonceId = this.annonce?.id || 0;
    if (!this.demande.poids || this.demande.poids <= 0) {
      this.errorMessage = 'Le poids doit être supérieur à 0';
      this.isSubmitting = false;
      return;
    }
    if (this.demande.poids > (this.annonce?.capacite ?? 0)) {
      this.errorMessage = 'Le poids dépasse la capacité disponible';
      this.isSubmitting = false;
      return;
    }
    this.demandeService.createDemande(this.demande).subscribe({
      next: (response) => {
        this.isSubmitting = false;
        this.router.navigate(['/expediteur/history']);
      },
      error: (error) => {
        this.isSubmitting = false;
        this.errorMessage = error.message || 'Une erreur est survenue lors de la création de la demande';
      }
    });
  }

  onCancel() {
    this.router.navigate(['/expediteur/annonces']);
  }

  formatDate(dateString: string): string {
    return new Date(dateString).toLocaleDateString('fr-FR', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
