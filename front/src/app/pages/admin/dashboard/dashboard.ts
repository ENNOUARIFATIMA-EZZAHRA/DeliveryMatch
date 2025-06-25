import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { RouterLink } from '@angular/router';
import { AnnonceService } from '../../../service/annonce/annonce.service';
import { DemandeService } from '../../../service/demande/demande.service';
import { UserService } from '../../../service/UserService/user.service';

@Component({
  selector: 'app-admin-dashboard',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './dashboard.html',
  styles: []
})
export class AdminDashboardComponent implements OnInit {
  stats = {
    utilisateurs: 0,
    nouveauxUtilisateurs: 0,
    annonces: 0,
    nouvellesAnnonces: 0,
    demandes: 0,
    demandesEnAttente: 0,
    chiffreAffaires: 0,
    croissanceCA: 0
  };

  userStats = {
    conducteurs: 0,
    expediteurs: 0,
    administrateurs: 0
  };

  recentActivity = [
    {
      type: 'user',
      title: 'Nouveau conducteur inscrit',
      description: 'Ahmed Benali s\'est inscrit comme conducteur',
      timestamp: new Date(Date.now() - 1000 * 60 * 30) // 30 minutes ago
    },
    {
      type: 'annonce',
      title: 'Nouvelle annonce créée',
      description: 'Trajet Casablanca → Marrakech ajouté',
      timestamp: new Date(Date.now() - 1000 * 60 * 60) // 1 hour ago
    },
    {
      type: 'demande',
      title: 'Demande de transport acceptée',
      description: 'Demande #123 acceptée par le conducteur',
      timestamp: new Date(Date.now() - 1000 * 60 * 90) // 1.5 hours ago
    }
  ];

  constructor(
    private annonceService: AnnonceService,
    private demandeService: DemandeService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.loadStats();
    this.loadUserStats();
  }

  loadStats() {
    // Charger les statistiques des annonces
    this.annonceService.getAnnonceStats().subscribe({
      next: (stats) => {
        this.stats.annonces = stats.total;
        this.stats.nouvellesAnnonces = stats.active;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des stats annonces:', error);
      }
    });

    // Charger les statistiques des demandes
    this.demandeService.getDemandeStats().subscribe({
      next: (stats) => {
        this.stats.demandes = stats.total;
        this.stats.demandesEnAttente = stats.enAttente;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des stats demandes:', error);
      }
    });

    // Données fictives pour l'exemple
    this.stats.utilisateurs = 1250;
    this.stats.nouveauxUtilisateurs = 45;
    this.stats.chiffreAffaires = 125000;
    this.stats.croissanceCA = 12;
  }

  loadUserStats() {
    // Données fictives pour l'exemple
    this.userStats = {
      conducteurs: 450,
      expediteurs: 750,
      administrateurs: 5
    };
  }

  get totalUsers(): number {
    return this.userStats.conducteurs + this.userStats.expediteurs + this.userStats.administrateurs;
  }

  getActivityIconClass(type: string): string {
    switch (type) {
      case 'user': return 'bg-blue-100 text-blue-600';
      case 'annonce': return 'bg-green-100 text-green-600';
      case 'demande': return 'bg-yellow-100 text-yellow-600';
      default: return 'bg-gray-100 text-gray-600';
    }
  }

  formatTimeAgo(date: Date): string {
    const now = new Date();
    const diffInMinutes = Math.floor((now.getTime() - date.getTime()) / (1000 * 60));
    
    if (diffInMinutes < 60) {
      return `Il y a ${diffInMinutes} min`;
    } else if (diffInMinutes < 1440) {
      const hours = Math.floor(diffInMinutes / 60);
      return `Il y a ${hours}h`;
    } else {
      const days = Math.floor(diffInMinutes / 1440);
      return `Il y a ${days}j`;
    }
  }

  getCircleCircumference(): number {
    return 2 * Math.PI * 40; // r = 40
  }

  getCircleOffset(value: number, total: number): number {
    if (total === 0) return 0;
    const percentage = value / total;
    return this.getCircleCircumference() * (1 - percentage);
  }
}
