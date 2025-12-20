package com.diro.ift2255.cli;

import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.User;

import java.util.List;
import java.util.Optional;

public class CliController {

    private final UserController userController;
    private final CourseController courseController;

    public CliController(UserController userController, CourseController courseController) {
        this.userController = userController;
        this.courseController = courseController;
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

    // Rechercher un cours par sigle ou mot-clé
    public void searchCourse(String query) {
        List<Course> results = courseController.searchCoursesConsole(query);
        if (results.isEmpty()) {
            System.out.println("Aucun cours trouvé pour : " + query);
        } else {
            System.out.println("Résultats de la recherche :");
            for (Course c : results) {
                System.out.println(c.getId() + " - " + c.getName());
            }
        }
    }

    // Vérifier l’éligibilité à un cours pour un utilisateur
    public void checkEligibility(int userId, String courseId) {
        boolean eligible = courseController.isUserEligibleForCourse(userId, courseId);
        System.out.println(eligible ? "Utilisateur éligible." : "Utilisateur non éligible.");
    }

    // Récupérer un cours par ID (optionnel)
    public Optional<Course> getCourseByIdConsole(String courseId) {
        return courseController.getCourseByIdConsole(courseId);
    }
}
