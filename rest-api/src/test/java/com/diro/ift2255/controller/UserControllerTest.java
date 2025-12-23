package com.diro.ift2255.controller;

import java.util.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diro.ift2255.model.User;
import com.diro.ift2255.service.UserService;

import io.javalin.http.Context;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;



@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserService mockService;

    @Mock
    private Context mockContext;

    private UserController controller;

    @BeforeEach
    public void setUp() {
        controller = new UserController(mockService);
    }

 // ------------------ Méthodes utilitaires pour l'affichage ------------------
    private void printMessage(String message, boolean isOk, boolean isLast) {
        String symbol = isLast ? "└─" : "├─";
        String status = isOk ? "[PASS]" : "[FAIL]";
        System.out.println("    │   " + symbol + " " + status + " " + message);
    }

    private void OK(String message) {
        printMessage(message, true, true);
    }

    private void OK(String message, boolean isLast) {
        printMessage(message, true, isLast);
    }

    private void Err(String message) {
        printMessage(message, false, true);
    }
//----------------------------TESTS---------------------------------------------
    @Test
    @DisplayName("Retourne la liste des utilisateurs")
    void testGetAllUsers() {
        
        List<User> mockUsers = List.of(new User(1,"Alice","emailAlice"),new User(2,"Bob","emailBob"));
        //ARRANGE
        when(mockService.getAllUsers()).thenReturn(mockUsers);
        //ACT
        controller.getAllUsers(mockContext);
        //ASSERT
        try{
        verify(mockContext).json(mockUsers);
        OK("liste des utilisateurs retournee");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;

        }
    }

    @Test
    @DisplayName("retourne 200 si un utlisateur existe")
    void testGetUserByIdSuccess(){
        User user = new User(1,"Alice","emailAlice");
        //ARRANGE
        when(mockService.getUserById(1)).thenReturn(Optional.of(user));
        when(mockContext.pathParam("id")).thenReturn("1");
        //ACT
        controller.getUserById(mockContext);
        //ASERT
        try{
        verify(mockContext).json(user);
        OK("Utilisateur trouve");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }

    }

    @Test
    @DisplayName("retourne 404 si un utilisateur n'est pas trouve,ID invalide")
    void testGetUserByIdNotFound() {
        //ARRANGE
        when(mockService.getUserById(1)).thenReturn(Optional.empty());
        when(mockContext.pathParam("id")).thenReturn("1");
        //ACT
        controller.getUserById(mockContext);
        //ASSERT
        try{
        verify(mockContext).status(404);
        OK("404 retourne",false);

        verify(mockContext).json(any());
        OK("erreur retourne",false);         
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("retourne 400 si l'identifiant est invalide")
    void testGetUserInvalidId() {
        //ARRANGE
        when(mockContext.pathParam("id")).thenReturn("123");
        //ACT
        controller.getUserById(mockContext);
        //ASSERT
        try{
        verify(mockContext).status(400);
        OK("status 400 retourne",false);

        verify(mockContext).json(any());
        OK("message d'erreur",false);
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("retourne 201 si un utilisateur est cree")
    void testCreateUserSuccess() {
        User user = new User(1,"Alice","emailAlice");
        //ARRANGE
        when(mockContext.bodyAsClass(User.class)).thenReturn(user);
        when(mockContext.status(201)).thenReturn(mockContext);
        //ACT
        controller.createUser(mockContext);
        //ASSERT
        try{
        verify(mockService).createUser(user);
        OK("service appele",false);

        verify(mockContext).status(201);
        OK("status 201 retourne",false);

        verify(mockContext).json(user);
        OK("Utilisateur retourne");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("retourne 200 quand un utilisateur est mis a jour avec succes")
    void testUpdateUserSuccess() {
        User user = new User(1,"Alice","emailAlice");
        //ARRANGE
        when(mockContext.pathParam("id")).thenReturn("1");
        when(mockContext.bodyAsClass(User.class)).thenReturn(user);
        //ACT
        controller.updateUser(mockContext);
        //ASSERT
        try{
        verify(mockService).updateUser(1,user);
        OK("Service update appele",false);

        verify(mockContext).json(user);
        OK("utilisateur mis a jour retourne");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("retourne 204 quand un utilisateur est supprime")
    void testDeleteUserSuccess() {
        //ARRANGE
        when(mockContext.pathParam("id")).thenReturn("1");
        //ACT
        controller.deleteUser(mockContext);
        //ASSERT
        try{
        verify(mockService).deleteUser(1);
        OK("Service delete appele",false);

        verify(mockContext).status(204);
        OK("status 204 retourne");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        }    
    }

    @Test
    @DisplayName("retourne 400 quand l'utilisateur n'est pas supprime")
    void testDeleteUserInvalidId() {
        //ARRANGE
        when(mockContext.pathParam("id")).thenReturn("abc");
        when(mockContext.status(400)).thenReturn(mockContext);
        //ACT
        controller.deleteUser(mockContext);
        //ASSERT
        try{
        verify(mockContext).status(400);
        OK("status 400 retourne",false);

        verify(mockContext).json(any());
        OK("erreur retourne",false);

        verify(mockService, never()).deleteUser(anyInt());
        OK("service non appele");
        } catch(AssertionError e){
            Err(e.getMessage());
            throw e;
        } 


    }
}
