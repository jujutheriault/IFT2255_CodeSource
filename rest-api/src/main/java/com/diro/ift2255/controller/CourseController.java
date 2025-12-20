package com.diro.ift2255.controller;

import io.javalin.http.Context;
import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.User;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.UserService;
import com.diro.ift2255.util.ResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class CourseController {

    private final CourseService service;
    private final UserService userService; // ajouter pour l'éligibilité
    private User user = null;

    public CourseController(CourseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return this.user;
    }

    // ==================== Javalin routes ====================
    public void getAllCourses(Context ctx) {
        Map<String, String> queryParams = extractQueryParams(ctx);
        List<Course> courses = service.getAllCourses(queryParams);
        ctx.json(courses);
    }

    public void getCourseById(Context ctx) {
        String id = ctx.pathParam("id");

        if (!validateCourseId(id)) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        Optional<Course> course = service.getCourseById(id);
        if (course.isPresent()) {
            ctx.json(course.get());
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Aucun cours ne correspond à l'ID: " + id));
        }
    }

    public void searchCourses(Context ctx) {
        Map<String, String> queryParams = extractQueryParams(ctx);
        List<Course> courses = service.getAllCourses(queryParams);
        RechercheCours recherche = new RechercheCours(courses, user);

        String motRecherche = ctx.pathParam("recherche");
        List<Course> searchResult = recherche.rechercher(motRecherche);

        if (!searchResult.isEmpty()) {
            ctx.json(searchResult);
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Aucun résultat trouvé pour la recherche " + motRecherche));
        }
    }

    private boolean validateCourseId(String courseId) {
        return courseId != null && courseId.trim().length() >= 6;
    }

    private Map<String, String> extractQueryParams(Context ctx) {
        Map<String, String> queryParams = new HashMap<>();
        ctx.queryParamMap().forEach((key, values) -> {
            if (!values.isEmpty()) {
                queryParams.put(key, values.get(0));
            }
        });
        return queryParams;
    }

    // ==================== Méthodes CLI ====================
    public void getAllCoursesConsole() {
        List<Course> courses = service.getAllCourses(null);
        System.out.println("=== Liste des cours ===");
        for (Course c : courses) {
            System.out.println(c.getId() + " - " + c.getName());
        }
    }

    public List<Course> searchCoursesConsole(String keyword) {
        List<Course> courses = service.getAllCourses(null);
        return courses.stream()
                .filter(c -> c.getId().toLowerCase().contains(keyword.toLowerCase()) ||
                             c.getName().toLowerCase().contains(keyword.toLowerCase()))
                .toList();
    }

    public Optional<Course> getCourseByIdConsole(String courseId) {
        return service.getCourseById(courseId);
    }

    // Vérifie si un utilisateur est éligible à un cours
    public boolean isUserEligibleForCourse(int userId, String courseId) {
        boolean userExists = userService.getUserById(userId).isPresent();
        boolean courseExists = getCourseByIdConsole(courseId).isPresent();
        return userExists && courseExists;
    }

}
