# 📦 DeliveryMatch – Plateforme de Co-Transport Collaboratif

DeliveryMatch est une application web innovante qui met en relation des **conducteurs** effectuant des trajets avec des **expéditeurs** souhaitant faire livrer des colis. L’objectif est d’**optimiser les trajets**, **réduire les coûts de transport** et **minimiser l’impact environnemental** grâce à une solution collaborative.

---

## 🚀 Fonctionnalités Principales

### 👤 Utilisateur

- Créer un compte (nom, prénom, email, mot de passe).
- Se connecter en toute sécurité.
- Modifier ses informations personnelles.
- Se déconnecter de la plateforme.

---

### 🚚 Conducteur

- Publier une annonce de trajet avec :
  - Lieu de départ, étapes, destination finale.
  - Dimensions maximales acceptées, type de marchandise, capacité.
- Voir les demandes de transport reçues.
- Accepter ou refuser une demande selon ses critères.
- Consulter l’historique de ses trajets et colis transportés.

---

### 📦 Expéditeur

- Rechercher des annonces de trajets (filtrage par destination, date, type de colis).
- Envoyer une demande de transport avec les détails du colis (dimensions, poids, type).
- Consulter l’historique de ses demandes et envois.

---

### 🛠️ Administrateur

- Accéder à un dashboard de gestion complet :
  - Gérer les utilisateurs (valider, suspendre, badge “Vérifié”).
  - Gérer les annonces (consulter, modifier, supprimer).
  - Visualiser des statistiques via **Chart.js** :
    - Nombre d’annonces, taux d’acceptation, utilisateurs actifs, etc.

---

## 🧰 Technologies Utilisées

| Couche       | Technologie                         |
|--------------|-------------------------------------|
| Backend      | Spring Boot, Spring Data JPA, Spring Security |
| Frontend     | Angular 16+, Bootstrap / Tailwind, Angular Material, Chart.js |
| Base de données | PostgreSQL / MySQL               |
| Tests        | JUnit                               |
| Conteneurisation | Docker                          |
| Documentation API | Swagger / Postman              |

---

## 📂 Architecture

Le projet est structuré selon une architecture MVC claire avec séparation des responsabilités (Controllers, Services, Entities, Repositories).

---

**Diagramme de Class**

<img width="550" alt="image" src="https://github.com/user-attachments/assets/cd4d554c-7a77-45c9-89df-94d299f7ce68" />



**Diagramme de sequence de login**

<img width="741" alt="image" src="https://github.com/user-attachments/assets/3e64e5e9-1a25-4e6a-83bc-166fecb3253f" />


**Diagramme de sequence de register**


![image](https://github.com/user-attachments/assets/16ac94d8-ef43-42b2-95a3-26501001ea04)


**Diagramme de Use case**


<img width="368" alt="image" src="https://github.com/user-attachments/assets/1f247bf1-cf36-4047-ac0f-ebd9a31cf2f5" />


**Diagramme de sequence de condicteur**


<img width="335" alt="image" src="https://github.com/user-attachments/assets/8d3b9f0e-2d63-482a-a94f-624f10894267" />



**Diagramme de sequence de Administrateur**


<img width="518" alt="image" src="https://github.com/user-attachments/assets/804fccf5-c359-461c-876b-bddcd2521344" />


**Diagramme de sequence de expiditeur**


<img width="553" alt="image" src="https://github.com/user-attachments/assets/94d9273e-a796-4be9-9a61-4063cb6e168b" />


