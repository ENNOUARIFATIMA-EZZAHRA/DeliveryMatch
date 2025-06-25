import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Annonce } from '../../models/annonce.model';

@Injectable({ providedIn: 'root' })
export class AnnonceService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Get all announcements
  getAllAnnonces(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/annonces`);
  }

  // Get announcements for the current driver
  getAnnoncesByConducteur(conducteurId: number): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/conducteur/annonces/${conducteurId}`);
  }

  // Get announcements for expediteurs (all available announcements)
  getAnnoncesForExpediteur(): Observable<Annonce[]> {
    return this.http.get<Annonce[]>(`${this.apiUrl}/annonces/expediteur`);
  }

  // Get a single announcement
  getAnnonceById(id: number): Observable<Annonce> {
    return this.http.get<Annonce>(`${this.apiUrl}/annonces/${id}`);
  }

 
  createAnnonce(data: Partial<Annonce>): Observable<Annonce> {
    return this.http.post<Annonce>(`${this.apiUrl}/annonces`, data);
  }


  updateAnnonce(id: number, data: Partial<Annonce>): Observable<Annonce> {
    return this.http.put<Annonce>(`${this.apiUrl}/annonces/${id}`, data);
  }

 
  deleteAnnonce(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/annonces/${id}`);
  }

 
  approveAnnonce(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/annonces/${id}/approve`, {});
  }

  
  rejectAnnonce(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/annonces/${id}/reject`, {});
  }

  getAnnonceStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/annonces/stats`);
  }


  getTripHistoryByConducteurId(id: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/conducteur/${id}/history`);
  }
} 