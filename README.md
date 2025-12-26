# Projet de plateforme de gestion d'horaire scolaire à l'UDEM

Ce projet consiste en une API REST développée en Java avec Javalin, d'un outil CLI et d’un bot Discord, permettant aux étudiants de consulter des cours, vérifier leur éligibilité, gérer des ensembles de cours, consulter des horaires par programme et trimestre, et partager des avis sur les cours.

L’application s’appuie sur des services externes (Planifium) pour la récupération des données académiques, d'un CSV de résultats agrégés et du bot discord pour les avis étudiants. 

## Liste des fonctionnalités de l’application par rôle
1. **Étudiant**

-  Consulter un cours par son sigle

- Rechercher des cours par mot-clé

- Consulter l’horaire d’un cours pour un trimestre donné

- Vérifier son éligibilité à un cours selon les prérequis complétés

- Créer et gérer des ensembles de cours

- Consulter les cours offerts dans un programme et un trimestre

- Donner un avis sur un cours (via le bot Discord)

- Consulter les avis d'un cours

2. **Bot Discord**

- Analyse les messages contenant des avis de cours

- Valide le format des avis (sigle, trimestre, difficulté, charge de travail)

- Transmet automatiquement les avis à l’API REST

- Sauvegarde localement un flux des avis envoyés

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

Ce projet inclut des test unitaires utilisant **JUnit 5** et **Mockito**.
Ces test se trouvent dans les fichiers suivants: 
- **controller/ComparaisonControllerTest.java**
- **controller/CourseControllerTest.java**
- **service/CourseServiceTest.java**
- **service/UserServiceTest.java**
- **controller/WnsembleCoursControllerTest.java**. 
Ces fichiers se trouvent dans le dossier **test/java/com/diro/ift2255**

3. **Pour lancer les tests**
    ```bash
    mvn clean test

4. **Intégration continue (CI)**

Le projet utilise GitHub Actions pour l’intégration continue.
Les tests sont exécutés automatiquement à chaque push et pull request vers le dépôt GitHub.


## Instructions pour démarrer le bot discord
1.  **Création et activation de l’environnement virtuel**
    ```bash
    cd discord_bot
    python3 -m venv .venv
    source .venv/bin/activate

2.  **Installation des dépendances**
    ```bash
    pip install -r requirements.txt

3. **Configuration du fichier .env**
Créer un fichier .env dans le dossier discord_bot/
    ```bash
    DISCORD_TOKEN= (Token donné dans le channel discord de l'équipe 14)
    GUILD_ID=1449881612547260557
    OPINION_API_URL=http://localhost:7070/avis

4.  **Démmarage du Bot**
    ```bash
    python3 bot.py

## Démarrage de l'API REST
### Prérequis
- **Java 17** (ou plus récent)
- **Maven** (pour compiler et générer le JAR)

1. **Compiler le projet et générer le JAR exécutable**
    ```bash
    cd rest-api
    mvn clean package
    java -jar target/rest-api.jar

- Lors du démarrage de l’API, une interface CLI est lancée dans le terminal.
Elle permet de tester rapidement les endpoints REST sans utiliser Postman
ou un navigateur.

### Port du serveur
L’API REST est accessible par défaut à l’adresse :
http://localhost:7070


## Endpoints principaux

1. **Consulter un cours par ID**
    ```bash
    GET /courses/{id}

2. **Consulter un cours incluant son horaire** 
    ```bash
    GET /courses/{id}?include_schedule=true

3. **Rechercher un cours par mot-clé**
    ```bash
    GET /courses/search/{mot-cle}

4. **Obtenir la liste d'un cours d'un programme** 
    ```bash
    GET /programs?programs_list=117510

5. **Obtenir la liste d'un cours d'un programme selon un trimestre**
    ```bash
    GET /programs/semester/{trimestre}?programs_list={programme}&include_schedule=true
6. **Vérifier l’éligibilité à un cours (prérequis)**
    ```bash
    GET /courses/{id}/eligibility?completed=IFT1025,IFT1065
7. **Créer un ensemble**
    ```bash
    GET /ensemble/create/{idEnsemble}
8. **Consulter un ensemble**
    ```bash
    GET /ensemble/consult/{idEnsemble}
9. **Ajouter un cours à un ensemble**
    ```bash
    GET /ensemble/{idEnsemble}/add/{courseId}
10. **Supprimer un cours d’un ensemble**
    ```bash
    GET /ensemble/{idEnsemble}/delete/{courseId}
11. **Créer un avis**
    ```bash
    POST /avis
12. **Obtenir tous les avis d’un cours (par sigle)**
    ```bash
    GET /avis/{sigle}
13. **Obtenir un résumé des avis d’un cours**
    ```bash
    GET /avis/{sigle}/resume


