package com.diro.ift2255.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.argThat;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.Etudiant;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.model.User;
import com.diro.ift2255.service.CourseService;

import io.javalin.http.Context;

@ExtendWith(MockitoExtension.class) // ← Active Mockito pour ce test
public class CourseControllerTest {

    @Mock // ← Crée un FAUX CourseService
    private CourseService mockService;

    @Mock // ← Crée un FAUX Context Javalin
    private Context mockContext;

    private CourseController controller; // ← Le VRAI contrôleur à tester

    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CourseController Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
        // On injecte les FAUX objets dans le VRAI contrôleur
        controller = new CourseController(mockService);
        testStartTime = System.currentTimeMillis();

        System.out.println("\nTEST: " + testInfo.getDisplayName());
        System.out.println("    ├─ Method: " + testInfo.getTestMethod().get().getName());
        System.out.println("    ├─ Assertions:");
    }

    @AfterEach
    void tearDown(TestInfo testInfo) {
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("    └─ Duration: " + duration + " ms");
    }

    /**************************************************************************
     * Tests for getAllCourses method
     *************************************************************************/

    @Test
    @DisplayName("Get all courses should return all courses when no query parameters")
    void testGetAllCoursesWithoutQueryParams() {
        // ARRANGE
        List<Course> mockCourses = Arrays.asList(
                new Course("IFT1015", "Programmation I"),
                new Course("IFT1025", "Programmation II"));

        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockService.getAllCourses(any())).thenReturn(mockCourses);

        // ACT
        controller.getAllCourses(mockContext);

        // ASSERT
        try {
            verify(mockContext).queryParamMap();
            OK("Query params extracted from context", false);

            verify(mockService).getAllCourses(any(Map.class));
            OK("Service called with query params", false);

            verify(mockContext).json(mockCourses);
            OK("Response returned with " + mockCourses.size() + " courses");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get all courses should pass query parameters to service")
    void testGetAllCoursesWithQueryParameters() {
        // ARRANGE
        Map<String, List<String>> queryParamMap = new HashMap<>();
        queryParamMap.put("session", Arrays.asList("A2025"));

        List<Course> mockCourses = Arrays.asList(
                new Course("IFT1015", "Programmation I"));

        when(mockContext.queryParamMap()).thenReturn(queryParamMap);
        when(mockService.getAllCourses(any())).thenReturn(mockCourses);

        // ACT
        controller.getAllCourses(mockContext);

        // ASSERT
        try {
            verify(mockService).getAllCourses(argThat(params -> 
                    params.containsKey("session") &&
                    params.get("session").equals("A2025")));
            OK("Service called with correct query parameters", false);

            verify(mockContext).json(mockCourses);
            OK("Response returned successfully");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    /**************************************************************************
     * Tests for getCourseById method
     *************************************************************************/

    @Test
    @DisplayName("Get course by ID should return course when ID exists")
    void testGetCourseByIdWhenIdExists() {
        // ARRANGE
        String courseId = "IFT2255";
        Course mockCourse = new Course(courseId, "Génie logiciel");

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockService.getCourseById(courseId)).thenReturn(Optional.of(mockCourse));

        // ACT
        controller.getCourseById(mockContext);

        // ASSERT
        try {
            verify(mockContext).pathParam("id");
            OK("Path parameter 'id' extracted", false);

            verify(mockService).getCourseById(courseId);
            OK("Service called with ID: " + courseId, false);

            verify(mockContext).json(mockCourse);
            OK("Course returned successfully", false);

            verify(mockContext, never()).status(anyInt());
            OK("No error status set");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course by ID should return 404 when course not found")
    void testGetCourseByIdWhenCourseNotFound() {
        // ARRANGE
        String courseId = "IFT1234";

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockService.getCourseById(courseId)).thenReturn(Optional.empty());
        when(mockContext.status(404)).thenReturn(mockContext);

        // ACT
        controller.getCourseById(mockContext);

        // ASSERT
        try {
            verify(mockService).getCourseById(courseId);
            OK("Service called with ID: " + courseId, false);

            verify(mockContext).status(404);
            OK("Status 404 set", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Error message returned");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course by ID should return 400 when ID is null")
    void testGetCourseByIdWhenIdIsNull() {
        // ARRANGE
        when(mockContext.pathParam("id")).thenReturn(null);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.getCourseById(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Status 400 set", false);

            verify(mockContext).json(argThat(map -> map instanceof Map &&
                    ((Map<?, ?>) map).containsKey("error")));
            OK("Error message returned", false);

            verify(mockService, never()).getCourseById(any());
            OK("Service was not called");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("Get course by ID should return 400 when ID is empty string")
    void testGetCourseByIdWhenIdIsEmpty() {
        // ARRANGE
        when(mockContext.pathParam("id")).thenReturn("");
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.getCourseById(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Status 400 set for empty ID", false);

            verify(mockService, never()).getCourseById(any());
            OK("Service was not called");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }


    /**************************************************************************
     * Tests for searchCourse method
     *************************************************************************/

    /// Test pour recherche vide
    @Test
    @DisplayName("Empty search should return all courses matching student's program")
    void testEmptySearch() {
        // ARRANGE
        // Base de données simulées
        List<Course> mockCourses = Arrays.asList(
                new Course("IFT1015", "Programmation I"),
                new Course("IFT1025", "Programmation II"),
                new Course("ESP3900", "Espagnol Intermédiaire")); 

        RechercheCours mockRecherche = new RechercheCours();
        Etudiant mockEtudiant = new Etudiant(12345, "Jean Dupont", "jean@hotmail.com");

        // On crée une recherche avec un mot vide
        String motRechercheTest = "";

        // On configure le contrôleur avec un utilisateur simulé
        controller.setUser(mockEtudiant);

        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockContext.pathParam("recherche")).thenReturn(motRechercheTest);
        when(mockService.getAllCourses(any())).thenReturn(mockCourses);

        // ACT
        // On appelle searchCourses sans paramètres de requête
        controller.searchCourses(mockContext);

        // ASSERT
        try {
            // On verifie que la réponse contient tous les cours
            verify(mockContext).json(argThat(courses -> 
                courses instanceof List &&
                ((List<?>) courses).size() == 3));
            OK("Every course returned for empty search");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    } 

    // Test pour recherche avec mot-clé invalide
    @Test
    @DisplayName("Invalid seache searh should return error message")
    void testInvalidSearch() {
        // ARRANGE
        // Base de données simulées
        List<Course> mockCourses = Arrays.asList(
                new Course("IFT1015", "Programmation I"),
                new Course("IFT1025", "Programmation II"),
                new Course("ESP3900", "Espagnol Intermédiaire")); 

        RechercheCours mockRecherche = new RechercheCours();
        Etudiant mockEtudiant = new Etudiant(12345, "Jean Dupont", "jean@hotmail.com");

        // On crée une recherche avec un mot vide
        String motRechercheTest = "cours inexistant";

        // On configure le contrôleur avec un utilisateur simulé
        controller.setUser(mockEtudiant);

        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockContext.pathParam("recherche")).thenReturn(motRechercheTest);
        when(mockService.getAllCourses(any())).thenReturn(mockCourses);
        when(mockContext.status(anyInt())).thenReturn(mockContext);

        // ACT
        // On appelle searchCourses sans paramètres de requête
        controller.searchCourses(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(404);
            OK("Test failed successfully, received 404 with error message.");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    } 

    // Test pour une recherche normale (succès)
    @Test
    @DisplayName("SearchCourses with normal search term should return matching courses")
    void testSearchNormalTerms() {
        // ARRANGE
        // Base de données simulées
        List<Course> mockCourses = Arrays.asList(
                new Course("IFT1015", "Programmation I"),
                new Course("IFT1025", "Programmation II"),
                new Course("ESP3900", "Espagnol Intermédiaire")); 

        User mockUser = new User(12345, "Jean Dupont", "jean@hotmail.com");
        // On crée une recherche avec mot-clé
        String motRechercheTest = "Programmation";
        
        // On configure le contrôleur avec un utilisateur simulé
        controller.setUser(mockUser);

        when(mockContext.queryParamMap()).thenReturn(new HashMap<>());
        when(mockService.getAllCourses(any())).thenReturn(mockCourses);
        when(mockContext.pathParam("recherche")).thenReturn(motRechercheTest);

        // ACT
        // On appelle searchCourses sans paramètres de requête
        controller.searchCourses(mockContext);

        // ASSERT
        try {
            // On verifie que la réponse contient tous les cours
            verify(mockContext).json(argThat(courses -> 
                courses instanceof List &&
                ((List<?>) courses).size() == 2));
            OK("Only courses matching search returned");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: CourseController Tests");
        System.out.println("=".repeat(80) + "\n");
    }

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

    private void Err(String message, boolean isLast) {
        printMessage(message, false, isLast);
    }

}
