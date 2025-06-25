export interface RegisterData {
  nom: string;
  prenom: string;
  email: string;
  motDePass: string;
  role: 'CONDUCTEUR' | 'EXPEDITEUR' | 'ADMINISTRATEUR';
}

export interface LoginData {
  email: string;
  motDePass: string;
}

export interface User {
  id: number;
  nom: string;
  prenom: string;
  email: string;
  role: string;
  dateInscription: Date;
  status: string;
  verified: boolean;
  noteMoyenne?: number;
}

export interface AuthResponse {
  token: string;
  user: User;
}

export interface ProfileData {
  prenom: string;
  nom: string;
  email: string;
} 