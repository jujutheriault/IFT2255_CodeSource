package com.diro.ift2255.cli;

import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.controller.ComparaisonController;
import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.User;
import com.diro.ift2255.model.ResultatAgrege;
import com.diro.ift2255.model.Horaire;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.service.ResultatService;
import com.diro.ift2255.service.HoraireService;
import com.diro.ift2255.service.EligibiliteService;
import com.diro.ift2255.model.AvisEtudiant;
import com.diro.ift2255.service.AvisService;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

public class CliController {

    private final UserController userController;
    private final CourseController courseController;
    private final ComparaisonController comparaisonController;
    private final ResultatService resultatService;
    private final HoraireService horaireService; 
    private final AvisService avisService;

    public CliController(UserController userController, CourseController courseController) {
        this.userController = userController;
        this.courseController = courseController;
        this.comparaisonController = new ComparaisonController();
        this.resultatService = new ResultatService();
        this.horaireService = new HoraireService();
        this.avisService = new AvisService();
    }

    // Afficher l'horaire d'un étudiant
    public void afficherHoraire(int userId) {
        Optional<User> userOpt = userController.getUserByIdConsole(userId);
        
        if (userOpt.isEmpty()) {
            System.out.println("❌ Utilisateur non trouvé avec l'ID : " + userId);
            return;
        }

        User user = userOpt.get();
        List<Horaire> horaires = horaireService.getHoraireByUserId(userId);

        if (horaires.isEmpty()) {
            System.out.println("📅 Aucun horaire disponible pour " + user.getName());
            return;
        }

        System.out.println("\n══════════════════════════════════════════════════════════════════════════");
        System.out.println("                        HORAIRE DE " + user.getName().toUpperCase() + "                              ║");
        System.out.println("══════════════════════════════════════════════════════════════════════════\n");

        int count = 1;
        for (Horaire h : horaires) {
            Optional<Course> courseOpt = courseController.getCourseByIdConsole(h.getSigle());
            String courseName = courseOpt.isPresent() ? courseOpt.get().getName() : "Cours inconnu";

            System.out.println("─────────────────────────────────────────────────────────────────────────");
            System.out.printf("📚 COURS #%d\n", count++);
            System.out.println("─────────────────────────────────────────────────────────────────────────");
            System.out.println("🆔 Sigle        : " + h.getSigle());
            System.out.println("📖 Nom          : " + courseName);
            System.out.printf("🕐 Horaire      : %s %s - %s\n", 
                h.getJourSemaine(), 
                h.getHeureDebut(), 
                h.getHeureFin()
            );
            System.out.println("📍 Lieu         : " + h.getPavillon() + ", local " + h.getLocal());
            System.out.println();
        }

        System.out.println("═════════════════════════════════════════════════════════════════════════");
        System.out.printf("📊 Total : %d cours\n", horaires.size());
        System.out.println("═════════════════════════════════════════════════════════════════════════\n");
    }

    // Liste tous les utilisateurs
    public void listUsers() {
        List<User> users = userController.getAllUsersConsole();
        System.out.println("=== Utilisateurs ===");
        for (User u : users) {
            System.out.println(u.getId() + " - " + u.getName() + " (" + u.getEmail() + ")");
        }
    }

    // Liste tous les cours
    public void listCourses() {
        System.out.println("=== Cours ===");
        courseController.getAllCoursesConsole();
    }

    // ✅ RECHERCHE PAR SIGLE
    public void searchBySigle(String sigle) {
        List<Course> allCourses = courseController.searchCoursesConsole("");
        List<Course> results = new ArrayList<>();
        
        for (Course c : allCourses) {
            if (c.getId() != null && c.getId().toLowerCase().contains(sigle.toLowerCase())) {
                results.add(c);
            }
        }
        
        afficherResultatsRecherche(results, "sigle", sigle);
    }

    // ✅ RECHERCHE PAR NOM
    public void searchByName(String nom) {
        List<Course> allCourses = courseController.searchCoursesConsole("");
        List<Course> results = new ArrayList<>();
        
        for (Course c : allCourses) {
            if (c.getName() != null && c.getName().toLowerCase().contains(nom.toLowerCase())) {
                results.add(c);
            }
        }
        
        afficherResultatsRecherche(results, "nom", nom);
    }

    // ✅ RECHERCHE PAR DESCRIPTION
    public void searchByDescription(String description) {
        List<Course> allCourses = courseController.searchCoursesConsole("");
        List<Course> results = new ArrayList<>();
        
        for (Course c : allCourses) {
            if (c.getDescription() != null && c.getDescription().toLowerCase().contains(description.toLowerCase())) {
                results.add(c);
            }
        }
        
        afficherResultatsRecherche(results, "description", description);
    }

    // ✅ AFFICHER LES RÉSULTATS DE RECHERCHE
    private void afficherResultatsRecherche(List<Course> results, String type, String query) {
        if (results.isEmpty()) {
            System.out.println("❌ Aucun cours trouvé avec \"" + query + "\" dans " + type);
            return;
        }
        
        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║       RÉSULTATS - \"" + query + "\" dans " + type.toUpperCase() + "                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝\n");
        
        for (Course c : results) {
            System.out.printf("%-10s - %s (%d crédits)\n", c.getId(), c.getName(), c.getCredits());
        }
        
        System.out.println("\n═════════════════════════════════════════════════════════════════════════");
        System.out.printf("📊 Total : %d cours trouvés\n", results.size());
        System.out.println("═════════════════════════════════════════════════════════════════════════\n");
    }

    // Lister les cours par trimestre
    public void listerCoursParTrimestre(String trimestre) {
        List<Course> allCourses = courseController.searchCoursesConsole("");
        RechercheCours recherche = new RechercheCours(allCourses, null);
        
        String saison = trimestre.substring(0, 1).toUpperCase();
        List<Course> resultat = recherche.filtrerParTrimestre(saison);
        
        if (resultat.isEmpty()) {
            System.out.println("❌ Aucun cours trouvé pour le trimestre " + trimestre);
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                  COURS OFFERTS - " + getNomSaison(saison) + "                          ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝\n");

        for (Course c : resultat) {
            System.out.printf("%-10s - %s (%d crédits)\n", c.getId(), c.getName(), c.getCredits());
        }

        System.out.println("\n═════════════════════════════════════════════════════════════════════════");
        System.out.printf("📊 Total : %d cours\n", resultat.size());
        System.out.println("═════════════════════════════════════════════════════════════════════════\n");
    }

    public Optional<Course> getCourseByIdConsole(String courseId) {
        return courseController.getCourseByIdConsole(courseId);
    }

    // Afficher les détails complets d'un cours
    public void afficherDetailsCours(String courseId) {
        Optional<Course> courseOpt = courseController.getCourseByIdConsole(courseId);

        if (courseOpt.isEmpty()) {
            System.out.println("❌ Cours non trouvé : " + courseId);
            return;
        }

        Course c = courseOpt.get();

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                          DÉTAILS DU COURS                                ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝\n");

        System.out.println("🆔 Sigle             : " + c.getId());
        System.out.println("📖 Nom               : " + c.getName());
        System.out.println("⭐ Crédits           : " + c.getCredits());

        System.out.println("\n📝 Description :");
        if (c.getDescription() != null && !c.getDescription().isEmpty()) {
            System.out.println("   " + c.getDescription());
        } else {
            System.out.println("   Non disponible");
        }

        System.out.println("\n🔗 Prérequis :");
        if (c.getPrerequis() != null && !c.getPrerequis().isEmpty()) {
            for (String prereq : c.getPrerequis()) {
                System.out.println("   • " + prereq);
            }
        } else {
            System.out.println("   Aucun");
        }

        System.out.println("\n🔗 Corequis :");
        if (c.getCorequis() != null && !c.getCorequis().isEmpty()) {
            for (String coreq : c.getCorequis()) {
                System.out.println("   • " + coreq);
            }
        } else {
            System.out.println("   Aucun");
        }

        System.out.println("\n📅 Sessions disponibles :");
        if (c.getTerms() != null && !c.getTerms().isEmpty()) {
            if (Boolean.TRUE.equals(c.getTerms().get("autumn"))) {
                System.out.println("   ✓ Automne");
            }
            if (Boolean.TRUE.equals(c.getTerms().get("winter"))) {
                System.out.println("   ✓ Hiver");
            }
            if (Boolean.TRUE.equals(c.getTerms().get("summer"))) {
                System.out.println("   ✓ Été");
            }
        } else {
            System.out.println("   Non spécifié");
        }

        Optional<ResultatAgrege> resultatOpt = resultatService.getResultatBySigle(c.getId());
        
        if (resultatOpt.isPresent()) {
            ResultatAgrege resultat = resultatOpt.get();
            
            System.out.println("\n═════════════════════════════════════════════════════════════════════════");
            System.out.println("📊 RÉSULTATS ACADÉMIQUES");
            System.out.println("═════════════════════════════════════════════════════════════════════════");
            System.out.println("📈 Moyenne du cours : " + resultat.getMoyenne());
            System.out.printf("⭐ Score moyen      : %.2f / 5.0\n", resultat.getScore());
            System.out.println("👥 Étudiants        : " + resultat.getParticipants() + " participants");
            System.out.println("📅 Données sur      : " + resultat.getTrimestres() + " trimestres");
        } else {
            System.out.println("\n📊 Aucune donnée académique disponible pour ce cours.");
        }

        if (c.getProfesseur() != null && !c.getProfesseur().isEmpty()) {
            System.out.println("\n👨‍🏫 Professeur       : " + c.getProfesseur());
        }

        if (c.getCycle() != null && !c.getCycle().isEmpty()) {
            System.out.println("🎓 Cycle             : " + c.getCycle());
        }

        if (c.getChargeTravail() > 0) {
            System.out.println("⏱️  Charge de travail : " + c.getChargeTravail() + " heures");
        }

        System.out.println("\n═════════════════════════════════════════════════════════════════════════\n");

        List<AvisEtudiant> avis = avisService.getAvisBySigle(c.getId());
        
        if (!avis.isEmpty()) {
            double noteMoyenne = avisService.getNoteMoyenne(c.getId());
            
            System.out.println("\n═════════════════════════════════════════════════════════════════════════");
            System.out.println("💬 AVIS DES ÉTUDIANTS");
            System.out.println("═════════════════════════════════════════════════════════════════════════");
            System.out.printf("⭐ Note moyenne : %.1f / 5.0 (%d avis)\n\n", noteMoyenne, avis.size());
            
            for (AvisEtudiant av : avis) {
                System.out.println("─────────────────────────────────────────────────────────────────────────");
                System.out.println("👤 " + av.getAuteur() + " • " + "⭐".repeat(av.getNote()) + " (" + av.getNote() + "/5) • " + av.getDate());
                System.out.println("   " + av.getCommentaire());
                System.out.println();
            }
        } else {
            System.out.println("\n💬 Aucun avis étudiant disponible pour ce cours.");
        }
    }

    private String getNomSaison(String saison) {
        switch (saison.toUpperCase()) {
            case "H": return "HIVER";
            case "A": return "AUTOMNE";
            case "E": return "ÉTÉ";
            default: return saison;
        }
    }

    // Comparer plusieurs cours
    public void compareCourses(List<String> courseIds) {
        if (courseIds == null || courseIds.isEmpty()) {
            System.out.println("❌ Aucun cours à comparer.");
            return;
        }

        comparaisonController.reinitialiserSelection();

        int count = 0;
        for (String id : courseIds) {
            Optional<Course> course = courseController.getCourseByIdConsole(id);
            if (course.isPresent()) {
                comparaisonController.selectionnerCoursComparer(course.get());
                count++;
            } else {
                System.out.println("⚠️  Cours non trouvé : " + id);
            }
        }

        if (count == 0) {
            System.out.println("❌ Aucun cours valide trouvé.");
            return;
        }

        afficherComparaison();
    }

    // Afficher le tableau de comparaison
    private void afficherComparaison() {
        Course[] courses = comparaisonController.getCoursComparer();
        int taille = comparaisonController.getTaille();

        if (taille == 0) {
            System.out.println("❌ Aucun cours sélectionné pour la comparaison.");
            return;
        }

        System.out.println("\n╔══════════════════════════════════════════════════════════════════════════╗");
        System.out.println("║                        TABLEAU DE COMPARAISON                            ║");
        System.out.println("╚══════════════════════════════════════════════════════════════════════════╝\n");

        for (int i = 0; i < taille; i++) {
            Course c = courses[i];
            if (c == null) continue;

            System.out.println("─────────────────────────────────────────────────────────────────────────");
            System.out.printf("📚 COURS #%d\n", i + 1);
            System.out.println("─────────────────────────────────────────────────────────────────────────");
            System.out.println("🆔 Sigle        : " + c.getId());
            System.out.println("📖 Nom          : " + c.getName());
            System.out.println("⭐ Crédits      : " + c.getCredits());
            
            String desc = c.getDescription();
            if (desc != null && !desc.isEmpty()) {
                String shortDesc = desc.length() > 80 ? desc.substring(0, 77) + "..." : desc;
                System.out.println("📝 Description  : " + shortDesc);
            } else {
                System.out.println("📝 Description  : N/A");
            }

            if (c.getPrerequis() != null && !c.getPrerequis().isEmpty()) {
                System.out.println("🔗 Prérequis    : " + String.join(", ", c.getPrerequis()));
            } else {
                System.out.println("🔗 Prérequis    : Aucun");
            }

            if (c.getCorequis() != null && !c.getCorequis().isEmpty()) {
                System.out.println("🔗 Corequis     : " + String.join(", ", c.getCorequis()));
            } else {
                System.out.println("🔗 Corequis     : Aucun");
            }

            if (c.getTerms() != null) {
                List<String> sessions = new java.util.ArrayList<>();
                if (Boolean.TRUE.equals(c.getTerms().get("autumn"))) sessions.add("Automne");
                if (Boolean.TRUE.equals(c.getTerms().get("winter"))) sessions.add("Hiver");
                if (Boolean.TRUE.equals(c.getTerms().get("summer"))) sessions.add("Été");
                
                if (!sessions.isEmpty()) {
                    System.out.println("📅 Sessions     : " + String.join(", ", sessions));
                } else {
                    System.out.println("📅 Sessions     : Non spécifié");
                }
            }
            System.out.println();
        }

        System.out.println("═════════════════════════════════════════════════════════════════════════");
        System.out.printf("📊 CHARGE TOTALE : %d crédits\n", comparaisonController.calculerChargeTotale());
        System.out.println("═════════════════════════════════════════════════════════════════════════\n");
    }

    // Vérifier l'éligibilité
    public void checkEligibility(int userId, String courseId) {
        Optional<User> userOpt = userController.getUserByIdConsole(userId);
        Optional<Course> courseOpt = courseController.getCourseByIdConsole(courseId);
        
        if (userOpt.isEmpty()) {
            System.out.println("❌ Utilisateur introuvable.");
            return;
        }
        
        if (courseOpt.isEmpty()) {
            System.out.println("❌ Cours introuvable.");
            return;
        }
        
        User user = userOpt.get();
        Course course = courseOpt.get();
        
        boolean eligible = EligibiliteService.estEligible(user, course);
        
        System.out.println("\n═══════════════════════════════════════════════════════════");
        System.out.println("👤 Étudiant : " + user.getName());
        System.out.println("🎓 Cycle étudiant : " + getCycleDescription(user.getCycle()));
        System.out.println("📚 Cours : " + course.getId() + " - " + course.getName());
        System.out.println("📖 Cycle du cours : " + EligibiliteService.getDescriptionCycle(course.getId()));
        System.out.println("═══════════════════════════════════════════════════════════");
        
        if (eligible) {
            System.out.println("✅ L'étudiant EST éligible à ce cours.");
        } else {
            System.out.println("❌ L'étudiant N'EST PAS éligible à ce cours (cycle insuffisant).");
        }
        
        System.out.println("═══════════════════════════════════════════════════════════\n");
    }

    private String getCycleDescription(int cycle) {
        switch (cycle) {
            case 1: return "1er cycle (Baccalauréat)";
            case 2: return "2e cycle (Maîtrise)";
            case 3: return "3e cycle (Doctorat)";
            default: return "Inconnu";
        }
    }
}