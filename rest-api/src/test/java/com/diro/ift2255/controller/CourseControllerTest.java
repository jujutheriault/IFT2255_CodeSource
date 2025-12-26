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
import static org.mockito.Mockito.mock;
import static org.mockito.ArgumentMatchers.anySet;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.Etudiant;
import com.diro.ift2255.model.RechercheCours;
import com.diro.ift2255.model.User;
import com.diro.ift2255.service.CourseService;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    
    /**************************************************************************
     * Tests for checkEligibility method
     *************************************************************************/

    @Test
    @DisplayName("checkEligibility should return eligible=true when no prerequisites are missing")
    void testCheckEligibilityEligible() {
        // ARRANGE
        String courseId = "IFT2255";

        Course mockCourse = mock(Course.class);

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.queryParam("completed")).thenReturn("ESP1900,IFT1025");

        // Le cours n'a aucun prérequis manquant selon la logique du modèle
        when(mockCourse.missingPrerequisites(anySet())).thenReturn(List.of());

        // Optionnel: champs bonus ajoutés dans la réponse
        when(mockCourse.getPrerequisiteCourses()).thenReturn(List.of("IFT1025"));
        when(mockCourse.getRequirementText()).thenReturn("Avoir complété IFT1025");

        when(mockService.getCourseById(courseId)).thenReturn(Optional.of(mockCourse));

        // ACT
        controller.checkEligibility(mockContext);

        // ASSERT
        try {
            verify(mockService).getCourseById(courseId);
            OK("Service called with courseId: " + courseId, false);

            verify(mockCourse).missingPrerequisites(anySet());
            OK("Model missingPrerequisites called", false);

            verify(mockContext).json(argThat(obj ->
                obj instanceof Map &&
                Boolean.TRUE.equals(((Map<?, ?>) obj).get("eligible")) &&
                ((Map<?, ?>) obj).get("course_id").equals(courseId) &&
                ((List<?>) ((Map<?, ?>) obj).get("missing_prerequisites")).isEmpty()
            ));
            OK("Returned eligible=true with empty missing list");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("checkEligibility should return eligible=false and missing prerequisites when prerequisites are missing")
    void testCheckEligibilityNotEligible() {
        // ARRANGE
        String courseId = "IFT2255";
        Course mockCourse = mock(Course.class);

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.queryParam("completed")).thenReturn("ESP1900"); // cours complétés

        when(mockCourse.missingPrerequisites(anySet())).thenReturn(List.of("IFT1025", "MAT1978"));

        when(mockCourse.getPrerequisiteCourses()).thenReturn(List.of("IFT1025", "MAT1978"));
        when(mockCourse.getRequirementText()).thenReturn("Avoir complété IFT1025 et MAT1978");

        when(mockService.getCourseById(courseId)).thenReturn(Optional.of(mockCourse));

        // ACT
        controller.checkEligibility(mockContext);

        // ASSERT
        try {
            verify(mockService).getCourseById(courseId);
            OK("Service called with courseId: " + courseId, false);

            verify(mockCourse).missingPrerequisites(anySet());
            OK("Model missingPrerequisites called", false);

            verify(mockContext).json(argThat(obj ->
                obj instanceof Map &&
                Boolean.FALSE.equals(((Map<?, ?>) obj).get("eligible")) &&
                ((Map<?, ?>) obj).get("course_id").equals(courseId) &&
                ((List<?>) ((Map<?, ?>) obj).get("missing_prerequisites")).size() == 2
            ));
            OK("Returned eligible=false with 2 missing prerequisites");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("checkEligibility should return 404 when course is not found")
    void testCheckEligibilityCourseNotFound() {
        // ARRANGE
        String courseId = "IFT9999";

        when(mockContext.pathParam("id")).thenReturn(courseId);
        when(mockContext.queryParam("completed")).thenReturn("IFT1025");
        when(mockService.getCourseById(courseId)).thenReturn(Optional.empty());
        when(mockContext.status(404)).thenReturn(mockContext);

        // ACT
        controller.checkEligibility(mockContext);

        // ASSERT
        try {
            verify(mockService).getCourseById(courseId);
            OK("Service called with courseId: " + courseId, false);

            verify(mockContext).status(404);
            OK("Status 404 set", false);

            verify(mockContext).json(argThat(map ->
                map instanceof Map &&
                ((Map<?, ?>) map).containsKey("error")
            ));
            OK("Error response returned for course not found");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }

        verify(mockContext, never()).json(argThat(obj ->
            obj instanceof Map && ((Map<?, ?>) obj).containsKey("eligible")
        ));
    }

    /**************************************************************************
     * Tests for getCourseByProgram method
     *************************************************************************/

    @Test
    @DisplayName("getCoursesByProgram should return 200 and program JSON when programs_list is valid")
    void testGetCoursesByProgram_Success() {
        // ARRANGE
        String programId = "117510";
        String responseLevel = "reg";

        when(mockContext.queryParam("programs_list")).thenReturn(programId);
        when(mockContext.queryParam("response_level")).thenReturn(responseLevel);
        when(mockContext.queryParam("include_courses_detail")).thenReturn("true");

        // JsonNode fake (simule la réponse Planifium déjà parsée)
        ObjectMapper mapper = new ObjectMapper();
        JsonNode programData;
        try {
            programData = mapper.readTree("""
                [
                {
                    "id": "117510",
                    "name": "Baccalauréat en informatique"
                }
                ]
            """);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        when(mockService.getProgram(programId, true, responseLevel)).thenReturn(programData);
        when(mockContext.status(200)).thenReturn(mockContext);

        // ACT
        controller.getCoursesByProgram(mockContext);

        // ASSERT
        try {
            verify(mockService).getProgram(programId, true, responseLevel);
            OK("Service getProgram called with correct parameters", false);

            verify(mockContext).status(200);
            OK("Status 200 set", false);

            verify(mockContext).json(programData);
            OK("Program JSON returned successfully");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("getCoursesByProgram should return 400 when programs_list is missing")
    void testGetCoursesByProgram_FailureMissingProgramId() {
        // ARRANGE
        when(mockContext.queryParam("programs_list")).thenReturn(null);
        when(mockContext.status(400)).thenReturn(mockContext);

        // ACT
        controller.getCoursesByProgram(mockContext);

        // ASSERT
        try {
            verify(mockContext).status(400);
            OK("Status 400 set when programs_list is missing", false);

            verify(mockContext).json(argThat(map ->
                map instanceof Map && ((Map<?, ?>) map).containsKey("error")
            ));
            OK("Error message returned", false);
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }


    /**************************************************************************
     * Tests for getCourseByProgramAndSemester method
     *************************************************************************/
    @Test
    @DisplayName("getCoursesByProgramAndSemester should return 200 and course list when program and semester are valid")
    void testGetCoursesByProgramAndSemester_Success() {
        // ARRANGE
        String programId = "117510";
        String semesterRaw = "h25";
        String semesterNormalized = "h25";

        when(mockContext.queryParam("programs_list")).thenReturn(programId);
        when(mockContext.pathParam("semester")).thenReturn(semesterRaw);

        when(mockService.normalizeSemester(semesterRaw)).thenReturn(semesterNormalized);

        List<Course> mockCourses = Arrays.asList(
            new Course("IFT2255", "Génie logiciel"),
            new Course("IFT2015", "Structures de données")
        );

        when(mockService.getCoursesByProgramAndSemester(programId, semesterNormalized)).thenReturn(mockCourses);
        when(mockContext.status(200)).thenReturn(mockContext);

        // ACT
        controller.getCoursesByProgramAndSemester(mockContext);

        // ASSERT
        try {
            verify(mockService).normalizeSemester(semesterRaw);
            OK("Semester normalized correctly", false);

            verify(mockService).getCoursesByProgramAndSemester(programId, semesterNormalized);
            OK("Service called with correct programId and semester", false);

            verify(mockContext).status(200);
            OK("Status 200 set", false);

            verify(mockContext).json(mockCourses);
            OK("Course list returned successfully");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("getCoursesByProgramAndSemester should return 404 when no courses are offered for the given semester")
    void testGetCoursesByProgramAndSemester_FailureNoCoursesOffered() {
        // ARRANGE
        String programId = "117510";
        String semesterRaw = "h25";
        String semesterNormalized = "h25";

        when(mockContext.queryParam("programs_list")).thenReturn(programId);
        when(mockContext.pathParam("semester")).thenReturn(semesterRaw);

        when(mockService.normalizeSemester(semesterRaw)).thenReturn(semesterNormalized);

        // Aucun cours offert
        when(mockService.getCoursesByProgramAndSemester(programId, semesterNormalized))
            .thenReturn(List.of());

        when(mockContext.status(404)).thenReturn(mockContext);

        // ACT
        controller.getCoursesByProgramAndSemester(mockContext);

        // ASSERT
        try {
            verify(mockService).normalizeSemester(semesterRaw);
            OK("Semester normalized correctly", false);

            verify(mockService).getCoursesByProgramAndSemester(programId, semesterNormalized);
            OK("Service called and returned empty list", false);

            verify(mockContext).status(404);
            OK("Status 404 set when no course is offered", false);

            verify(mockContext).json(argThat(map ->
                map instanceof Map &&
                ((Map<?, ?>) map).containsKey("error")
            ));
            OK("Error message returned");
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
