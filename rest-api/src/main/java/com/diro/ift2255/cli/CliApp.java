package com.diro.ift2255.cli;

import com.diro.ift2255.controller.CourseController;
import com.diro.ift2255.controller.UserController;
import com.diro.ift2255.service.CourseService;
import com.diro.ift2255.service.UserService;
import com.diro.ift2255.util.HttpClientApi;

public class CliApp {

    public static void main(String[] args) {
        // Instanciation des services
        UserService userService = new UserService();
        CourseService courseService = new CourseService(new HttpClientApi());

        // Instanciation des controllers
        UserController userController = new UserController(userService);
        CourseController courseController = new CourseController(courseService, userService);

        // Instanciation du controller CLI
        CliController cliController = new CliController(userController, courseController);

        // Lancement du menu
        Menu menu = new Menu(cliController);
        menu.start();
    }
}
