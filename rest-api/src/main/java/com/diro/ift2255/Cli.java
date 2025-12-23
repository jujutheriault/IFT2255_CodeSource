package com.diro.ift2255;

import java.util.Scanner;

public class Cli {

    private final String baseHost;
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
            System.out.println("\nQue voulez-vous faire :");
            System.out.println("1. Rechercher un cours (par mot-clé)");
            System.out.println("2. Manipuler un ensemble de cours");
            System.out.println("3. Consulter la liste de cours d'un programme");
            System.out.println("4. Consulter la liste de cours d'un trimestre");
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
        String input = scanner.nextLine();
        String url = baseHost + "/courses/search/" + input;
        
        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);
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
                url = baseHost + "/ensemble/voir/" + ensembleId;
                System.out.println("\n[Action] Récupération des données...");
                break;

            default:
                System.out.println("Option invalide.");
                return;
        }

        // URL à copier coller dans un browser
        System.out.println("URL cible : " + url);
    }
}