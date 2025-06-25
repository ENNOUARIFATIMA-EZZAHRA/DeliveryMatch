import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AdminService {
  private apiUrl = environment.apiUrl;

  constructor(private http: HttpClient) {}

  // Statistiques générales
  getDashboardStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/dashboard/stats`);
  }

  // Gestion des utilisateurs
  getAllUsers(page: number = 0, size: number = 10, filters?: any): Observable<any> {
    let params: any = { page, size };
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key]) {
          params[key] = filters[key];
        }
      });
    }
    return this.http.get(`${this.apiUrl}/admin/users`, { params });
  }

  getUserById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/users/${id}`);
  }

  createUser(userData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/users`, userData);
  }

  updateUser(id: number, userData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/admin/users/${id}`, userData);
  }

  deleteUser(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/admin/users/${id}`);
  }

  activateUser(id: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/users/${id}/activate`, {});
  }

  deactivateUser(id: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/users/${id}/deactivate`, {});
  }

  exportUsers(format: string = 'csv'): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/users/export`, {
      params: { format },
      responseType: 'blob'
    });
  }

  // Gestion des annonces
  getAllAnnonces(page: number = 0, size: number = 10, filters?: any): Observable<any> {
    let params: any = { page, size };
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key]) {
          params[key] = filters[key];
        }
      });
    }
    return this.http.get(`${this.apiUrl}/admin/annonces`, { params });
  }

  getAnnonceById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/annonces/${id}`);
  }

  approveAnnonce(id: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/annonces/${id}/approve`, {});
  }

  rejectAnnonce(id: number, reason?: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/annonces/${id}/reject`, { reason });
  }

  deleteAnnonce(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/admin/annonces/${id}`);
  }

  getAnnonceStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/annonces/stats`);
  }

  // Gestion des demandes
  getAllDemandes(page: number = 0, size: number = 10, filters?: any): Observable<any> {
    let params: any = { page, size };
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key]) {
          params[key] = filters[key];
        }
      });
    }
    return this.http.get(`${this.apiUrl}/admin/demandes`, { params });
  }

  getDemandeById(id: number): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/demandes/${id}`);
  }

  approveDemande(id: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/demandes/${id}/approve`, {});
  }

  rejectDemande(id: number, reason?: string): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/demandes/${id}/reject`, { reason });
  }

  deleteDemande(id: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/admin/demandes/${id}`);
  }

  getDemandeStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/demandes/stats`);
  }

  // Statistiques détaillées
  getDetailedStats(period: string = '30'): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/statistics`, {
      params: { period }
    });
  }

  getTopVilles(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/statistics/top-villes`);
  }

  getUserEvolution(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/statistics/user-evolution`);
  }

  getPerformanceMetrics(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/statistics/performance`);
  }

  // Rapports et exports
  generateReport(type: string, filters?: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/reports/generate`, {
      type,
      filters
    });
  }

  exportReport(reportId: string, format: string = 'pdf'): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/reports/${reportId}/export`, {
      params: { format },
      responseType: 'blob'
    });
  }

  // Modération
  getReportedContent(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/moderation/reported`);
  }

  reviewReport(reportId: number, action: string, reason?: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/moderation/reports/${reportId}/review`, {
      action,
      reason
    });
  }

  // Paramètres système
  getSystemSettings(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/settings`);
  }

  updateSystemSettings(settings: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/admin/settings`, settings);
  }

  // Logs et audit
  getSystemLogs(page: number = 0, size: number = 50, filters?: any): Observable<any> {
    let params: any = { page, size };
    if (filters) {
      Object.keys(filters).forEach(key => {
        if (filters[key]) {
          params[key] = filters[key];
        }
      });
    }
    return this.http.get(`${this.apiUrl}/admin/logs`, { params });
  }

  getAuditTrail(userId?: number, action?: string, startDate?: string, endDate?: string): Observable<any> {
    let params: any = {};
    if (userId) params.userId = userId;
    if (action) params.action = action;
    if (startDate) params.startDate = startDate;
    if (endDate) params.endDate = endDate;
    
    return this.http.get(`${this.apiUrl}/admin/audit`, { params });
  }

  // Notifications et alertes
  getSystemNotifications(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/notifications`);
  }

  markNotificationAsRead(notificationId: number): Observable<any> {
    return this.http.patch(`${this.apiUrl}/admin/notifications/${notificationId}/read`, {});
  }

  sendSystemNotification(notification: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/notifications/send`, notification);
  }

  // Sauvegarde et maintenance
  createBackup(): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/backup/create`, {});
  }

  getBackupStatus(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/backup/status`);
  }

  restoreBackup(backupId: string): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/backup/${backupId}/restore`, {});
  }

  // Monitoring système
  getSystemHealth(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/system/health`);
  }

  getDatabaseStats(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/system/database-stats`);
  }

  getServerMetrics(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/system/server-metrics`);
  }

  // Gestion des rôles et permissions
  getAllRoles(): Observable<any> {
    return this.http.get(`${this.apiUrl}/admin/roles`);
  }

  createRole(roleData: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/roles`, roleData);
  }

  updateRole(roleId: number, roleData: any): Observable<any> {
    return this.http.put(`${this.apiUrl}/admin/roles/${roleId}`, roleData);
  }

  deleteRole(roleId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/admin/roles/${roleId}`);
  }

  assignRoleToUser(userId: number, roleId: number): Observable<any> {
    return this.http.post(`${this.apiUrl}/admin/users/${userId}/roles`, { roleId });
  }

  removeRoleFromUser(userId: number, roleId: number): Observable<any> {
    return this.http.delete(`${this.apiUrl}/admin/users/${userId}/roles/${roleId}`);
  }
} 