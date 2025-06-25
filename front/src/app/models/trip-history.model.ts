export interface TripHistory {
  id: number;
  villeDepart: string;
  villeArrivee: string;
  dateDepart: Date;
  nbDemandes: number;
  etat: 'COMPLETED' | 'PENDING' | 'CANCELED';
  evaluation?: number;
}
