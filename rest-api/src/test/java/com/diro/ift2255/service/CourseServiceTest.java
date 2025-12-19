package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private HttpClientApi clientApi;

    @InjectMocks
    private CourseService service;

    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("CourseService Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setup(TestInfo testInfo) {
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

    @Test
    @DisplayName("getAllCourses should return a list from API.")
    void getAllCourses_returnList() {

        Map<String, String> params = Map.of("course_id", "IFT2255");

        Course testCourse1 = mock(Course.class);
        Course testCourse2 = mock(Course.class);

        List<Course> expected = List.of(testCourse1, testCourse2);

        when(clientApi.get(
            any(URI.class),
            ArgumentMatchers.<TypeReference<List<Course>>>any()
        )).thenReturn(expected);

        // ACT
        List<Course> result = service.getAllCourses(params);

        // ASSERT + LOG
        try {
            assertEquals(2, result.size(), "Should return a list of 2 courses.");
            OK("Returned : " + result.size() + " courses as expected.");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }

        verify(clientApi, times(1))
            .get(any(URI.class), any(TypeReference.class));
    }

    @Test
    @DisplayName("getAllCourses with Null parameters should use empty map and return an empty list. ")
    void getAllCourses_withNullParams () {

        // ARRANGE
        List<Course> expected = Collections.emptyList();

        when(clientApi.get(
            any(URI.class), ArgumentMatchers.<TypeReference<List<Course>>>any()
        )).thenReturn(expected);

        // ACT
        List<Course> result = service.getAllCourses(null);

        // ASSERT + LOG
        try {
            assertTrue(result.isEmpty(), "Result list should be empty");
            OK("Returned empty list as expected");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }

        verify(clientApi, times(1))
            .get(any(URI.class), any(TypeReference.class));
    } 

    @Test
    @DisplayName("getCourseById should return course if API succeeds.")
    void getCourseById_ReturnCourse() {

        // ARRANGE
        Course expected = mock(Course.class);

        when(clientApi.get(
            any(URI.class),
            eq(Course.class)
        )).thenReturn(expected);

        // ACT
        Optional<Course> result = service.getCourseById("IFT-2255");

        // ASSERT + LOG
        try {
            assertTrue(result.isPresent(), "Course should be present");
            OK("Course found for id IFT-2255", false);

            assertEquals(expected, result.get(), "Returned course should match mock");
            OK("Returned course matches expected mock");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }

        verify(clientApi, times(1))
            .get(any(URI.class), eq(Course.class));
    }

    @Test
    @DisplayName("getCourseById should return empty if API throws.")
    void getCourseById_Empty() {

        // ARRANGE
        when(clientApi.get(
            any(URI.class),
            eq(Course.class)
        )).thenThrow(new RuntimeException("Not found"));

        // ACT
        Optional<Course> result = service.getCourseById("MAT-1400");

        // ASSERT + LOG
        try {
            assertTrue(result.isEmpty(), "Result should be empty when API throws.");
            OK("Empty optional returned as expected when API throws.");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
        verify(clientApi, times(1)).get(
            any(URI.class),
            eq(Course.class));
    }

    // ------------------------------------------------------------------------
    // Helper methods for logging
    // ------------------------------------------------------------------------
    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: CourseService Tests");
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
