import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../../service/authService/auth.service';
import { LoginData } from '../../../models/user.model';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './login.html',
  styleUrls: ['./login.css']
})
export class LoginComponent {
  email = '';
  password = '';
  showPassword = false;
  submitted = false;
  errorMessage = '';
  successMessage = '';
  isLoading = false;

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit() {
    this.submitted = true;
    this.errorMessage = '';
    this.successMessage = '';
    
    if (!this.email || !this.password) {
      this.errorMessage = 'Please fill all fields.';
      return;
    }

    this.isLoading = true;
    
    const loginData: LoginData = {
      email: this.email,
      motDePass: this.password
    };

    this.authService.login(loginData).subscribe({
      next: (response) => {
        this.isLoading = false;
        this.successMessage = 'Login successful!';
        
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
        }, 1000);
      },
      error: (error) => {
        this.isLoading = false;
        this.errorMessage = error.error?.message || 'Login error.';
      }
    });
  }
} 