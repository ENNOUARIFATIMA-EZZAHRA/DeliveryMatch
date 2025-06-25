import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { UserService } from '../../service/UserService/user.service';
import { AuthService } from '../../service/authService/auth.service';
import { ProfileData, User } from '../../models/user.model';

@Component({
  selector: 'app-admin-profile',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class AdminProfileComponent implements OnInit {
  profile: ProfileData = {
    prenom: '',
    nom: '',
    email: ''
  };
  
  passwordData = {
    currentPassword: '',
    newPassword: '',
    confirmPassword: ''
  };
  
  personalStats = {
    annoncesModerees: 0,
    demandesTraitees: 0,
    utilisateursGeres: 0,
    rapportsGeneres: 0
  };
  
  twoFactorEnabled = false;
  emailNotifications = true;
  
  isUpdating = false;
  isChangingPassword = false;
  
  constructor(
    private userService: UserService,
    private authService: AuthService
  ) {}
  
  ngOnInit() {
    this.loadProfile();
    // Only load stats if the user is an admin
    if (this.authService.getUserRole() === 'ADMINISTRATEUR') {
      this.loadPersonalStats();
    }
  }
  
  loadProfile() {
    this.userService.getProfile().subscribe({
      next: (user) => {
        this.profile = {
          prenom: user.prenom || '',
          nom: user.nom || '',
          email: user.email || ''
        };
        // Also update the local storage to keep it in sync
        this.authService.updateCurrentUser(user);
      },
      error: (err) => {
        console.error('Failed to load profile', err);
      }
    });
  }
  
  loadPersonalStats() {
    // Load admin personal statistics
    this.userService.getAdminStats().subscribe({
      next: (stats) => {
        this.personalStats = {
          annoncesModerees: stats.annoncesModerees || 0,
          demandesTraitees: stats.demandesTraitees || 0,
          utilisateursGeres: stats.utilisateursGeres || 0,
          rapportsGeneres: stats.rapportsGeneres || 0
        };
      },
      error: (error) => {
        console.error('Error loading statistics:', error);
      }
    });
  }
  
  updateProfile() {
    this.isUpdating = true;
    this.userService.updateProfile(this.profile).subscribe({
      next: () => {
        this.isUpdating = false;
        // Update local data
        const currentUser = this.authService.getCurrentUser();
        if (currentUser) {
          const updatedUser: User = {
            ...currentUser,
            prenom: this.profile.prenom,
            nom: this.profile.nom,
            email: this.profile.email
          };
          this.authService.updateCurrentUser(updatedUser);
        }
        alert('Profile updated successfully');
      },
      error: (error) => {
        this.isUpdating = false;
        console.error('Error updating profile:', error);
        alert('Error updating profile');
      }
    });
  }
  
  changePassword() {
    if (this.passwordData.newPassword !== this.passwordData.confirmPassword) {
      alert('Passwords do not match');
      return;
    }
    
    this.isChangingPassword = true;
    this.userService.changePassword(this.passwordData.currentPassword, this.passwordData.newPassword).subscribe({
      next: () => {
        this.isChangingPassword = false;
        this.passwordData = {
          currentPassword: '',
          newPassword: '',
          confirmPassword: ''
        };
        alert('Password changed successfully');
      },
      error: (error) => {
        this.isChangingPassword = false;
        console.error('Error changing password:', error);
        alert('Error changing password');
      }
    });
  }
  
  toggleTwoFactor() {
    this.twoFactorEnabled = !this.twoFactorEnabled;
    // Implement 2FA activation/deactivation logic
    console.log('2FA toggled:', this.twoFactorEnabled);
  }
  
  toggleEmailNotifications() {
    this.emailNotifications = !this.emailNotifications;
    // Implement notification management logic
    console.log('Email notifications toggled:', this.emailNotifications);
  }
  
  viewActiveSessions() {
    // Navigate to active sessions page
    console.log('View active sessions');
  }
  
  uploadPhoto() {
    // Implement photo upload
    console.log('Upload photo');
  }
  
  exportData() {
    // Implement data export
    console.log('Export data');
  }
  
  downloadActivityLog() {
    // Implement activity log download
    console.log('Download activity log');
  }
  
  deactivateAccount() {
    if (confirm('Are you sure you want to deactivate your account? This action is irreversible.')) {
      // Implement account deactivation
      console.log('Deactivate account');
    }
  }
  
  getRoleLabel(): string {
    const user = this.authService.getCurrentUser();
    switch (user?.role) {
      case 'ADMINISTRATEUR':
        return 'Administrateur';
      case 'CONDUCTEUR':
        return 'conducteur';
      case 'EXPEDITEUR':
        return 'Expediteur';
      default:
        return 'User';
    }
  }
}
