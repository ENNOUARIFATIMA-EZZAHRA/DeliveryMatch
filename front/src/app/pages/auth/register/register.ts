import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/authService/auth.service';
import { RegisterData } from '../../../models/user.model';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.html',
  styleUrls: ['./register.css']
})
export class RegisterComponent {
  nom = '';
  prenom = '';
  email = '';
  motDePass = '';
  role: 'CONDUCTEUR' | 'EXPEDITEUR' | 'ADMINISTRATEUR' = 'CONDUCTEUR';
  errorMessage = '';
  successMessage = '';
  submitted = false;
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.submitted = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    if (!this.nom || !this.prenom || !this.email || !this.motDePass || !this.role) {
      this.errorMessage = 'Please fill all required fields.';
      return;
    }

    this.isLoading = true;
    
    const registerData: RegisterData = {
      nom: this.nom,
      prenom: this.prenom,
      email: this.email,
      motDePass: this.motDePass,
      role: this.role
    };

    this.authService.register(registerData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = 'Account created successfully!';
        
        // Store token and user data
        localStorage.setItem('jwt_token', response.token);
        this.authService.updateCurrentUser(response.user);
        
        // Redirect based on role
        setTimeout(() => {
          switch (response.user.role) {
            case 'CONDUCTEUR':
              this.router.navigate(['/conducteur/annonces']);
              break;
            case 'EXPEDITEUR':
              this.router.navigate(['/expediteur/annonces']);
              break;
            case 'ADMINISTRATEUR':
              this.router.navigate(['/admin/dashboard']);
              break;
            default:
              this.router.navigate(['/']);
          }
        }, 2000);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = error.error || 'Error creating account.';
      }
    });
  }
}
