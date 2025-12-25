# Projet de plateforme de gestion d'horaire scolaire à l'UDEM

## Structure du projet

```sh
rest-api/
│
├── src/
│   ├── main/
│   │   ├── resources/data
│   │   ├── java/com/diro/ift2255/
│   │   │   ├── config/
│   │   │   │   └── Routes.java           # Définition des routes HTTP
│   │   │   │
│   │   │   ├── controller/
│   │   │   │   ├── CourseController.java       # Contrôleur pour les endpoints de cours
│   │   │   │   └── UserController.java         # Contrôleur pour les endpoints utilisateurs
│   │   │   │   └── ComparaisonController.java  # Contrôleur pour les cours sélectionnés pour comparaison
│   │   │   │   └── EnsembleController.java     # Contrôleur pour l'outil qui permet de créer des ensembles de cours
│   │   │   │
│   │   │   ├── model/
│   │   │   │   ├── Course.java           # Modèle représentant un cours
│   │   │   │   └── User.java             # Modèle représentant un utilisateur
│   │   │   │
│   │   │   ├── service/
│   │   │   │   ├── CourseService.java    # Logique métier liée aux cours
│   │   │   │   └── UserService.java      # Logique métier liée aux utilisateurs
│   │   │   │
│   │   │   ├── util/
│   │   │   │   ├── HttpClientAPI.java    # Client HTTP pour appels externes
│   │   │   │   ├── HttpResponse.java     # Représentation d'une réponse HTTP
│   │   │   │   ├── HttpStatus.java       # Codes de statut HTTP
│   │   │   │   ├── ResponseUtil.java     # Outils pour formater les réponses
│   │   │   │   └── ValidationUtil.java   # Méthodes utilitaires de validation
│   │   │   │
│   │   │   ├── Cli.java             
│   │   │   │
│   │   │   └── Main.java                 # Point d’entrée du serveur Javalin
│   │   │
│   │   └── resources/                    # Ressources utilisées dans le code
│   │
│   └── test/                             # Tests unitaires (JUnit)
│   │    ├── java/com/diro/ift2255/
│   │    │   ├── controller/                             
│   │    │   │     ├── CourseControllerTest.java        # Contrôleur pour les tests de CourseController
│   │    │   │     ├── EnsembleControllerTest.java      # Contrôleur pour les tests de EnsembleController
│   │    │   │     ├── UserControllerTest.java          # Contrôleur pour les tests de UserController
│   │    │   │     └──  ComparaisonControllerTest.java   # Contrôleur pour les tests de ComparaisonControllerTest
│   │    │   │ 
│   │    │   ├── service/       
│   │    │   │     └──  CourseServiceTest.java   # Contrôleur pour les tests de CourseService
│   │    │   │     └──  UserServiceTest.java     # Contrôleur pour les tests de UserService
│   │    │   │ 
└── pom.xml
```

## Description du projet

L’application de gestion d’horaire scolaire vise à aider les étudiants à prendre des décisions éclairées dans le choix de leurs cours. Elle leur permet de consulter des informations complètes et structurées pour chaque cours, incluant la description, les prérequis et co-requis,ainsi que des avis d’étudiants ayant déjà suivi ces cours. Les étudiants ont également accès à des statistiques académiques agrégées, telles que la moyenne de classe, le nombre d’inscriptions et le taux d’échec.

Il y a aussi la possibilité d’affiner les résultats selon différents critères, notamment le sigle, le titre, la description ou le trimestre. L’outil permet également aux étudiants de vérifier leur éligibilité à un cours en fonction de leur cycle d’études et des exigences académiques.

Enfin, l’application propose une fonctionnalité de comparaison de plusieurs cours, permettant de visualiser clairement les différences entre les options possibles et d’évaluer la charge de travail globale d’une combinaison de cours, afin d’aider l’étudiant à faire le meilleur choix possible.

## Liste des fonctionnalités de l'application par rôle
**Étudiant**
- **Rechercher des cours** avec plusieurs critères :
  - Par **sigle** (ex: IFT2255)
  - Par **nom** (ex: génie logiciel)
  - Par **description** (mots-clés : ajax, java, algorithmes, etc.)
  - Par **trimestre** (H25, A24, E24)
- **Consulter les informations détaillées** d'un cours :
  - Description complète
  - Nombre de crédits
  - Prérequis et corequis
  - Sessions disponibles (Automne, Hiver, Été)
    
- **Visualiser les résultats agrégés** :
  - Moyenne de classe
  - Score moyen sur 5.0
  - Nombre de participants total
  - Données sur plusieurs trimestres

- **Consulter les avis** d'étudiants ayant suivi le cours :
  - Note moyenne des étudiants (sur 5 étoiles)
  - La charge de travail réelle
  - La qualité de l'enseignement
  - La difficulté du cours
    
- **Poster des avis sur des cours déjà suivis**
  
- **Vérifier automatiquement** si vous êtes éligible à un cours selon :
  - Votre cycle d'études (Baccalauréat, Maîtrise, Doctorat)
  - Le cycle requis pour le cours

- **Comparer plusieurs cours**
  - **Visualisation synthétique** incluant : Sigles et noms des cours, Nombre de crédits de chaque cours, Charge de travail totale (somme des crédits)
    
- **Afficher l'horaire**

**Bot Discord**
- **Collecter et soumettre des avis étudiants à l'API REST**

## Évaluation (tests)

**1. Récupération de tous les utilisateurs (UserController.getAllUsers())**

Test : testGetAllUsers
Description : Vérifie que la liste complète des utilisateurs est retournée en JSON lorsque le service contient des utilisateurs.


**2. Récupération d'un utilisateur par ID (UserController.getUserById())**

Tests :

testGetUserByIdSuccess - Utilisateur trouvé
testGetUserByIdNotFound - Utilisateur inexistant (404)
testGetUserInvalidId - ID invalide (400)


Description : Vérifie la récupération d'un utilisateur spécifique, la gestion d'erreur 404 si l'utilisateur n'existe pas, et la validation de l'ID (doit être numérique).


**3. Création d'un utilisateur (UserController.createUser())**

Test : testCreateUserSuccess
Description : Vérifie qu'un nouvel utilisateur est correctement créé et que le status HTTP 201 (Created) est retourné.


**4. Suppression d'un utilisateur (UserController.deleteUser())**

Tests :

testDeleteUserSuccess - Suppression réussie
testDeleteUserInvalidId - ID invalide (400)


Description : Vérifie la suppression d'un utilisateur existant avec status 204 (No Content), et la gestion d'erreur pour un ID non numérique.


**5. Création d'un ensemble de cours (EnsembleController.createEnsemble())**

Tests :

testCreateEnsembleAlreadyExists (1ère création) - Succès
testCreateEnsembleAlreadyExists (2ème création) - Déjà existant (400)


Description : Vérifie la création d'un nouvel ensemble avec status 201, et refuse la création d'un ensemble déjà existant avec status 400.


**6. Ajout d'un cours à un ensemble (EnsembleController.addCourse())**

Tests :

testAddCourseSuccess - Cours ajouté avec succès
testAddCourseToNonExistentEnsemble - Ensemble inexistant (404)
testAddCourseInvalidCourseId - ID cours invalide (400)


Description : Vérifie l'ajout d'un cours à un ensemble existant, la gestion d'erreur si l'ensemble n'existe pas, et la validation de l'ID du cours (minimum 6 caractères).


## Instructions d'installation

1. **Cloner le dépôt**
   ```bash
   git clone https://github.com/jujutheriault/IFT2255_Devoir2.git
   cd rest-api

2. **Vérifier l'installation de Maven et Java**
   ```bash
   java -version
   mvn -version
   
- Si ce n'est pas installé, installez-le selon les instruction d'installation

3. ***Installer les dépendances de maven***
    ```bash
    mvn clean install


## Instructions d'exécution et de test
1. **Pour compiler le projet:**
    ```bash
    mvn clean compile

2. **Les tests** 
- Ce projet inclut des test unitaires utilisant **JUnit 5** et **Mockito**.
- Ces test se trouvent dans les fichiers **controller/ComparaisonControllerTest.java**, **controller/CourseControllerTest.java**,
**service/CourseServiceTest.java**, **service/UserServiceTest.java** et **controller/WnsembleCoursControllerTest.java**. Ces fichiers se trouvent dans le dossier **test/java/com/diro/ift2255**

3. **Pour exécuter les tests**
    ```bash
    mvn clean test

4. **Pour exécuter et intéragir avec le projet**
    ```bash
    mvn clean compile exec:java -Dexec.mainClass="com.diro.ift2255.Main"

- Un CLI sera démarrer en même temps dans le terminal, il imprimera le résultat des endpoints dans ce terminal. 

5. **Par défaut, l'API est accessible à:**
    ```bash
    http://localhost:7070
    


## Endpoints principaux

1. **Consulter un cours par ID**
    ```bash
    GET /courses/{id}

2. **Consulter un cours incluant son horaire** 
    ```bash
    GET /courses/{id}?include_schedule=true&semester=a25

3. **Rechercher un cours par mot-clé**
    ```bash
    GET /courses/search/{mot-cle}

4. **Obtenir la liste d'un cours d'un programme** 
    ```bash
    GET /programs?programs_list=117510

5. **Obtenir la liste d'un cours d'un programme selon un trimestre**
    ```bash
    GET /programs/semester/{trimestre}?programs_list={programme}&include_schedule=true

