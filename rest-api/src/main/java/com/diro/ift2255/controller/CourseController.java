package com.diro.ift2255.controller;


import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.model.User;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.UserService;
import com.diro.ift2255.util.ResponseUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Arrays;
import io.javalin.http.Context;


public class CourseController {

    private final CourseService service;
    private final UserService userService;
    private User user = null;

    public CourseController(CourseService service, UserService userService) {
        this.service = service;
        this.userService = userService;
    }

    /**
     * Setter pour l'utilisateur
     * @param user un utilisateur
     */
    public void setUser(User user) {
        this.user = user;
    }
    /**
     * Getter pourun utilisateur
     * @return un utilisateur
     */
    public User getUser() {
        return this.user;
    }
    /**
     * Controlleur pour un service externe
     * @param service un service externe
     */

    // ==================== Javalin routes ====================
    
    /**
     * GET /courses
     * Supports: courses_sigle, name, description, include_schedule, schedule_semester
     */
    public void getAllCourses(Context ctx) {
        Map<String, String> queryParams = extractQueryParams(ctx);
        List<Course> courses = service.getAllCourses(queryParams);
        ctx.json(courses);
    }

    /**
     * GET /courses/search/by-sigles?sigles=IFT1015,IFT2255
     */
    public void getCoursesBySigles(Context ctx) {
        String siglesParam = ctx.queryParam("sigles");
        
        if (siglesParam == null || siglesParam.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Paramètre 'sigles' requis"));
            return;
        }

        List<String> sigles = Arrays.asList(siglesParam.split(","));
        List<Course> courses = service.getCoursesBySigles(sigles);
        
        ctx.json(courses);
    }

    /**
     * GET /courses/search/by-name?name=logiciel
     */
    public void searchByName(Context ctx) {
        String name = ctx.queryParam("name");
        
        if (name == null || name.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Paramètre 'name' requis"));
            return;
        }

        List<Course> courses = service.searchByName(name);
        ctx.json(courses);
    }

    /**
     * GET /courses/search/by-description?description=java
     */
    public void searchByDescription(Context ctx) {
        String description = ctx.queryParam("description");
        
        if (description == null || description.trim().isEmpty()) {
            ctx.status(400).json(ResponseUtil.formatError("Paramètre 'description' requis"));
            return;
        }

        List<Course> courses = service.searchByDescription(description);
        ctx.json(courses);
    }

    /**
     * GET /courses/{id}
     * Supports: include_schedule, schedule_semester
     */
    public void getCourseById(Context ctx) {
        String id = ctx.pathParam("id");

        if (!validateCourseId(id)) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        // Extract query params for schedule
        Map<String, String> queryParams = extractQueryParams(ctx);
        
        Optional<Course> course = service.getCourseById(id, queryParams);
        
        if (course.isPresent()) {
            ctx.json(course.get());
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Aucun cours ne correspond à l'ID: " + id));
        }
    }

    /**
     * GET /courses/{id}/schedule?semester=a25
     */
    public void getCourseSchedule(Context ctx) {
        String id = ctx.pathParam("id");
        String semester = ctx.queryParam("semester");

        if (!validateCourseId(id)) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        Optional<Course> course = service.getCourseWithSchedule(id, semester);
        
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

    // ✅ CORRIGÉ : Recherche dans ID, nom ET description
    public List<Course> searchCoursesConsole(String keyword) {
        List<Course> courses = service.getAllCourses(null);
        return courses.stream()
                .filter(c -> 
                    (c.getId() != null && c.getId().toLowerCase().contains(keyword.toLowerCase())) ||
                    (c.getName() != null && c.getName().toLowerCase().contains(keyword.toLowerCase())) ||
                    (c.getDescription() != null && c.getDescription().toLowerCase().contains(keyword.toLowerCase()))
                )
                .toList();
    }

    public Optional<Course> getCourseByIdConsole(String courseId) {
        return service.getCourseById(courseId);
    }

    public boolean isUserEligibleForCourse(int userId, String courseId) {
        boolean userExists = userService.getUserById(userId).isPresent();
        boolean courseExists = getCourseByIdConsole(courseId).isPresent();
        return userExists && courseExists;
    }
}