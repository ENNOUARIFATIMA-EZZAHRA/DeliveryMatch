import { User } from "./user.model";

export interface Annonce {
  id: number;
  lieuDepart: string;
  destination: string;
  dateDepart: string;
  capacite: number;
  typeVehicule: string;
  description: string;
  conducteur: User;
  status: string;
  etapes?: string;
  dimensionsMax?: string;
  typeMarchandise?: string;
  prix?: number;
  noteMoyenne?: number;
}
