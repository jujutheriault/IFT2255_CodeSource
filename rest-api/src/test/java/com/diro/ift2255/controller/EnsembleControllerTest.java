package com.diro.ift2255.controller;

import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diro.ift2255.model.EnsembleCours;

import io.javalin.http.Context;

@ExtendWith(MockitoExtension.class)
class EnsembleControllerTest {

    @Mock
    Context mockContext;

    EnsembleController controller;

    @BeforeEach
    void setUp() {
        controller = new EnsembleController();
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
    // ------------------ Tests ------------------

    @Test
    @DisplayName("createEnsemble devrait retourner 400 quand l'ensemble existe deja")
    void testCreateEnsembleAlreadyExists() {
        // ARRANGE
        when(mockContext.pathParam("idEnsemble")).thenReturn("ENS-1");
        when(mockContext.status(400)).thenReturn(mockContext);
        when(mockContext.status(201)).thenReturn(mockContext);

        // ACT
        controller.createEnsemble(mockContext); // première création
        controller.createEnsemble(mockContext); // deuxième création (doit échouer)

        // ASSERT
        try {
            verify(mockContext).status(201);
            OK("Status 201 retourne pour la creation initiale", false);

            verify(mockContext).status(400);
            OK("Status 400 retourne pour la creation d'un ensemble existant", false);

            verify(mockContext, times(2)).json(any());
            OK("Reponse JSON envoyee 2 fois", false);
        } catch (Exception e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Ajouter un cours retourne 404 si l'ensemble n'existe pas")
    void testAddCourseToNonExistentEnsemble() {
        // ARRANGE
        when(mockContext.pathParam("idEnsemble")).thenReturn("ENS-NonExistent");
        when(mockContext.pathParam("courseId")).thenReturn("IFT-1015");
        when(mockContext.status(404)).thenReturn(mockContext);

        // ACT
        controller.addCourse(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(404);
            OK("Status 404 retourne pour un ensemble inexistant", false);

            verify(mockContext).json(argThat(obj ->
                    obj instanceof Map && ((Map<?, ?>) obj).containsKey("error")));
            OK("Message d'erreur envoyé", false);
        } catch (Exception e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Add course should return 400 when course ID is invalid")
    void testAddCourseInvalidCourseId() {
        // ARRANGE
        when(mockContext.pathParam("idEnsemble")).thenReturn("ENS-1");
        when(mockContext.pathParam("courseId")).thenReturn("A"); // trop court
        when(mockContext.status(201)).thenReturn(mockContext);
        when(mockContext.status(400)).thenReturn(mockContext);

        controller.createEnsemble(mockContext); // créer l'ensemble

        // ACT
        controller.addCourse(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Status 400 set for invalid course ID", false);

            verify(mockContext).json(argThat(obj ->
                    obj instanceof Map && ((Map<?, ?>) obj).containsKey("error")));
            OK("Error message returned");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Add course should return 200 when course is added successfully")
    void testAddCourseSuccess() {
        // ARRANGE
        when(mockContext.pathParam("idEnsemble")).thenReturn("ENS-1");
        when(mockContext.pathParam("courseId")).thenReturn("IFT-1015");

        when(mockContext.status(anyInt())).thenReturn(mockContext);

        controller.createEnsemble(mockContext);

        // ACT
        controller.addCourse(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(200);
            OK("Status 200 set when course added", false);

            verify(mockContext).json(argThat(obj ->
                    obj instanceof EnsembleCours &&
                    ((EnsembleCours) obj).getList().contains("IFT-1015")));
            OK("Course added to ensemble");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }
}