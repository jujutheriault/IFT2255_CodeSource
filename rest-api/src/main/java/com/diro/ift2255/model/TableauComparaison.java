package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TableauComparaison {

    private Course[] cours;
    private int taille;
    /**
     * Constructuer d'un tableau de comparaison
     */
    public TableauComparaison() {
        this.cours = new Course[10]; // capacité par défaut
        this.taille = 0;
    }
    /**
     * Constructeur d'un tableau de comparaison avec parametres
     * @param capacite capacite du tableau 
     */
    public TableauComparaison(int capacite) {
        this.cours = new Course[capacite];
        this.taille = 0;
    }

    // --- Getter ---
    /**
     * Getter pour les cours du tableau de comparasion
     * @return un cours du tableau pour comparer
     */
    public Course[] getCours() {
        return cours;
    }
    /**
     * Setter pour les cours du tableau de comparaison
     * @param cours un cours a modifier dans le tableau 
     */
    public void setCours(Course[] cours) {
        this.cours = cours;
        this.taille = (cours == null) ? 0 : cours.length;
    }

    // Ajouter un cours
    /**
     * Ajouter un cours au tableau de comparaison
     * @param c un cours a ajouter au tableau
     */
    public void ajouterCours(Course c) {
        if (taille >= cours.length) {
            agrandirTableau();
        }
        cours[taille] = c;
        taille++;
    }
    /**
     * Agrandissement du tableau de comparaison
     * 
     */
    private void agrandirTableau() {
        Course[] nouveau = new Course[cours.length * 2];
        for (int i = 0; i < cours.length; i++) {
            nouveau[i] = cours[i];
        }
        cours = nouveau;
    }

    // Calculer la charge totale (somme des crédits)
    /**
     * Calcul de la charge totale par le nombre de credits des cours du tableau
     * @return la charge totale par le nombre de credits des cours dans le tableau
     */
    public int calculChargeTotale() {
        int total = 0;
        for (int i = 0; i < taille; i++) {
            if (cours[i] != null) {
                total += cours[i].getCredits();
            }
        }
        return total;
    }
    /**
     * Getter pour la taille du tableau
     * @return la taille du tableau de comparaison
     */
    public int getTaille() {
        return taille;
    }
}
