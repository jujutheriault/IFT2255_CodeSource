package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeAll;

class ComparaisonControllerTest {

    private ComparaisonController controller;
    private Course c1;
    private Course c2;

    @BeforeAll
    static void printHeader() {
        System.out.println("\n" + "=".repeat(80));
        System.out.println("ComparaisonController Tests");
        System.out.println("=".repeat(80));
    }

    @BeforeEach
    public void setUp() {
        controller = new ComparaisonController();

        c1 = new Course("C101", "Math");
        c1.setCredits(3); // définit les crédits

        c2 = new Course("C102", "Physique");
        c2.setCredits(4); // définit les crédits
    }

    @Test
    void testSelectionnerCoursComparer() {
        controller.selectionnerCoursComparer(c1);
        Course[] cours = controller.getCoursComparés();

        assertEquals("C101", cours[0].getId());
        assertEquals(3, cours[0].getCredits());
    }

    @Test
    void testDeselectionnerCoursComparer() {
        controller.selectionnerCoursComparer(c1);
        controller.selectionnerCoursComparer(c2);

        controller.deselectionnerCoursComparer("C101");
        Course[] cours = controller.getCoursComparés();

        // Après suppression de C101, C102 est à l'index 0 et l'index 1 est null
        assertEquals("C102", cours[0].getId());
        assertNull(cours[1]);
    }

    @Test
    void testComparerCours() {
        Course[] selection = {c1, c2};
        controller.comparerCours(selection);
        Course[] cours = controller.getCoursComparés();

        assertEquals("C101", cours[0].getId());
        assertEquals("C102", cours[1].getId());
    }

    @Test
    void testCalculerChargeTotale() {
        controller.selectionnerCoursComparer(c1);
        controller.selectionnerCoursComparer(c2);

        int total = controller.calculerChargeTotale();
        assertEquals(7, total); // 3 + 4
    }

    @Test
    void testReinitialiserSelection() {
        controller.selectionnerCoursComparer(c1);
        controller.reinitialiserSelection();

        Course[] cours = controller.getCoursComparés();
        assertNull(cours[0]);
    }
}
