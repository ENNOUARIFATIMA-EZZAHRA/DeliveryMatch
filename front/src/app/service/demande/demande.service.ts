import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { CreateDemandeRequest } from '../../pages/expediteur/demande-create/demande-create';

@Injectable({ providedIn: 'root' })
export class DemandeService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

 
  getAllDemandes(): Observable<any> {
    return this.http.get(`${this.apiUrl}/demandes`);
  }

 
  getDemandeById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/demandes/${id}`);
  }

 
  createDemande(data: CreateDemandeRequest): Observable<any> {
    return this.http.post(`${this.apiUrl}/demandes`, data);
  }


  updateDemande(id: number, data: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/demandes/${id}`, data);
  }

 
  deleteDemande(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/demandes/${id}`);
  }


  approveDemande(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/demandes/${id}/approve`, {});
  }

  
  rejectDemande(id: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/demandes/${id}/reject`, {});
  }

 
  getDemandeStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/demandes/stats`);
  }
} 