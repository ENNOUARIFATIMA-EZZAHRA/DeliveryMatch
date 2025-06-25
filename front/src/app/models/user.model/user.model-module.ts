import { UserRole, UserStatus } from "../enums/enums-module";

export interface User {
  id?: number;
  nom: string;
  prenom: string;
  email: string;
  motDePass?: string;
  dateInscription?: string;
  role: UserRole;
  status?: UserStatus;
  verified?: boolean;
  telephone?: string;
  adresse?: string;
  statut?: string;
  isActive?: boolean;
}
