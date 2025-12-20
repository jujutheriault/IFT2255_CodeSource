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

    public static void register(Javalin app) {
        registerUserRoutes(app);
        registerCourseRoutes(app);
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
        CourseController courseController = new CourseController(courseService, userService); // ✅ MODIFIÉ ICI

        app.get("/courses", courseController::getAllCourses);
        app.get("/courses/{id}", courseController::getCourseById);
    }
}