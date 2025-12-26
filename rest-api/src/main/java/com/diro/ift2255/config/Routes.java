package com.diro.ift2255.config;

import com.diro.ift2255.controller.AvisController;
import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.EnsembleController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.service.AvisService;
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
     * 
     * enregistrement de l'applicatino
     * @param app une application javalin
     */
    public static void register(Javalin app) {

        HttpClientApi httplientApi = new HttpClientApi();

        CourseService courseService = new CourseService(httplientApi);
        CourseController courseController = new CourseController(courseService);

        registerUserRoutes(app);
        registerCourseRoutes(app, courseController);
        registerProgramRoutes(app, courseController);
        createEnsembleRoutes(app);
        registerAvisRoutes(app);
    }
    /**
     * enregistrement des routes de l'utilisateur
     * @param app une application javalin
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
     * enregistrement des routes pour les cours
     * @param app une application javalin
     * @param courseController le controleur pour les cours
     */
    private static void registerCourseRoutes(Javalin app, CourseController courseController) {

        app.get("/courses", courseController::getAllCourses);
        app.get("/courses/{id}", courseController::getCourseById);
        app.get("/courses/search/{recherche}", courseController::searchCourses); 
        app.get("/courses/{id}/schedule", courseController::getCourseSchedule);
        app.get("/courses/{id}/eligibility", courseController::checkEligibility);

    }
    /**
     * enregistrement des routes pour les programmes 
     * @param app une application javalin
     * @param courseController le controleur des cours
     */
    private static void registerProgramRoutes(Javalin app, CourseController courseController) {
        // Route pour la recherche par programme
        app.get("/programs", courseController::getCoursesByProgram);
        app.get("/programs/semester/{semester}", courseController::getCoursesByProgramAndSemester);
    }

        // http://localhost:7070/courses/search/IFT?courses_sigle=ift1015,ift1025,esp1900  utilisation url
        // http://localhost:7070/courses/search/java
 
    /**
     * routes pour la creation d'ensembles de cours
     * @param app une application javalin
     */
    private static void createEnsembleRoutes(Javalin app){
        EnsembleController ensembleController = new EnsembleController();

        app.get("/ensemble/create/{idEnsemble}", ensembleController::createEnsemble);
        app.get("/ensemble/consult/{idEnsemble}", ensembleController::getEnsembleById);
        app.get("/ensemble/{idEnsemble}/add/{courseId}", ensembleController::addCourse); 
        app.get("/ensemble/{idEnsemble}/delete/{courseId}", ensembleController::deleteCourse);
    }

    private static void registerAvisRoutes(Javalin app) {
        AvisService avisService = new AvisService("../data/avis.ndjson");
        AvisController avisController = new AvisController(avisService);

        app.post("/avis", avisController::createAvis);
        app.get("/avis/{sigle}", avisController::getAvisBySigle);
        app.get("/avis/{sigle}/resume", avisController::getResume);
    }
}
