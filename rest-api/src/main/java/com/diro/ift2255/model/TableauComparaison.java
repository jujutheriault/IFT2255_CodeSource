package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableauComparaison {

    private Cours[] cours;
    private int taille;

    public TableauComparaison() {
        this.cours = new Cours[10]; // capacité par défaut
        this.taille = 0;
    }

    public TableauComparaison(int capacite) {
        this.cours = new Cours[capacite];
        this.taille = 0;
    }

    // --- Getter ---
    public Cours[] getCours() {
        return cours;
    }

    public void setCours(Cours[] cours) {
        this.cours = cours;
        this.taille = cours.length;
    }

    //Ajouter un cours
    public void ajouterCours(Cours c) {
        if (taille >= cours.length) {
            agrandirTableau();
        }
        cours[taille] = c;
        taille++;
    }

    private void agrandirTableau() {
        Cours[] nouveau = new Cours[cours.length * 2];
        for (int i = 0; i < cours.length; i++) {
            nouveau[i] = cours[i];
        }
        cours = nouveau;
    }

    //Calculer la charge totale
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

