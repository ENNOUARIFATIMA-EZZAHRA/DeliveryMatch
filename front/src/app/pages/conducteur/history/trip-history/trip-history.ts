import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceService } from '../../../../service/annonce/annonce.service';
import { TripHistory } from '../../../../models/trip-history.model';

@Component({
  selector: 'app-trip-history',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './trip-history.html',
  styleUrls: ['./trip-history.css']
})
export class TripHistoryComponent implements OnInit {
  trips: TripHistory[] = [];
  isLoading = true;
  errorMessage = '';

  constructor(private annonceService: AnnonceService) {}

  ngOnInit(): void {
    this.fetchTripHistory();
  }

  fetchTripHistory(): void {
    const conducteurId = 1; 
    this.annonceService.getTripHistoryByConducteurId(conducteurId).subscribe({
      next: (data) => {
        this.trips = data;
        this.isLoading = false;
      },
      error: (err) => {
        this.errorMessage = 'Erreur lors du chargement du trip history';
        this.isLoading = false;
      }
    });
  }
}
