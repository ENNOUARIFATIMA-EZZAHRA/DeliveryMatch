import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { RegisterData, LoginData, AuthResponse, User } from '../../models/user.model';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Login
  login(credentials: LoginData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/users/login`, credentials);
  }

  // Register new user
  register(data: RegisterData): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(`${this.apiUrl}/users/register`, data);
  }

  // Check if user is authenticated
  isAuthenticated(): boolean {
    return !!localStorage.getItem('jwt_token');
  }

  // Get current user from localStorage
  getCurrentUser(): User | null {
    const user = localStorage.getItem('currentUser');
    return user ? JSON.parse(user) : null;
  }

  // Update current user data in localStorage
  updateCurrentUser(data: User): void {
    localStorage.setItem('currentUser', JSON.stringify(data));
  }

  // Get current user role from localStorage
  getUserRole(): string | null {
    const user = this.getCurrentUser();
    return user?.role || null;
  }

  // Logout
  logout(): void {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('currentUser');
  }
} 