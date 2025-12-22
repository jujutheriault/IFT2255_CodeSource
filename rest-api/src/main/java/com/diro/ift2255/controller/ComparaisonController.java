package com.diro.ift2255.controller;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.TableauComparaison;
import java.util.*;

public class ComparaisonController {

    private TableauComparaison tableau;

    public ComparaisonController() {
        this.tableau = new TableauComparaison();
    }

    // Ajoute un cours si non null
    public void selectionnerCoursComparer(Course cours) {
        if (cours == null) return;
        tableau.ajouterCours(cours);
    }

    // Retire un cours par ID (décalage du tableau)
    public void deselectionnerCoursComparer(String courseId) {
        if (courseId == null) return;

        Course[] liste = tableau.getCours();
        int taille = tableau.getTaille();

        for (int i = 0; i < taille; i++) {
            if (liste[i] != null && courseId.equals(liste[i].getId())) {
                for (int j = i; j < taille - 1; j++) {
                    liste[j] = liste[j + 1];
                }
                liste[taille - 1] = null;
                return;
            }
        }
    }

    // Ajoute plusieurs cours à la fois
    public void comparerCours(Course[] coursSelectionnes) {
        if (coursSelectionnes == null) return;

        for (Course c : coursSelectionnes) {
            if (c != null) tableau.ajouterCours(c);
        }
    }

    // Retourne la somme des crédits
    public int calculerChargeTotale() {
        return tableau.calculChargeTotale();
    }

    // Réinitialise le tableau de comparaison
    public void reinitialiserSelection() {
        tableau = new TableauComparaison();
    }

    // Retourne les cours sélectionnés
    public Course[] getCoursComparer() {
        return tableau.getCours();
    }

    // ✅ NOUVELLE MÉTHODE : Génère un résultat de comparaison structuré
    public Map<String, Object> genererComparaison() {
        Map<String, Object> result = new HashMap<>();
        
        Course[] coursListe = tableau.getCours();
        int taille = tableau.getTaille();
        
        List<Map<String, Object>> comparaison = new ArrayList<>();
        
        for (int i = 0; i < taille; i++) {
            if (coursListe[i] != null) {
                comparaison.add(formatCourseForComparison(coursListe[i]));
            }
        }
        
        result.put("courses", comparaison);
        result.put("totalCredits", calculerChargeTotale());
        result.put("count", taille);
        
        return result;
    }

    // Formate un cours pour la comparaison
    private Map<String, Object> formatCourseForComparison(Course c) {
        Map<String, Object> data = new HashMap<>();
        data.put("id", c.getId());
        data.put("name", c.getName());
        data.put("credits", c.getCredits());
        data.put("description", c.getDescription() != null ? c.getDescription() : "N/A");
        data.put("prerequisite_courses", c.getPrerequis() != null ? c.getPrerequis() : new ArrayList<>());
        data.put("concomitant_courses", c.getCorequis() != null ? c.getCorequis() : new ArrayList<>());
        data.put("available_terms", c.getTerms() != null ? c.getTerms() : new HashMap<>());
        return data;
    }

    public int getTaille() {
        return tableau.getTaille();
    }
}