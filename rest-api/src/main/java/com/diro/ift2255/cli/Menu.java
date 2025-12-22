package com.diro.ift2255.cli;

import java.util.ArrayList;
import java.util.List;
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
            System.out.println("3. Rechercher des cours");
            System.out.println("4. Vérifier l'éligibilité d'un utilisateur à un cours");
            System.out.println("5. Comparer des cours");
            System.out.println("6. Voir les détails d'un cours");
            System.out.println("7. Afficher l'horaire d'un étudiant");
            System.out.println("8. Lister les cours par trimestre");
            System.out.println("9. Quitter");
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
                    rechercheAvanceeMenu();
                    break;
                case "4":
                    System.out.print("Entrez l'ID de l'utilisateur : ");
                    int userId = Integer.parseInt(scanner.nextLine());
                    System.out.print("Entrez le sigle du cours : ");
                    String courseId = scanner.nextLine();
                    controller.checkEligibility(userId, courseId);
                    break;
                case "5":
                    comparerCoursMenu();
                    break;
                case "6":
                    voirDetailsCoursMenu();
                    break;
                case "7":
                    afficherHoraireMenu();
                    break;
                case "8":
                    listerParTrimestreMenu();
                    break;
                case "9":
                    System.out.println("Au revoir !");
                    running = false;
                    break;
                default:
                    System.out.println("Option invalide, réessayez.");
            }
        }

        scanner.close();
    }

    private void rechercheAvanceeMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║       RECHERCHE DE COURS              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("1. Par sigle (ex: IFT2255)");
        System.out.println("2. Par nom (ex: logiciel)");
        System.out.println("3. Par description (ex: ajax, java)");
        System.out.println("4. Par trimestre (ex: H25, A24)");
        System.out.print("\nChoix : ");
        
        String choix = scanner.nextLine().trim();
        
        switch (choix) {
            case "1":
                System.out.print("➤ Sigle : ");
                String sigle = scanner.nextLine().trim();
                controller.searchBySigle(sigle);
                break;
                
            case "2":
                System.out.print("➤ Nom : ");
                String nom = scanner.nextLine().trim();
                controller.searchByName(nom);
                break;
                
            case "3":
                System.out.print("➤ Mot dans la description : ");
                String description = scanner.nextLine().trim();
                controller.searchByDescription(description);
                break;
                
            case "4":
                System.out.print("➤ Trimestre (H25, A24, E24) : ");
                String trimestre = scanner.nextLine().trim().toUpperCase();
                controller.listerCoursParTrimestre(trimestre);
                break;
                
            default:
                System.out.println("Option invalide.");
        }
    }

    private void comparerCoursMenu() {
        System.out.println("\n╔═══════════════════════════════════════╗");
        System.out.println("║     COMPARAISON DE COURS              ║");
        System.out.println("╚═══════════════════════════════════════╝");
        System.out.println("Entrez les sigles des cours à comparer, séparés par des virgules.");
        System.out.println("Exemple : IFT1015,IFT1025,IFT2255");
        System.out.print("\n➤ Sigles : ");

        String input = scanner.nextLine().trim();

        if (input.isEmpty()) {
            System.out.println("❌ Aucun cours saisi.");
            return;
        }

        String[] sigles = input.split(",");
        List<String> courseIds = new ArrayList<>();

        for (String sigle : sigles) {
            courseIds.add(sigle.trim().toUpperCase());
        }

        controller.compareCourses(courseIds);
    }

    private void voirDetailsCoursMenu() {
        System.out.print("\n➤ Entrez le sigle du cours : ");

        String courseId = scanner.nextLine().trim().toUpperCase();

        if (courseId.isEmpty()) {
            System.out.println("❌ Aucun sigle saisi.");
            return;
        }

        controller.afficherDetailsCours(courseId);
    }

    private void afficherHoraireMenu() {
        System.out.print("\n➤ Entrez l'ID de l'étudiant : ");

        try {
            int userId = Integer.parseInt(scanner.nextLine().trim());
            controller.afficherHoraire(userId);
        } catch (NumberFormatException e) {
            System.out.println("❌ ID invalide. Veuillez entrer un nombre.");
        }
    }

    private void listerParTrimestreMenu() {
        System.out.print("\n➤ Entrez le trimestre (H25, A24, E24) : ");
        String trimestre = scanner.nextLine().trim().toUpperCase();
        
        if (trimestre.isEmpty()) {
            System.out.println("❌ Trimestre invalide.");
            return;
        }
        
        controller.listerCoursParTrimestre(trimestre);
    }
}