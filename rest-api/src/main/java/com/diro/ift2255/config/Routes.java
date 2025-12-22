package com.diro.ift2255.config;

import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.controller.ComparaisonController;
import com.diro.ift2255.controller.ProgramController;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.UserService;
import com.diro.ift2255.service.ProgramService;
import com.diro.ift2255.util.HttpClientApi;

import io.javalin.Javalin;

import java.util.List;
import java.util.Map;

public class Routes {

    public static void register(Javalin app) {
        registerUserRoutes(app);
        registerCourseRoutes(app);
        registerComparisonRoutes(app);
        registerProgramRoutes(app); // ✅ AJOUT
    }

    private static void registerUserRoutes(Javalin app) {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        app.get("/users", userController::getAllUsers);
        app.get("/users/{id}", userController::getUserById);
        app.post("/users", userController::createUser);
        app.put("/users/{id}", userController::updateUser);
        app.delete("/users/{id}", userController::deleteUser);
    }

    private static void registerCourseRoutes(Javalin app) {
        UserService userService = new UserService();
        CourseService courseService = new CourseService(new HttpClientApi());
        CourseController courseController = new CourseController(courseService, userService);

        // Routes principales
        app.get("/courses", courseController::getAllCourses);
        app.get("/courses/{id}", courseController::getCourseById);
        
        // ✅ NOUVELLES ROUTES
        app.get("/courses/{id}/schedule", courseController::getCourseSchedule);
        app.get("/courses/search/by-sigles", courseController::getCoursesBySigles);
        app.get("/courses/search/by-name", courseController::searchByName);
        app.get("/courses/search/by-description", courseController::searchByDescription);
    }

    private static void registerComparisonRoutes(Javalin app) {
        CourseService courseService = new CourseService(new HttpClientApi());
        ComparaisonController comparaisonController = new ComparaisonController();

        app.post("/courses/compare", ctx -> {
            try {
                Map<String, List<String>> body = ctx.bodyAsClass(Map.class);
                List<String> courseIds = body.get("courseIds");

                if (courseIds == null || courseIds.isEmpty()) {
                    ctx.status(400).json(Map.of("error", "Veuillez fournir une liste d'IDs de cours"));
                    return;
                }

                comparaisonController.reinitialiserSelection();

                for (String id : courseIds) {
                    courseService.getCourseById(id).ifPresent(comparaisonController::selectionnerCoursComparer);
                }

                ctx.json(comparaisonController.genererComparaison());

            } catch (Exception e) {
                ctx.status(400).json(Map.of("error", "Erreur lors de la comparaison: " + e.getMessage()));
            }
        });
    }

    // ✅ NOUVELLE SECTION : Routes des programmes
    private static void registerProgramRoutes(Javalin app) {
        ProgramService programService = new ProgramService(new HttpClientApi());
        ProgramController programController = new ProgramController(programService);

        app.get("/programs/{id}", programController::getProgramById);
    }
}