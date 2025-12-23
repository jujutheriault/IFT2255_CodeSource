package com.diro.ift2255.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.model.User;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.util.ResponseUtil;
import com.fasterxml.jackson.databind.JsonNode;

import io.javalin.http.Context;


public class CourseController {
    // Service qui contient la logique métier pour la manipulation des cours et la communication avec les services externes
    private final CourseService service;
    private User user = null;

    /**
     * Controlleur pour un service externe
     * @param service un service externe
     */
    public CourseController(CourseService service) {
        this.service = service;
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
     * Récupère la liste de tous les cours.
     * @param ctx Contexte Javalin représentant la requête et la réponse HTTP
     */
    public void getAllCourses(Context ctx) {
        Map<String, String> queryParams = extractQueryParams(ctx);

        List<Course> courses = service.getAllCourses(queryParams);
        ctx.json(courses);
    }

    /**
     * Récupère un cours spécifique par son ID.
     * @param ctx Contexte Javalin représentant la requête et la réponse HTTP
     */
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

    /**
     * Recherche des cours en fonction des paramètres de requête.
     * @param ctx Contexte Javalin représentant la requête et la réponse HTTP
     */   

    public void searchCourses(Context ctx) {

        Map<String, String> queryParams = extractQueryParams(ctx);

        // On va chercher les cours selon les paramètres puis on crée un objet RechercheCours
        List<Course> courses = service.getAllCourses(queryParams);
        RechercheCours recherche = new RechercheCours(courses, user);

        String motRecherche = ctx.pathParam("recherche");

        List<Course> searchResult = recherche.rechercher(motRecherche);

        if (!searchResult.isEmpty()) {
            ctx.json(searchResult);
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Aucun résultat trouvé pour la recherche" + motRecherche));
        }
    }  

     /**
     * Recherche de la liste de cours d'un programme.
     * @param ctx Contexte Javalin représentant la requête et la réponse HTTP
     */   

    public void getCoursesByProgram(Context ctx) {
        String programId = ctx.queryParam("programs_list");
        String responseLevel = ctx.queryParam("response_level");

        if (programId == null || programId.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre programs_list est requis (ex: 117510)."));
            return;
    }

        boolean includeDetail = "true".equalsIgnoreCase(ctx.queryParam("include_courses_detail"));
        JsonNode programData = service.getProgram(programId, includeDetail, responseLevel);
        ctx.status(200).json(programData);
    }

    /**
     * Recherche de la liste de cours d'un programme et d'un trimestre.
     * @param ctx Contexte Javalin représentant la requête et la réponse HTTP
     */   

    public void getCoursesByProgramAndSemester(Context ctx) {
        String programId = ctx.queryParam("programs_list");
        
        if (programId == null || programId.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre programs_list est requis (ex: 117510)."));
            return;
        }
        
        String semesterRaw = ctx.pathParam("semester");

        if (semesterRaw == null || semesterRaw.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre semester est requis (ex: H25 ou a25)."));
            return;
        }

        String semester;

        try {
            semester = service.normalizeSemester(semesterRaw);
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(ResponseUtil.formatError(e.getMessage()));
            return;
        }

        List<Course> courses = service.getCoursesByProgramAndSemester(programId, semester);

        if (courses.isEmpty()) {
            ctx.status(404).json(ResponseUtil.formatError("Aucun cours trouvé pour ce programme et ce trimestre."));
            return;
        }

        ctx.status(200).json(courses);
    }


    /**
     * Vérifie que l'ID du cours est bien formé
     * @param courseId L'ID du cours à valider
     * @return Valeur booléeene indiquant si l'ID est valide
     */
    private boolean validateCourseId(String courseId) {
        return courseId != null && courseId.trim().length() >= 6;
    }

    /**
     * Récupère tous les paramètres de requête depuis l'URL et les met dans une Map
     * @param ctx Contexte Javalin représentant la requête HTTP
     * @return Map contenant les paramètres de requête et leurs valeurs
     */
    private Map<String, String> extractQueryParams(Context ctx) {
        Map<String, String> queryParams = new HashMap<>();

        ctx.queryParamMap().forEach((key, values) -> {
            if (!values.isEmpty()) {
                queryParams.put(key, values.get(0));
            }
        });

        return queryParams;
    }

    public void getCourseSchedule(Context ctx) {
        String id = ctx.pathParam("id");

        if (!validateCourseId(id)) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        String semesterRaw = ctx.queryParam("semester");

        if (semesterRaw == null || semesterRaw.isBlank()) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre semester est requis (ex: H25, A24, E24)."));
            return;
        }

        String semester;
        try {
            semester = service.normalizeSemester(semesterRaw); // h25/a25/e24
        } catch (IllegalArgumentException e) {
            ctx.status(400).json(ResponseUtil.formatError(e.getMessage()));
            return;
        }

        Optional<Course> courseOpt = service.getCourseWithScheduleForSemester(id, semester);

        if (courseOpt.isEmpty()) {
            ctx.status(404).json(ResponseUtil.formatError("Cours introuvable: " + id));
            return;
        }

        Course course = courseOpt.get();

    // schedules peut être null ou vide si le cours n’est pas offert à ce trimestre
        if (course.getSchedules() == null || course.getSchedules().isEmpty()) {
            ctx.status(404).json(ResponseUtil.formatError(
                "Aucun horaire disponible pour " + id + " au trimestre " + semesterRaw.toUpperCase()
                + ". Le cours n'est peut-être pas offert à ce trimestre."
            ));
        return;
        }

    // Option 1 (simple): retourner juste schedules
        ctx.status(200).json(course.getSchedules());

    // Option 2 (si tu préfères retourner le cours complet):
    // ctx.status(200).json(course);
    }
}
