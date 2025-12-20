package com.diro.ift2255;

import java.util.Scanner;

public class Cli {

    private final String baseHost;

    public Cli(String baseHost) {
        this.baseHost = baseHost;
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("\n=== SYSTÈME DE GESTION DES COURS (CLI) ===");

        while (running) {
            System.out.println("\nQue voulez-vous faire :");
            System.out.println("1. Rechercher un cours (par mot-clé)");
            System.out.println("0. Quitter");
            System.out.print("> ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    cliSearch(scanner);
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
    private void cliSearch(Scanner scanner) {
        System.out.print("Entrez le mot-clé de recherche : ");
        String input = scanner.nextLine();
        String url = baseHost + "/courses/search/" + input;
        
        System.out.println("\n[Action] Recherche lancée...");
        System.out.println("URL cible : " + url);
    }
}