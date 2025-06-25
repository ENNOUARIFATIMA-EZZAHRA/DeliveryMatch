import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { AnnonceService } from '../../../service/annonce/annonce.service';
import { UserService } from '../../../service/UserService/user.service';

@Component({
  selector: 'app-annonces-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './annonces-management.html',
  styles: []
})
export class AnnoncesManagementComponent implements OnInit {
  annonces: any[] = [];
  filteredAnnonces: any[] = [];
  stats = {
    total: 0,
    actives: 0,
    enAttente: 0,
    signalees: 0
  };
  
  filters = {
    status: '',
    lieuDepart: '',
    destination: '',
    date: ''
  };
  
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;
  totalPages = 1;
  
  // Ajout pour le modal d'ajout d'annonce
  showAddAnnonceModal = false;
  isSubmittingAnnonce = false;
  annonceFormData = {
    lieuDepart: '',
    destination: '',
    dateDepart: '',
    prix: 0,
    capaciteDisponible: 0,
    description: '',
    typeVehicule: ''
  };
  
  constructor(
    private annonceService: AnnonceService,
    private userService: UserService
  ) {}
  
  ngOnInit() {
    this.loadAnnonces();
    this.loadStats();
  }
  
  loadAnnonces() {
    this.annonceService.getAllAnnonces().subscribe({
      next: (annonces) => {
        this.annonces = annonces;
        this.applyFilters();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des annonces:', error);
      }
    });
  }
  
  loadStats() {
    this.annonceService.getAnnonceStats().subscribe({
      next: (stats) => {
        this.stats = {
          total: stats.total || 0,
          actives: stats.active || 0,
          enAttente: stats.pending || 0,
          signalees: stats.reported || 0
        };
      },
      error: (error) => {
        console.error('Erreur lors du chargement des statistiques:', error);
      }
    });
  }
  
  applyFilters() {
    this.filteredAnnonces = this.annonces.filter(annonce => {
      if (this.filters.status && annonce.statut !== this.filters.status) return false;
      if (this.filters.lieuDepart && !annonce.lieuDepart.toLowerCase().includes(this.filters.lieuDepart.toLowerCase())) return false;
      if (this.filters.destination && !annonce.destination.toLowerCase().includes(this.filters.destination.toLowerCase())) return false;
      if (this.filters.date && annonce.dateDepart !== this.filters.date) return false;
      return true;
    });
    
    this.totalItems = this.filteredAnnonces.length;
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
    this.currentPage = 1;
  }
  
  clearFilters() {
    this.filters = {
      status: '',
      lieuDepart: '',
      destination: '',
      date: ''
    };
    this.applyFilters();
  }
  
  getStatusClass(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'bg-green-100 text-green-800';
      case 'PENDING': return 'bg-yellow-100 text-yellow-800';
      case 'REJECTED': return 'bg-red-100 text-red-800';
      case 'COMPLETED': return 'bg-blue-100 text-blue-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }
  
  getStatusLabel(status: string): string {
    switch (status) {
      case 'ACTIVE': return 'Active';
      case 'PENDING': return 'En attente';
      case 'REJECTED': return 'Rejetée';
      case 'COMPLETED': return 'Terminée';
      default: return 'Inconnu';
    }
  }
  
  viewAnnonce(annonce: any) {
    // Navigation vers la page de détails
    console.log('Voir annonce:', annonce);
  }
  
  approveAnnonce(id: number) {
    this.annonceService.approveAnnonce(id).subscribe({
      next: () => {
        this.loadAnnonces();
        this.loadStats();
      },
      error: (error) => {
        console.error('Erreur lors de l\'approbation:', error);
      }
    });
  }
  
  rejectAnnonce(id: number) {
    this.annonceService.rejectAnnonce(id).subscribe({
      next: () => {
        this.loadAnnonces();
        this.loadStats();
      },
      error: (error) => {
        console.error('Erreur lors du rejet:', error);
      }
    });
  }
  
  deleteAnnonce(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ?')) {
      this.annonceService.deleteAnnonce(id).subscribe({
        next: () => {
          this.loadAnnonces();
          this.loadStats();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression:', error);
        }
      });
    }
  }
  
  get startIndex(): number {
    return (this.currentPage - 1) * this.itemsPerPage;
  }
  
  get endIndex(): number {
    return Math.min(this.startIndex + this.itemsPerPage, this.totalItems);
  }
  
  get visiblePages(): number[] {
    const pages: number[] = [];
    const start = Math.max(1, this.currentPage - 2);
    const end = Math.min(this.totalPages, this.currentPage + 2);
    
    for (let i = start; i <= end; i++) {
      pages.push(i);
    }
    
    return pages;
  }
  
  previousPage() {
    if (this.currentPage > 1) {
      this.currentPage--;
    }
  }
  
  nextPage() {
    if (this.currentPage < this.totalPages) {
      this.currentPage++;
    }
  }
  
  goToPage(page: number) {
    this.currentPage = page;
  }
  
  closeAnnonceModal() {
    this.showAddAnnonceModal = false;
    this.isSubmittingAnnonce = false;
    this.annonceFormData = {
      lieuDepart: '',
      destination: '',
      dateDepart: '',
      prix: 0,
      capaciteDisponible: 0,
      description: '',
      typeVehicule: ''
    };
  }
  
  saveAnnonce() {
    if (this.isSubmittingAnnonce) return;
    this.isSubmittingAnnonce = true;
    this.annonceService.createAnnonce(this.annonceFormData).subscribe({
      next: () => {
        this.closeAnnonceModal();
        this.loadAnnonces();
      },
      error: (error) => {
        this.isSubmittingAnnonce = false;
        alert('Erreur lors de l\'ajout de l\'annonce.');
        console.error(error);
      }
    });
  }
}
