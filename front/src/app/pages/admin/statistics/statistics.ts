import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceService } from '../../../service/annonce/annonce.service';
import { DemandeService } from '../../../service/demande/demande.service';
import { UserService } from '../../../service/UserService/user.service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-statistics',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './statistics.html',
  styles: []
})
export class StatisticsComponent implements OnInit {
  selectedPeriod = '30';
  stats = {
    nouveauxUtilisateurs: 0,
    croissanceUtilisateurs: 0,
    nouvellesAnnonces: 0,
    croissanceAnnonces: 0,
    demandesTraitees: 0,
    tauxAcceptation: 0,
    chiffreAffaires: 0,
    croissanceCA: 0
  };

  demandeStats = {
    acceptees: 0,
    refusees: 0,
    enAttente: 0,
    terminees: 0
  };

  userEvolution = [
    { date: 'Lun', conducteurs: 45, expediteurs: 75, total: 120 },
    { date: 'Mar', conducteurs: 48, expediteurs: 78, total: 126 },
    { date: 'Mer', conducteurs: 52, expediteurs: 82, total: 134 },
    { date: 'Jeu', conducteurs: 55, expediteurs: 85, total: 140 },
    { date: 'Ven', conducteurs: 58, expediteurs: 88, total: 146 },
    { date: 'Sam', conducteurs: 60, expediteurs: 90, total: 150 },
    { date: 'Dim', conducteurs: 62, expediteurs: 92, total: 154 }
  ];

  topVillesDepart = [
    { nom: 'Casablanca', count: 45 },
    { nom: 'Rabat', count: 32 },
    { nom: 'Marrakech', count: 28 },
    { nom: 'Fès', count: 25 },
    { nom: 'Agadir', count: 20 }
  ];

  topVillesArrivee = [
    { nom: 'Marrakech', count: 38 },
    { nom: 'Casablanca', count: 35 },
    { nom: 'Rabat', count: 30 },
    { nom: 'Fès', count: 22 },
    { nom: 'Agadir', count: 18 }
  ];

  performanceMetrics = {
    tempsReponseMoyen: 2.5,
    tauxSatisfaction: 94,
    utilisateursActifs: 1250
  };

  constructor(
    private annonceService: AnnonceService,
    private demandeService: DemandeService,
    private userService: UserService
  ) {}

  ngOnInit() {
    this.loadStatistics();
  }

  loadStatistics() {
    // Charger les statistiques des annonces
    this.annonceService.getAnnonceStats().subscribe({
      next: (stats) => {
        this.stats.nouvellesAnnonces = stats.active;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des stats annonces:', error);
      }
    });

    // Charger les statistiques des demandes
    this.demandeService.getDemandeStats().subscribe({
      next: (stats) => {
        this.demandeStats = {
          acceptees: stats.acceptees,
          refusees: stats.refusees,
          enAttente: stats.enAttente,
          terminees: stats.terminees
        };
        this.stats.demandesTraitees = stats.acceptees + stats.refusees + stats.terminees;
        this.stats.tauxAcceptation = this.stats.demandesTraitees > 0 ? 
          Math.round((stats.acceptees / this.stats.demandesTraitees) * 100) : 0;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des stats demandes:', error);
      }
    });

    // Charger les statistiques des utilisateurs
    this.userService.getUserStats().subscribe({
      next: (stats) => {
        this.stats.nouveauxUtilisateurs = stats.nouveauxCeMois;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des stats utilisateurs:', error);
      }
    });

    // Données fictives pour l'exemple
    this.stats.croissanceUtilisateurs = 12;
    this.stats.croissanceAnnonces = 8;
    this.stats.chiffreAffaires = 125000;
    this.stats.croissanceCA = 15;
  }

  exportReport() {
    // Logique d'export du rapport
    console.log('Export du rapport...');
    // Ici vous pouvez implémenter l'export en PDF ou Excel
  }

  get totalDemandes(): number {
    return this.demandeStats.acceptees + this.demandeStats.refusees + 
           this.demandeStats.enAttente + this.demandeStats.terminees;
  }

  get maxUsers(): number {
    return Math.max(...this.userEvolution.map(u => u.total));
  }

  get maxVilleCount(): number {
    return Math.max(...this.topVillesDepart.map(v => v.count));
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
