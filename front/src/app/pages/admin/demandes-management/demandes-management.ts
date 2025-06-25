import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { DemandeService } from '../../../service/demande/demande.service';
import { AnnonceService } from '../../../service/annonce/annonce.service';

@Component({
  selector: 'app-demandes-management',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './demandes-management.html',
  styles: []
})
export class DemandesManagementComponent implements OnInit {
  demandes: any[] = [];
  filteredDemandes: any[] = [];
  stats = {
    total: 0,
    acceptees: 0,
    enAttente: 0,
    refusees: 0
  };
  
  filters = {
    status: '',
    expediteur: '',
    conducteur: '',
    date: ''
  };
  
  currentPage = 1;
  itemsPerPage = 10;
  totalItems = 0;
  totalPages = 1;
  
  constructor(
    private demandeService: DemandeService,
    private annonceService: AnnonceService
  ) {}
  
  ngOnInit() {
    this.loadDemandes();
    this.loadStats();
  }
  
  loadDemandes() {
    this.demandeService.getAllDemandes().subscribe({
      next: (demandes) => {
        this.demandes = demandes;
        this.applyFilters();
      },
      error: (error) => {
        console.error('Erreur lors du chargement des demandes:', error);
      }
    });
  }
  
  loadStats() {
    this.demandeService.getDemandeStats().subscribe({
      next: (stats) => {
        this.stats = {
          total: stats.total || 0,
          acceptees: stats.acceptees || 0,
          enAttente: stats.enAttente || 0,
          refusees: stats.refusees || 0
        };
      },
      error: (error) => {
        console.error('Erreur lors du chargement des statistiques:', error);
      }
    });
  }
  
  applyFilters() {
    this.filteredDemandes = this.demandes.filter(demande => {
      if (this.filters.status && demande.statut !== this.filters.status) return false;
      if (this.filters.expediteur && !demande.expediteur?.nom?.toLowerCase().includes(this.filters.expediteur.toLowerCase())) return false;
      if (this.filters.conducteur && !demande.annonce?.conducteur?.nom?.toLowerCase().includes(this.filters.conducteur.toLowerCase())) return false;
      if (this.filters.date && demande.dateCreation !== this.filters.date) return false;
      return true;
    });
    
    this.totalItems = this.filteredDemandes.length;
    this.totalPages = Math.ceil(this.totalItems / this.itemsPerPage);
    this.currentPage = 1;
  }
  
  clearFilters() {
    this.filters = {
      status: '',
      expediteur: '',
      conducteur: '',
      date: ''
    };
    this.applyFilters();
  }
  
  getStatusClass(status: string): string {
    switch (status) {
      case 'ACCEPTED': return 'bg-green-100 text-green-800';
      case 'PENDING': return 'bg-yellow-100 text-yellow-800';
      case 'REJECTED': return 'bg-red-100 text-red-800';
      case 'COMPLETED': return 'bg-blue-100 text-blue-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }
  
  getStatusLabel(status: string): string {
    switch (status) {
      case 'ACCEPTED': return 'Acceptée';
      case 'PENDING': return 'En attente';
      case 'REJECTED': return 'Refusée';
      case 'COMPLETED': return 'Terminée';
      default: return 'Inconnu';
    }
  }
  
  viewDemande(demande: any) {
    // Navigation vers la page de détails
    console.log('Voir demande:', demande);
  }
  
  approveDemande(id: number) {
    this.demandeService.approveDemande(id).subscribe({
      next: () => {
        this.loadDemandes();
        this.loadStats();
      },
      error: (error) => {
        console.error('Erreur lors de l\'approbation:', error);
      }
    });
  }
  
  rejectDemande(id: number) {
    this.demandeService.rejectDemande(id).subscribe({
      next: () => {
        this.loadDemandes();
        this.loadStats();
      },
      error: (error) => {
        console.error('Erreur lors du rejet:', error);
      }
    });
  }
  
  deleteDemande(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cette demande ?')) {
      this.demandeService.deleteDemande(id).subscribe({
        next: () => {
          this.loadDemandes();
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
}
