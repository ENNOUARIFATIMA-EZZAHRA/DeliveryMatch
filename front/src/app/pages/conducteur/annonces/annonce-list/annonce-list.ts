import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AnnonceService } from '../../../../service/annonce/annonce.service';
import { AuthService } from '../../../../service/authService/auth.service';
import { Annonce } from '../../../../models/annonce.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-annonce-list',
  standalone: true,
  imports: [CommonModule, RouterLink, FormsModule],
  templateUrl: './annonce-list.html',
  styleUrl: './annonce-list.css'
})
export class AnnonceListComponent implements OnInit {
  annonces: Annonce[] = [];
  filteredAnnonces: Annonce[] = [];
  loading = true;
  
  // Filtres
  searchCriteria = {
    lieuDepart: '',
    destination: '',
    dateDepart: '',
    capaciteMin: null,
    prixMax: null,
    typeVehicule: ''
  };

  constructor(
    private annonceService: AnnonceService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit() {
    this.loadAnnonces();
  }

  loadAnnonces() {
    this.loading = true;
    const currentUser = this.authService.getCurrentUser();
    const conducteurId = currentUser ? currentUser.id : null;
    console.log('Conducteur ID utilisé pour la recherche:', conducteurId);
    if (!conducteurId) {
      this.loading = false;
      this.annonces = [];
      this.filteredAnnonces = [];
      return;
    }
    this.annonceService.getAnnoncesByConducteur(conducteurId).subscribe({
      next: (annonces: any[]) => {
        this.annonces = annonces;
        this.filteredAnnonces = this.annonces;
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Erreur lors du chargement des annonces:', error);
        this.loading = false;
      }
    });
  }

  filterAnnonces() {
    this.filteredAnnonces = this.annonces.filter(annonce => {
      const departMatch = !this.searchCriteria.lieuDepart || annonce.lieuDepart?.toLowerCase().includes(this.searchCriteria.lieuDepart.toLowerCase());
      const arriveeMatch = !this.searchCriteria.destination || annonce.destination?.toLowerCase().includes(this.searchCriteria.destination.toLowerCase());
      const dateMatch = !this.searchCriteria.dateDepart || (annonce.dateDepart && annonce.dateDepart.startsWith(this.searchCriteria.dateDepart));
      const capaciteMatch = !this.searchCriteria.capaciteMin || (annonce.capacite && annonce.capacite >= this.searchCriteria.capaciteMin);
      const prixMatch = !this.searchCriteria.prixMax || (annonce.prix !== undefined && annonce.prix <= this.searchCriteria.prixMax);
      const typeVehiculeMatch = !this.searchCriteria.typeVehicule || (annonce.typeVehicule && annonce.typeVehicule.toLowerCase().includes(this.searchCriteria.typeVehicule.toLowerCase()));
      return departMatch && arriveeMatch && dateMatch && capaciteMatch && prixMatch && typeVehiculeMatch;
    });
  }

  getStatusClass(status?: string): string {
    switch (status) {
      case 'ACTIVE': return 'bg-green-100 text-green-800';
      case 'EN_ATTENTE': return 'bg-yellow-100 text-yellow-800';
      case 'EN_COURS': return 'bg-blue-100 text-blue-800';
      case 'TERMINEE': return 'bg-gray-100 text-gray-800';
      case 'ANNULEE': return 'bg-red-100 text-red-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatusLabel(status?: string): string {
    switch (status) {
      case 'ACTIVE': return 'Active';
      case 'EN_ATTENTE': return 'En Attente';
      case 'EN_COURS': return 'En cours';
      case 'TERMINEE': return 'Terminée';
      case 'ANNULEE': return 'Annulée';
      default: return 'Inconnu';
    }
  }

  getVehicleTypeLabel(type: string): string {
    switch (type) {
      case 'VOITURE': return 'Voiture';
      case 'CAMION': return 'Camion';
      case 'MOTO': return 'Moto';
      case 'VAN': return 'Van';
      default: return type;
    }
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

  canEditAnnonce(annonce: Annonce): boolean {
    
    const isEditableStatus = ['EN_ATTENTE', 'ACTIVE'].includes(annonce.status);
    return isEditableStatus;
  }

  editAnnonce(id: number) {
    // Navigation vers la page de modification
    this.router.navigate(['/conducteur/annonce/edit', id]);
  }

  deleteAnnonce(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ? Cette action est irréversible.')) {
      this.annonceService.deleteAnnonce(id).subscribe({
        next: () => {
          alert('Annonce supprimée avec succès');
          this.loadAnnonces(); // Recharger la liste
        },
        error: (error) => {
          console.error('Erreur lors de la suppression:', error);
          alert('Erreur lors de la suppression de l\'annonce');
        }
      });
    }
  }
} 