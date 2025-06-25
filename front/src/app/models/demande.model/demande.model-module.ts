import { DemandeStatus } from "../enums/enums-module";

export interface Demande {
  id?: number;
  expediteurId: number;
  annonceId: number;
  dimensions: string;
  poids: number;
  type: string;
  statut?: DemandeStatus;
  dateDemande?: Date;
}
