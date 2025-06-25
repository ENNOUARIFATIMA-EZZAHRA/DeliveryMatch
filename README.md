# DeliveryMatch - Frontend Angular

## Description

DeliveryMatch est une plateforme web moderne développée avec Angular et Tailwind CSS qui permet de connecter les expéditeurs et les conducteurs pour le transport de colis au Maroc. L'application propose une interface utilisateur intuitive avec des fonctionnalités spécifiques selon le rôle de l'utilisateur.

## Fonctionnalités

### 👥 Utilisateurs non connectés
- Page d'accueil avec présentation des services
- Inscription et connexion
- Navigation simple

### 🚛 Conducteurs
- **Dashboard personnalisé** : Vue d'ensemble des annonces et demandes
- **Gestion des annonces** : Création, modification, suppression d'annonces de transport
- **Gestion des demandes** : Acceptation/refus des demandes de transport
- **Historique des trajets** : Suivi des trajets effectués
- **Statistiques personnelles** : Revenus, nombre de trajets, évaluations

### 📦 Expéditeurs
- **Recherche de trajets** : Filtrage par ville, date, prix
- **Création de demandes** : Demande de transport avec détails du colis
- **Suivi des demandes** : Statut en temps réel des demandes
- **Historique** : Consultation des demandes passées
- **Évaluations** : Notation des conducteurs

### 👨‍💼 Administrateurs
- **Dashboard administratif** : Statistiques globales et métriques
- **Gestion des utilisateurs** : CRUD complet, activation/désactivation, export
- **Modération des annonces** : Approbation, rejet, suppression
- **Gestion des demandes** : Supervision et résolution des conflits
- **Statistiques détaillées** : Graphiques, rapports, analyses
- **Profil administrateur** : Gestion des paramètres et sécurité

### 🛠️ Technologies utilisées
- **Angular 17** : Framework principal avec composants standalone
- **Tailwind CSS** : Framework CSS utilitaire pour le design
- **TypeScript** : Langage de programmation typé
- **RxJS** : Programmation réactive
- **Angular Router** : Navigation et routage
- **Angular Forms** : Gestion des formulaires
- **HTTP Client** : Communication avec l'API backend

### Prérequis
- Node.js (version 18 ou supérieure)
- npm ou yarn
- Angular CLI

### Installation
```bash
# Cloner le repository
git clone <repository-url>
cd front

# Installer les dépendances
npm install

# Démarrer le serveur de développement
npm start
```

### Configuration
1. **Variables d'environnement** :
   - `environment.ts` : Configuration de développement
   - `environment.prod.ts` : Configuration de production

2. **API Backend** :
   - URL par défaut : `http://localhost:8080/api`
   - Modifier dans `src/environments/environment.ts`

### Scripts disponibles
```bash
npm start          # Démarrer le serveur de développement
npm run build      # Build de production
npm run test       # Exécuter les tests
npm run lint       # Vérifier le code avec ESLint
```

## Fonctionnalités Détaillées

### 🔐 Authentification et Autorisation
- **JWT Token** : Gestion sécurisée des sessions
- **Guards** : Protection des routes selon les rôles
- **Intercepteurs** : Ajout automatique des tokens aux requêtes
- **Gestion des erreurs** : Redirection automatique en cas d'expiration

### 📊 Dashboard et Statistiques
- **Graphiques interactifs** : Évolution des utilisateurs, répartition des demandes
- **Métriques en temps réel** : Statistiques mises à jour automatiquement
- **Filtres avancés** : Recherche par période, statut, utilisateur
- **Export de données** : Génération de rapports PDF/Excel

### 🔍 Recherche et Filtrage
- **Recherche textuelle** : Par ville, nom, description
- **Filtres multiples** : Statut, date, prix, utilisateur
- **Pagination** : Navigation efficace dans les listes
- **Tri dynamique** : Par colonne et ordre

### 📱 Interface Responsive
- **Mobile-first** : Optimisation pour tous les écrans
- **Navigation adaptative** : Menu hamburger sur mobile
- **Composants flexibles** : Adaptation automatique du contenu
- **Touch-friendly** : Boutons et interactions optimisés

## Sécurité

### 🔒 Mesures de sécurité implémentées
- **Validation côté client** : Vérification des formulaires
- **Sanitisation des données** : Protection contre les injections
- **Gestion des sessions** : Expiration automatique des tokens
- **Autorisation granulaire** : Accès selon les rôles et permissions

### 🛡️ Bonnes pratiques
- **HTTPS** : Communication sécurisée en production
- **CORS** : Configuration appropriée pour l'API
- **Validation** : Vérification des données d'entrée
- **Logging** : Traçabilité des actions utilisateur



## Tests

### 🧪 Stratégie de tests
- **Tests unitaires** : Jasmine/Karma pour les composants
- **Tests d'intégration** : Validation des services
- **Tests E2E** : Cypress pour les scénarios utilisateur
- **Couverture de code** : Objectif > 80%

### 📋 Exécution des tests
```bash
npm run test              # Tests unitaires
npm run test:watch        # Tests en mode watch
npm run test:coverage     # Tests avec couverture
npm run e2e               # Tests end-to-end
```

##diagrammes##

**diagramme de class**



<img width="440" alt="image" src="https://github.com/user-attachments/assets/e6b66cc0-55d6-48a3-af2b-5dcdaa089b02" />


**diagramme de use case**



<img width="411" alt="image" src="https://github.com/user-attachments/assets/c824253f-6d85-41aa-8a60-388cb83a1cea" />


**diagramme de sequence login***




<img width="715" alt="image" src="https://github.com/user-attachments/assets/91dfe83f-6e51-451b-bbe7-0ef2183ff6ab" />



**diagramme de sequence register***




<img width="763" alt="image" src="https://github.com/user-attachments/assets/4d1c1168-0195-40e0-a85a-cae9630ab50d" />




**diagramme de sequence conducteur***





<img width="282" alt="image" src="https://github.com/user-attachments/assets/37d8e926-a7d6-4bb9-b7a8-db1f12ea9a03" />





**diagramme de sequence expediteur***




<img width="560" alt="image" src="https://github.com/user-attachments/assets/84800d34-4031-49d1-9d8d-606f90daeab5" />




**diagramme de sequence Administrateur***



<img width="461" alt="image" src="https://github.com/user-attachments/assets/bb204c84-e63c-4d24-afad-78ef8f501acd" />



