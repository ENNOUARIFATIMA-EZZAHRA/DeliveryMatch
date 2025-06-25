import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AnnonceService } from '../../../../service/annonce/annonce.service';
import { ActivatedRoute, Router, RouterLink } from '@angular/router';
import { Annonce } from '../../../../models/annonce.model';
import { AuthService } from '../../../../service/authService/auth.service';

@Component({
  selector: 'app-annonce-details',
  standalone: true,
  imports: [CommonModule, RouterLink],
  templateUrl: './annonce-details.html',
  styleUrl: './annonce-details.css'
})
export class AnnonceDetails implements OnInit {
  annonce: Annonce | undefined;

  constructor(
    private route: ActivatedRoute,
    private router: Router,
    private annonceService: AnnonceService,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.annonceService.getAnnonceById(+id).subscribe(
        data => {
          this.annonce = data;
        },
        error => {
          console.error('Error fetching annonce details:', error);
        }
      );
    }
  }

  canEditAnnonce(): boolean {
    if (!this.annonce) return false;
    const isEditableStatus = ['EN_ATTENTE', 'ACTIVE'].includes(this.annonce.status);
    return isEditableStatus;
  }

  editAnnonce(): void {
    if (this.annonce) {
      this.router.navigate(['/conducteur/annonce/edit', this.annonce.id]);
    }
  }

  deleteAnnonce(): void {
    if (!this.annonce) return;
    
    if (confirm('Êtes-vous sûr de vouloir supprimer cette annonce ? Cette action est irréversible.')) {
      this.annonceService.deleteAnnonce(this.annonce.id).subscribe({
        next: () => {
          alert('Annonce supprimée avec succès');
          this.router.navigate(['/conducteur/annonces']);
        },
        error: (error) => {
          console.error('Erreur lors de la suppression:', error);
          alert('Erreur lors de la suppression de l\'annonce');
        }
      });
    }
  }
}
