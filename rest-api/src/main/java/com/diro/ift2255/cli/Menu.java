package com.diro.ift2255.cli;

import java.util.Scanner;

public class Menu {

    private final CliController controller;
    private final Scanner scanner;

    public Menu(CliController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;

        while (running) {
            System.out.println("\n===== CLI Menu =====");
            System.out.println("1. Lister tous les utilisateurs");
            System.out.println("2. Lister tous les cours");
            System.out.println("3. Rechercher un cours par sigle ou mot-clé");
            System.out.println("4. Vérifier l'éligibilité d'un utilisateur à un cours");
            System.out.println("5. Quitter");
            System.out.print("Choix : ");

            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    controller.listUsers();
                    break;
                case "2":
                    controller.listCourses();
                    break;
                case "3":
                    System.out.print("Entrez le sigle ou mot-clé : ");
                    String query = scanner.nextLine();
                    controller.searchCourse(query);
                    break;
                case "4":
                    System.out.print("Entrez l'ID de l'utilisateur : ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Entrez le sigle du cours : ");
                    String courseId = scanner.nextLine();
                    controller.checkEligibility(userId, courseId);
                    break;
                case "5":
                    System.out.println("Au revoir !");
                    running = false;
                    break;
                default:
                    System.out.println("Option invalide, réessayez.");
            }
        }

        scanner.close();
    }
}
