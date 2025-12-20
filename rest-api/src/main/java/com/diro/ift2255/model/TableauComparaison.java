package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableauComparaison {

    private Course[] cours;
    private int taille;

    public TableauComparaison() {
        this.cours = new Course[10]; // capacité par défaut
        this.taille = 0;
    }

    public TableauComparaison(int capacite) {
        this.cours = new Course[capacite];
        this.taille = 0;
    }

    // --- Getter ---
    public Course[] getCours() {
        return cours;
    }

    public void setCours(Course[] cours) {
        this.cours = cours;
        this.taille = (cours == null) ? 0 : cours.length;
    }

    // Ajouter un cours
    public void ajouterCours(Course c) {
        if (taille >= cours.length) {
            agrandirTableau();
        }
        cours[taille] = c;
        taille++;
    }

    private void agrandirTableau() {
        Course[] nouveau = new Course[cours.length * 2];
        for (int i = 0; i < cours.length; i++) {
            nouveau[i] = cours[i];
        }
        cours = nouveau;
    }

    // Calculer la charge totale (somme des crédits)
    public int calculChargeTotale() {
        int total = 0;
        for (int i = 0; i < taille; i++) {
            if (cours[i] != null) {
                total += cours[i].getCredits();
            }
        }
        return total;
    }

    public int getTaille() {
        return taille;
    }
}
