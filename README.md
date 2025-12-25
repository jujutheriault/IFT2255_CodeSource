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

