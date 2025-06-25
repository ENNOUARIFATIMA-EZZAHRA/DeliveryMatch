import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../../service/UserService/user.service';
import { User } from '../../../models/user.model/user.model-module';

@Component({
  selector: 'app-users',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './users.html',
  styles: []
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  filteredUsers: User[] = [];
  loading = true;
  isSubmitting = false;
  showAddUserModal = false;
  editingUser: User | null = null;
  
  searchCriteria = {
    nom: '',
    email: '',
    role: '',
    statut: ''
  };

  userFormData = {
    nom: '',
    prenom: '',
    email: '',
    motDePass: '',
    role: '',
    telephone: '',
    adresse: ''
  };

  constructor(private userService: UserService) {}

  ngOnInit() {
    this.loadUsers();
  }

  loadUsers() {
    this.loading = true;
    this.userService.getAllUsers().subscribe({
      next: (users) => {
        this.users = users;
        this.filteredUsers = users;
        this.loading = false;
      },
      error: (error) => {
        console.error('Erreur lors du chargement des utilisateurs:', error);
        this.loading = false;
      }
    });
  }

  searchUsers() {
    this.filteredUsers = this.users.filter(user => {
      const nomMatch = !this.searchCriteria.nom || 
        (user.nom.toLowerCase().includes(this.searchCriteria.nom.toLowerCase()) ||
         user.prenom.toLowerCase().includes(this.searchCriteria.nom.toLowerCase()));
      
      const emailMatch = !this.searchCriteria.email || 
        user.email.toLowerCase().includes(this.searchCriteria.email.toLowerCase());
      
      const roleMatch = !this.searchCriteria.role || user.role === this.searchCriteria.role;
      const statutMatch = !this.searchCriteria.statut || user.statut === this.searchCriteria.statut;
      
      return nomMatch && emailMatch && roleMatch && statutMatch;
    });
  }

  editUser(user: User) {
    this.editingUser = user;
    this.userFormData = {
      nom: user.nom,
      prenom: user.prenom,
      email: user.email,
      motDePass: '',
      role: user.role,
      telephone: user.telephone || '',
      adresse: user.adresse || ''
    };
    this.showAddUserModal = true;
  }

  saveUser() {
    if (this.isSubmitting) return;

    this.isSubmitting = true;

    if (this.editingUser) {
      // Mise à jour
      this.userService.updateUser(this.editingUser.id!, this.userFormData).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.closeModal();
          this.loadUsers();
        },
        error: (error) => {
          console.error('Erreur lors de la mise à jour:', error);
          this.isSubmitting = false;
        }
      });
    } else {
      // Création
      this.userService.createUser(this.userFormData).subscribe({
        next: () => {
          this.isSubmitting = false;
          this.closeModal();
          this.loadUsers();
        },
        error: (error) => {
          console.error('Erreur lors de la création:', error);
          this.isSubmitting = false;
        }
      });
    }
  }

  toggleUserStatus(user: User) {
    const newStatus = !user.isActive;
    this.userService.toggleUserStatus(user.id!, newStatus).subscribe({
      next: () => {
        this.loadUsers();
      },
      error: (error) => {
        console.error('Erreur lors du changement de statut:', error);
      }
    });
  }

  deleteUser(id: number) {
    if (confirm('Êtes-vous sûr de vouloir supprimer cet utilisateur ?')) {
      this.userService.deleteUser(id).subscribe({
        next: () => {
          this.loadUsers();
        },
        error: (error) => {
          console.error('Erreur lors de la suppression:', error);
        }
      });
    }
  }

  exportUsers() {
    this.userService.exportUsers('csv').subscribe({
      next: (blob) => {
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'utilisateurs.csv';
        a.click();
        window.URL.revokeObjectURL(url);
      },
      error: (error) => {
        console.error('Erreur lors de l\'export:', error);
      }
    });
  }

  closeModal() {
    this.showAddUserModal = false;
    this.editingUser = null;
    this.userFormData = {
      nom: '',
      prenom: '',
      email: '',
      motDePass: '',
      role: '',
      telephone: '',
      adresse: ''
    };
  }

  getRoleClass(role: string): string {
    switch (role) {
      case 'ADMIN': return 'bg-purple-100 text-purple-800';
      case 'CONDUCTEUR': return 'bg-blue-100 text-blue-800';
      case 'EXPEDITEUR': return 'bg-green-100 text-green-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getRoleLabel(role: string): string {
    switch (role) {
      case 'ADMIN': return 'Administrateur';
      case 'CONDUCTEUR': return 'Conducteur';
      case 'EXPEDITEUR': return 'Expéditeur';
      default: return role;
    }
  }

  getStatusClass(statut?: string): string {
    switch (statut) {
      case 'ACTIF': return 'bg-green-100 text-green-800';
      case 'INACTIF': return 'bg-red-100 text-red-800';
      case 'SUSPENDU': return 'bg-yellow-100 text-yellow-800';
      default: return 'bg-gray-100 text-gray-800';
    }
  }

  getStatusLabel(statut?: string): string {
    switch (statut) {
      case 'ACTIF': return 'Actif';
      case 'INACTIF': return 'Inactif';
      case 'SUSPENDU': return 'Suspendu';
      default: return 'Inconnu';
    }
  }

  formatDate(dateString?: string): string {
    if (!dateString) return 'Non renseigné';
    return new Date(dateString).toLocaleDateString('fr-FR');
  }
}
