import { Routes } from '@angular/router';
import { LoginComponent } from './pages/auth/login/login';
import { RegisterComponent } from './pages/auth/register/register';
import { HomeComponent } from './pages/home/home';
import { AdminDashboardComponent } from './pages/admin/dashboard/dashboard';
import { AuthGuard } from './service/authService/authGuard';

// Conducteur routes
import { AnnonceListComponent } from './pages/conducteur/annonces/annonce-list/annonce-list';
import { AnnonceCreateComponent } from './pages/conducteur/annonces/annonce-create/annonce-create';
import { AnnonceDetails } from './pages/conducteur/annonces/annonce-details/annonce-details';
import { AnnonceEditComponent } from './pages/conducteur/annonces/annonce-edit/annonce-edit';

// Expediteur routes
import { AnnoncesListComponent } from './pages/expediteur/annonces-list/annonces-list';
import { DemandeCreateComponent } from './pages/expediteur/demande-create/demande-create';
import { DemandeHistory } from './pages/expediteur/demande-history/demande-history';

// Conducteur Demandes
import { DemandeList } from './pages/conducteur/demandes/demande-list/demande-list';
import { DemandeDetail } from './pages/conducteur/demandes/demande-detail/demande-detail';

// Admin routes
import { UsersComponent } from './pages/admin/users-management/users';
import { AnnoncesManagementComponent } from './pages/admin/annonces-management/annonces-management';
import { DemandesManagementComponent } from './pages/admin/demandes-management/demandes-management';
import { StatisticsComponent } from './pages/admin/statistics/statistics';
import { AdminProfileComponent } from './pages/user-profile/profile';

// History routes
import { TripHistoryComponent } from './pages/conducteur/history/trip-history/trip-history';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  
  // Dashboard principal
  { path: 'dashboard', component: AdminDashboardComponent, canActivate: [AuthGuard] },
  
  // Routes Conducteur
  { path: 'conducteur/annonces', component: AnnonceListComponent, canActivate: [AuthGuard] },
  { path: 'conducteur/annonce/create', component: AnnonceCreateComponent, canActivate: [AuthGuard] },
  { path: 'conducteur/annonce/:id', component: AnnonceDetails, canActivate: [AuthGuard] },
  { path: 'conducteur/annonce/edit/:id', component: AnnonceEditComponent, canActivate: [AuthGuard] },
  
  // Routes Expediteur
  { path: 'expediteur/annonces', component: AnnoncesListComponent },
  { path: 'expediteur/demande/create/:id', component: DemandeCreateComponent, canActivate: [AuthGuard] },
  { path: 'expediteur/history', component: DemandeHistory, canActivate: [AuthGuard] },
  
  // Routes Demandes Conducteur
  { path: 'demandes', component: DemandeList, canActivate: [AuthGuard] },
  { path: 'demande/:id', component: DemandeDetail, canActivate: [AuthGuard] },
  
  // Routes Admin
  { path: 'admin', component: AdminDashboardComponent, canActivate: [AuthGuard] },
  { path: 'admin/users', component: UsersComponent, canActivate: [AuthGuard] },
  { path: 'admin/annonces', component: AnnoncesManagementComponent, canActivate: [AuthGuard] },
  { path: 'admin/demandes', component: DemandesManagementComponent, canActivate: [AuthGuard] },
  { path: 'admin/statistics', component: StatisticsComponent, canActivate: [AuthGuard] },
  { path: 'admin/profile', component: AdminProfileComponent, canActivate: [AuthGuard] },
  { path: 'profile', component: AdminProfileComponent, canActivate: [AuthGuard] },
  
  // Routes History
  { path: 'history', component: TripHistoryComponent, canActivate: [AuthGuard] },
  
  // Route par défaut
  { path: '**', redirectTo: '' }
];
