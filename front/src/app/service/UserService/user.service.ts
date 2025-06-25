import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { ProfileData } from '../../models/user.model';

@Injectable({ providedIn: 'root' })
export class UserService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  
  getProfile(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/profile`);
  }

  updateProfile(data: ProfileData): Observable<any> {
    return this.http.put(`${this.apiUrl}/users/update`, data);
  }

  
  getAdminStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/profile/stats`);
  }

 
  changePassword(currentPassword: string, newPassword: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/users/change-password`, {
      currentPassword,
      newPassword
    });
  }

 
  getAllUsers(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/users/all`);
  }

 
  updateUser(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/users/${id}`, data);
  }


  createUser(data: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/users`, data);
  }

 
  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/users/${id}`);
  }

  toggleUserStatus(id: number, isActive: boolean): Observable<any> {
    return this.http.patch(`${this.apiUrl}/users/${id}/status`, { isActive });
  }


  exportUsers(format: string): Observable<Blob> {
    return this.http.get(`${this.apiUrl}/users/export?format=${format}`, { responseType: 'blob' });
  }

 
  getUserStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/users/stats`);
  }
} 