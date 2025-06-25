import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-demande-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './demande-list.html',
  styleUrl: './demande-list.css'
})
export class DemandeList {
  demandes = [
    {
      id: 1,
      expediteur: { nom: 'Ali', prenom: 'Ben' },
      villeDepart: 'Fès',
      villeArrivee: 'Marrakech',
      date: '2024-06-12',
      statut: 'EN_ATTENTE'
    },
    {
      id: 2,
      expediteur: { nom: 'Sara', prenom: 'Lahcen' },
      villeDepart: 'Agadir',
      villeArrivee: 'Rabat',
      date: '2024-06-15',
      statut: 'ACCEPTEE'
    }
  ];
}
