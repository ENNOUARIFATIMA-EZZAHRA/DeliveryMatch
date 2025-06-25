import { Component } from '@angular/core';

@Component({
  selector: 'app-demande-detail',
  standalone: true,
  imports: [],
  templateUrl: './demande-detail.html',
  styleUrl: './demande-detail.css'
})
export class DemandeDetail {
  demande = {
    expediteur: { nom: 'Ali', prenom: 'Ben', email: 'ali.ben@email.com' },
    conducteur: { nom: 'Omar', prenom: 'Said', email: 'omar.said@email.com' },
    villeDepart: 'Fès',
    villeArrivee: 'Marrakech',
    date: '2024-06-12',
    statut: 'EN_ATTENTE',
    message: 'Besoin de livraison urgente.'
  };
}
