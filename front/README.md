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

## Architecture Technique

### 🏗️ Structure du projet
```
front/src/app/
├── pages/                    # Composants de pages
│   ├── auth/                # Authentification
│   ├── home/                # Page d'accueil
│   ├── dashboard/           # Dashboard principal
│   ├── conducteur/          # Pages conducteur
│   ├── expediteur/          # Pages expéditeur
│   ├── admin/               # Pages administrateur
│   ├── demandes/            # Gestion des demandes
│   └── history/             # Historique
├── service/                 # Services Angular
│   ├── authService/         # Authentification et autorisation
│   ├── annonce/             # Gestion des annonces
│   ├── demande/             # Gestion des demandes
│   ├── UserService/         # Gestion des utilisateurs
│   └── admin/               # Services administrateur
├── shared/                  # Composants partagés
│   └── navbar/              # Barre de navigation
└── models/                  # Modèles de données
```

### 🛠️ Technologies utilisées
- **Angular 17** : Framework principal avec composants standalone
- **Tailwind CSS** : Framework CSS utilitaire pour le design
- **TypeScript** : Langage de programmation typé
- **RxJS** : Programmation réactive
- **Angular Router** : Navigation et routage
- **Angular Forms** : Gestion des formulaires
- **HTTP Client** : Communication avec l'API backend

### 🎨 Design System
- **Couleurs** : Palette indigo/purple pour l'identité visuelle
- **Composants** : Design moderne avec ombres et arrondis
- **Responsive** : Adaptation mobile-first
- **Accessibilité** : Respect des standards WCAG

## Installation et Configuration

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

## Performance

### ⚡ Optimisations
- **Lazy Loading** : Chargement à la demande des modules
- **Compression** : Réduction de la taille des bundles
- **Cache** : Mise en cache des requêtes API
- **Optimisation des images** : Compression et formats modernes

### 📈 Métriques
- **First Contentful Paint** : < 1.5s
- **Largest Contentful Paint** : < 2.5s
- **Cumulative Layout Shift** : < 0.1
- **First Input Delay** : < 100ms

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

## Déploiement

### 🚀 Environnements
- **Développement** : `http://localhost:4200`
- **Staging** : Environnement de test
- **Production** : Serveur de production

### 📦 Build de production
```bash
npm run build:prod        # Build optimisé
npm run build:analyze     # Analyse du bundle
```

### 🔧 Configuration serveur
- **Nginx** : Serveur web recommandé
- **Compression gzip** : Réduction de la bande passante
- **Cache headers** : Optimisation des performances
- **HTTPS** : Certificat SSL obligatoire

## Maintenance

### 🔄 Mises à jour
- **Angular** : Mise à jour régulière du framework
- **Dépendances** : Audit de sécurité mensuel
- **Compatibilité** : Support des navigateurs modernes

### 📝 Documentation
- **Code** : Commentaires JSDoc
- **API** : Documentation des services
- **Changelog** : Historique des modifications

## Support

### 📞 Contact
- **Email** : support@deliverymatch.ma
- **Documentation** : Wiki interne
- **Issues** : GitHub Issues

### 🐛 Reporting de bugs
1. Vérifier les issues existantes
2. Créer une nouvelle issue avec :
   - Description détaillée
   - Étapes de reproduction
   - Environnement (OS, navigateur, version)
   - Captures d'écran si nécessaire

## Licence

Ce projet est sous licence MIT. Voir le fichier `LICENSE` pour plus de détails.

---

**DeliveryMatch** - Connecter le Maroc, un trajet à la fois 🚛📦
