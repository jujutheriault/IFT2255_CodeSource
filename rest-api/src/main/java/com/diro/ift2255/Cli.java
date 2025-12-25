package com.diro.ift2255;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

import java.util.Set;
import java.util.HashSet;


public class Cli {

    private final String baseHost;

    private final HttpClient httpClient = HttpClient.newBuilder()
        .connectTimeout(Duration.ofSeconds(5))
        .build();
    /**
     * Constructeur du CLI
     * @param baseHost
     */
    public Cli(String baseHost) {
        this.baseHost = baseHost;
    }
    /**
     * fonction qui demarre le CLI
     */
    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n=== SYSTÈME DE GESTION DES COURS (CLI) ===");

        while (running) {
            System.out.println("\nVeuillez indiquer la fonctionnalité à effectuer:");
            System.out.println("1. Rechercher un cours (par mot-clé)");
            System.out.println("2. Manipuler un ensemble de cours");
            System.out.println("3. Consulter la liste de cours d'un programme");
            System.out.println("4. Consulter la liste de cours d'un trimestre");
            System.out.println("5. Consulter un cours par ID");
            System.out.println("6. Vérifier l'éligibilité à un cours");
            System.out.println("0. Quitter");
            System.out.print("> ");

            String choice = scanner.nextLine();

            switch (choice) {
                // Recherche de cours
                case "1":
                    cliSearch(scanner);
                    break;
                // Créer et manipuler un ensemble
                case "2":
                    cliEnsemble(scanner);
                    break;
                case "3":
                // Consulter un programme (et ses cours)
                    cliProgram(scanner);
                    break;
                // Consulter les cours d'un programme et trimestre donné
                case "4":
                    cliTrimestre(scanner);
                    break;
                case "5":
                // Consulter tous les détails d'un cours
                    cliCourseById(scanner);
                    break;
                case "6":
                // Vérifier son éligibilité à un cours
                    cliEligibility(scanner);
                    break;
                case "0":
                    System.out.println("Fermeture du CLI...");
                    running = false;
                    break;
                default:
                    System.out.println("Option invalide, réessayez.");
            }
        }
    }

    // Imprime le URL correspondant à la recherche de cours
    /**
     * Recherche de cours par mots cles
     * @param scanner un scanner qui lit les entrees
     */
    private void cliSearch(Scanner scanner) {
        System.out.print("Entrez le mot-clé de recherche : ");
        String input = scanner.nextLine().trim();

        String url = baseHost + "/courses/search/" + input;
        
        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }

    /**
     * Recherche de la liste de cours par programme
     * @param scanner un scanner qui lit les entrees
     */
    private void cliProgram(Scanner scanner) {
        System.out.print("Entrez le numéro du programme : ");
        String program = scanner.nextLine();
        String url = baseHost + "/programs?programs_list=" + program;
        
        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }

    /**
     * Recherche de la liste de cours par trimestre
     * @param scanner un scanner qui lit les entrees
     */
    private void cliTrimestre(Scanner scanner) {
        System.out.print("Entrez le numéro du programme (ex: 117510) : ");
        String program = scanner.nextLine().trim();

        System.out.print("Entrez le trimestre à consulter (ex: a25): ");
        String semester = scanner.nextLine().trim().toLowerCase();

        // Nouvelle route locale
        String url = baseHost + "/programs/semester/" + semester + "?programs_list=" + program + "&include_schedule=true";

        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }


    /**
     * Gère les interactions du CLI liées aux ensembles de cours.
     * En fonction du choix de l'utilisateur, l'URL correspondante est construite
     * puis une requête HTTP GET est envoyée à l'API REST afin d'effectuer l'action.
     *
     * @param scanner Scanner utilisé pour lire les entrées de l'utilisateur
     */
    private void cliEnsemble(Scanner scanner) {
        System.out.println("\n--- GESTION DES ENSEMBLES ---");
        System.out.println("1. Créer un ensemble de cours");
        System.out.println("2. Ajouter un cours à un ensemble");
        System.out.println("3. Supprimer un cours d'un ensemble");
        System.out.println("4. Consulter un ensemble de cours");
        System.out.print("> ");
        
        String choice = scanner.nextLine();
        String url = "";
        String ensembleId;
        String courseId;

        switch (choice) {
            // Création d'un ensemble
            case "1":  
                System.out.print("ID pour le nouvel ensemble : ");
                ensembleId = scanner.nextLine();
                url = baseHost + "/ensemble/create/" + ensembleId ; 
                System.out.println("\n[Action] Création de l'ensemble...");
                break;
            // Ajout d'un cours à un ensemble
            case "2":  
                System.out.print("ID de l'ensemble : ");
                ensembleId = scanner.nextLine();
                System.out.print("ID du cours à ajouter : ");
                courseId = scanner.nextLine();
                url = baseHost + "/ensemble/" + ensembleId + "/add/" + courseId;
                System.out.println("\n[Action] Ajout du cours...");
                break;
            // Supression d'un cours d'un ensemble
            case "3":
                System.out.print("ID de l'ensemble : ");
                ensembleId = scanner.nextLine();
                System.out.print("ID du cours à supprimer : ");
                courseId = scanner.nextLine();
                url = baseHost + "/ensemble/" + ensembleId + "/delete/" + courseId;
                System.out.println("\n[Action] Suppression du cours...");
                break;
            // Consulter un ensemble
            case "4":
                System.out.print("ID de l'ensemble à consulter : ");
                ensembleId = scanner.nextLine();
                url = baseHost + "/ensemble/consult/" + ensembleId;
                System.out.println("\n[Action] Récupération des données...");
                break;

            default:
                System.out.println("Option invalide.");
                return;
        }

        // URL à copier coller dans un browser
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);

    }

    /**
 * Consultation d'un cours par son ID
 * @param scanner scanner pour lire l'entrée utilisateur
 */
    private void cliCourseById(Scanner scanner) {
        System.out.print("Entrez l'ID du cours (ex: IFT2255) : ");
        String courseId = scanner.nextLine().trim().toUpperCase();

        if (courseId.isEmpty()) {
            System.out.println("ID invalide.");
            return;
        }

        System.out.print("Inclure l'horaire? (y/n) : ");
        String answer = scanner.nextLine().trim().toLowerCase();
        // Inclure plusieurs cas de reponses
        boolean includeSchedule = answer.equals("y") || answer.equals("oui") || answer.equals("o") || answer.equals("yes");

        String url = baseHost + "/courses/" + courseId;
        if (includeSchedule) {
            url += "?include_schedule=true";
        }

        System.out.println("\n[Action] Consultation du cours...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }


    /**
     * Vérifie l'éligibilité d'un étudiant à un cours donné à partir du CLI.
     * Les cours complétés sont fournis sous forme d'une liste séparée par des
     * virgules ou des espaces, puis transmis à l'API REST via un paramètre
     * de requête. L'API retourne si l'étudiant est éligible au cours ainsi que
     * la liste des prérequis manquants si l'étudiant n'est pas éligible.
     *
     * @param scanner Scanner utilisé pour lire les entrées de l'utilisateur
     */
    private void cliEligibility(Scanner scanner) {
        System.out.print("Entrez le sigle du cours à vérifier (ex: IFT2255) : ");
        String courseId = scanner.nextLine().trim().toUpperCase();

        if (courseId.isEmpty()) {
            System.out.println("Sigle invalide.");
            return;
        }

        System.out.println("Entrez la liste des cours déjà complétés (séparés par des virgules ou espaces).");
        System.out.println("Ex: IFT1025, IFT1065, MAT1978");
        System.out.print("> ");
        String completedInput = scanner.nextLine().trim().toUpperCase();

        Set<String> completed = parseCourseList(completedInput);

        String completedParam = String.join(",", completed);
        String url = baseHost + "/courses/" + courseId + "/eligibility?completed=" + completedParam;

        System.out.println("\n[Action] Vérification d'éligibilité...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }

    /**
     * Analyse une chaîne de caractères représentant une liste de sigles de cours
     * et retourne un ensemble de cours normalisés.
     * Les doublons sont automatiquement éliminés grâce à l'utilisation d'un {@link Set}.
     *
     * @param input Chaîne contenant la liste des cours complétés par l'utilisateur
     * @return Un ensemble ({@link Set}) de sigles de cours normalisés
     */
    private Set<String> parseCourseList(String input) {
        Set<String> out = new HashSet<>();
        if (input == null || input.isBlank()) return out;

        // split par virgule OU espaces
        String[] parts = input.split("[,\\s]+");
        for (String p : parts) {
            String s = p.trim().toUpperCase();
            if (!s.isEmpty()) out.add(s);
        }
        return out;
    }

    /**
     * Envoie une requête HTTP GET vers l'URL fournie et affiche la réponse de l'API
     * dans la sortie standard.
     *
     * @param url URL complète vers laquelle la requête HTTP GET doit être envoyée
     */
    private void httpGetAndPrint(String url) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .timeout(Duration.ofSeconds(10))
                    .header("Accept", "application/json")
                    .GET()
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("\n--- RÉPONSE API ---");
            System.out.println("Status: " + response.statusCode());
            System.out.println(response.body());

        } catch (IllegalArgumentException e) {
            System.out.println("[Erreur] URL invalide: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("[Erreur] Impossible de contacter l’API. "
                    + "Assure-toi que le serveur est démarré sur " + baseHost);
            System.out.println("Détails: " + e.getMessage());
        }
    }
}