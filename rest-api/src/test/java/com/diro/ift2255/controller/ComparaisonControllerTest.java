package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ComparaisonControllerTest {

    private ComparaisonController controller;

    private Course c1;
    private Course c2;
    private long testStartTime;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ComparaisonController Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    void setUp(TestInfo testInfo) {
        testStartTime = System.currentTimeMillis();

        System.out.println("\nTEST: " + testInfo.getDisplayName());
        System.out.println("    ├─ Method: " + testInfo.getTestMethod().get().getName());
        System.out.println("    ├─ Assertions:");

        controller = new ComparaisonController();

        c1 = new Course("IFT-1015", "Programmation 1");
        c1.setCredits(3);

        c2 = new Course("MAT-1600", "Algèbre linéaire");
        c2.setCredits(4);
    }

    @AfterEach
    void tearDown() {
        long duration = System.currentTimeMillis() - testStartTime;
        System.out.println("    └─ Duration: " + duration + " ms");
    }

    @AfterAll
    static void printFooter() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("COMPLETED: ComparaisonController Tests");
        System.out.println("=".repeat(80) + "\n");
    }

    @Test
    @DisplayName("selectionnerCoursComparer should store first course in position 0")
    void testSelectionnerCoursComparer() {
        //ACT
        controller.selectionnerCoursComparer(c1);
        Course[] cours = controller.getCoursComparer();

        // ASSERT + LOG
        try {
            assertNotNull(cours[0], "First slot should not be null after selection");
            OK("First slot is not null", false);

            assertSame(c1, cours[0], "First slot should reference c1");
            OK("First slot references the expected course (c1)");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("deselectionnerCoursComparer should remove matching course by id")
    void testDeselectionnerCoursComparer() {
        // ARRANGE
        controller.selectionnerCoursComparer(c1);
        controller.selectionnerCoursComparer(c2);

        // ACT
        controller.deselectionnerCoursComparer("IFT-1015");
        Course[] cours = controller.getCoursComparer();

        // Après suppression de C101, C102 est à l'index 0 et l'index 1 est null
        try {
            // On vérifie le contenu
            assertNotNull(cours[0], "After removing IFT1015, first slot should not be null");
            OK("First slot is not null after deselection", false);
    
            assertEquals("MAT-1600", cours[0].getId(),
                "After removing IFT-1015, MAT-1600 should move to index 0");
            OK("Remaining course at index 0 has id MAT-1600", false);
    
            assertNull(cours[1], "Second slot should be null after deselection");
            OK("Second slot is null as expected");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test    
    @DisplayName("comparerCours should copy selection into internal array")
    void testComparerCours() {
        // ARRANGE
        Course[] selection = {c1, c2};

        // ACT
        controller.comparerCours(selection);
        Course[] cours = controller.getCoursComparer();

        try {
            assertSame(c1, cours[0], "First compared course should be c1");
            OK("First compared course is c1", false);

            assertSame(c2, cours[1], "Second compared course should be c2");
            OK("Second compared course is c2");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("calculerChargeTotale should sum credits of selected courses")
    void testCalculerChargeTotale() {
        // ARRANGE
        controller.selectionnerCoursComparer(c1);
        controller.selectionnerCoursComparer(c2);

        // ACT
        int total = controller.calculerChargeTotale();

        // ASSERT + LOG
        try {
            assertEquals(7, total, "Total credits should be 3 + 4 = 7");
            OK("Total credits correctly calculated as 7");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
    }

    @Test
    @DisplayName("reinitialiserSelection should clear the selected compared courses")
    void testReinitialiserSelection() {
        // ARRANGE
        controller.selectionnerCoursComparer(c1);

        // ACT
        controller.reinitialiserSelection();
        Course[] cours = controller.getCoursComparer();
        
        // ASSERT + LOG
        try {
            assertNull(cours[0], "First slot should be null after reset");
            OK("Selection correctly reset (first slot is null)");
        } catch (AssertionError e) {
            Err(e.getMessage());
            throw e;
        }
        
    
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
