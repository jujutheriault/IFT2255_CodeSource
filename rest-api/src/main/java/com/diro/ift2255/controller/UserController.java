package com.diro.ift2255.controller;

import com.diro.ift2255.model.User;
import com.diro.ift2255.service.UserService;
import io.javalin.http.Context;
import com.diro.ift2255.util.ResponseUtil;

import java.util.List;
import java.util.Optional;

public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    // ==================== Méthodes Javalin (pour les routes HTTP) ====================
    
    public void getAllUsers(Context ctx) {
        List<User> users = service.getAllUsers();
        ctx.json(users);
    }

    public void getUserById(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            Optional<User> user = service.getUserById(id);
            
            if (user.isPresent()) {
                ctx.json(user.get());
            } else {
                ctx.status(404).json(ResponseUtil.formatError("Utilisateur introuvable avec l'ID: " + id));
            }
        } catch (NumberFormatException e) {
            ctx.status(400).json(ResponseUtil.formatError("ID invalide"));
        }
    }

    public void createUser(Context ctx) {
        try {
            User user = ctx.bodyAsClass(User.class);
            service.createUser(user);
            ctx.status(201).json(user);
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.formatError("Données invalides"));
        }
    }

    public void updateUser(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            User user = ctx.bodyAsClass(User.class);
            service.updateUser(id, user);
            ctx.json(user);
        } catch (NumberFormatException e) {
            ctx.status(400).json(ResponseUtil.formatError("ID invalide"));
        } catch (Exception e) {
            ctx.status(400).json(ResponseUtil.formatError("Données invalides"));
        }
    }

    public void deleteUser(Context ctx) {
        try {
            int id = Integer.parseInt(ctx.pathParam("id"));
            service.deleteUser(id);
            ctx.status(204); // No Content
        } catch (NumberFormatException e) {
            ctx.status(400).json(ResponseUtil.formatError("ID invalide"));
        }
    }

    // ==================== Méthodes CLI (pour la console) ====================
    
    public List<User> getAllUsersConsole() {
        return service.getAllUsers();
    }

    public Optional<User> getUserByIdConsole(int id) {
        return service.getUserById(id);
    }
}