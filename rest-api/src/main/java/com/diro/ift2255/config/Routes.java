package com.diro.ift2255.config;

import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.UserService;
import com.diro.ift2255.util.HttpClientApi;

import io.javalin.Javalin;
/*
 * 
 * Classe qui gère les routes de l'API REST
 * 
 * 
 * 
 */
public class Routes {
    /**
     * Enregistre les routes de l'application Javalin
     * @param app l'application Javalin
     */
    public static void register(Javalin app) {
        registerUserRoutes(app);
        registerCourseRoutes(app);
    }
    /**
     * Enrtegistre les routes des utilisateurs
     * @param app l'application Javalin
     */
    private static void registerUserRoutes(Javalin app) {
        UserService userService = new UserService();
        UserController userController = new UserController(userService);

        app.get("/users", userController::getAllUsers);
        app.get("/users/{id}", userController::getUserById);
        app.post("/users", userController::createUser);
        app.put("/users/{id}", userController::updateUser);
        app.delete("/users/{id}", userController::deleteUser);
    }
    /**
     * Enregistre les routes pour les cours
     * @param app l'application Javalin
     */
    private static void registerCourseRoutes(Javalin app) {
        CourseService courseService = new CourseService(new HttpClientApi());
        CourseController courseController = new CourseController(courseService);

        app.get("/courses", courseController::getAllCourses);
        app.get("/courses/{id}", courseController::getCourseById);
    }
}