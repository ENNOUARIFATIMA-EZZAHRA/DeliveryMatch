import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnnonceService} from '../../../service/annonce/annonce.service';
import { DemandeService } from '../../../service/demande/demande.service';
import { Annonce } from '../../../models/annonce.model';
import { RouterModule } from '@angular/router';
@Component({
  selector: 'app-annonces-list',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './annonces-list.html',
  styleUrl: './annonces-list.css'
})
export class AnnoncesListComponent implements OnInit {
  annonces: Annonce[] = [];
  filteredAnnonces: Annonce[] = [];
  loading = true;
  currentPage = 1;
  totalPages = 1;
  sortBy = 'dateDepart';
  
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
    private demandeService: DemandeService
  ) {}

  ngOnInit() {
    this.loadAnnonces();
  }

  loadAnnonces() {
    this.loading = true;
    this.annonceService.getAnnoncesForExpediteur().subscribe({
      next: (annonces) => {
        console.log('Annonces récupérées pour expediteur:', annonces);
        this.annonces = annonces;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des annonces:', error);
        this.loading = false;
      }
    });
  }

  searchAnnonces() {
    this.loading = true;
    
    // Nettoyer les critères vides
    const cleanCriteria = Object.fromEntries(
      Object.entries(this.searchCriteria).filter(([_, value]) => 
        value !== null && value !== undefined && value !== ''
      )
    );

    this.annonceService.getAnnoncesForExpediteur().subscribe({
      next: (annonces: any[]) => {
        this.annonces = this.sortAnnonces(annonces);
        this.loading = false;
      },
      error: (error: any) => {
        console.error('Erreur lors de la recherche:', error);
        this.loading = false;
      }
    });
  }

  sortAnnonces(annonces: Annonce[]): Annonce[] {
    return annonces.sort((a, b) => {
      switch (this.sortBy) {
        case 'capacite':
          return (b.capacite ?? 0) - (a.capacite ?? 0);
        case 'dateDepart':
        default:
          return new Date(a.dateDepart).getTime() - new Date(b.dateDepart).getTime();
      }
    });
  }

  clearFilters() {
    this.searchCriteria = {
      lieuDepart: '',
      destination: '',
      dateDepart: '',
      capaciteMin: null,
      prixMax: null,
      typeVehicule: ''
    };
    this.sortBy = 'dateDepart';
    this.loadAnnonces();
  }

  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
      this.searchAnnonces();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
      this.searchAnnonces();
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

  filterAnnonces() {
    const villes = ['Agadir', 'Fes', 'Casablanca', 'Rabat', 'Beni Mellal'];
    this.filteredAnnonces = this.annonces.filter(annonce => {
      const departValid = villes.includes(annonce.lieuDepart);
      const arriveeValid = villes.includes(annonce.destination);
      const dateValid = annonce.dateDepart && !annonce.dateDepart.startsWith('1970');

      return departValid && arriveeValid && dateValid;
    });
  }
}
