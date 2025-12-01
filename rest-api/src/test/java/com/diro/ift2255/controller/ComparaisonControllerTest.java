package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.TableauComparaison;

public class ComparaisonController {

    private TableauComparaison tableau;

    public ComparaisonController() {
        this.tableau = new TableauComparaison();
    }

    // --- 1) Ajouter un cours à la comparaison ---
    public void selectionnerCoursComparer(Course cours) {
        if (cours == null) return;
        tableau.ajouterCours(cours);
    }

    // --- 2) Retirer un cours de la comparaison ---
    public void deselectionnerCoursComparer(String courseId) {
        if (courseId == null) return;

        Course[] liste = tableau.getCours();
        int taille = tableau.getTaille();

        for (int i = 0; i < taille; i++) {
            if (liste[i] != null && courseId.equals(liste[i].getId())) {

                // Décalage vers la gauche pour supprimer l’élément
                for (int j = i; j < taille - 1; j++) {
                    liste[j] = liste[j + 1];
                }
                liste[taille - 1] = null; // dernier élément devient null
                return;
            }
        }
    }

    // --- 3) Comparer plusieurs cours en les ajoutant au tableau ---
    public void comparerCours(Course[] coursSelectionnes) {
        if (coursSelectionnes == null) return;

        for (Course c : coursSelectionnes) {
            if (c != null) {
                tableau.ajouterCours(c);
            }
        }
    }

    // --- 4) Calcul de la charge totale (somme des crédits) ---
    public int calculerChargeTotale() {
        return tableau.calculChargeTotale();
    }

    // --- 5) Réinitialiser le tableau de comparaison ---
    public void reinitialiserSelection() {
        tableau = new TableauComparaison(); // Remplacement direct
    }

    // --- Getter pour la vue ---
    public Course[] getCoursComparés() {
        return tableau.getCours();
    }
}

