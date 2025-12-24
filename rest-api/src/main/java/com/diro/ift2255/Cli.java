package com.diro.ift2255;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.Scanner;

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
            System.out.println("0. Quitter");
            System.out.print("> ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cliSearch(scanner);
                    break;
                case "2":
                    cliEnsemble(scanner);
                    break;
                case "3":
                    cliProgram(scanner);
                    break;
                case "4":
                    cliTrimestre(scanner);
                    break;
                case "5":
                    cliCourseById(scanner);
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
        System.out.print("Entrez le numéro du programme : ");
        String program = scanner.nextLine().trim();

        System.out.print("Entrez le trimestre à consulter (ex: a25): ");
        String semester = scanner.nextLine().trim().toLowerCase();

        // Nouvelle route locale
        String url = baseHost + "/programs/semester/" + semester + "?programs_list=" + program + "&include_schedule=true";

        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }


    // Imprime le URL correspondant à la manipulation effectuée
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

        String url = baseHost + "/courses/" + courseId;

        System.out.println("\n[Action] Consultation du cours...");
        System.out.println("URL cible : " + url);

        httpGetAndPrint(url);
    }


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