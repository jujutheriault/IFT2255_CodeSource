package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ResultatsAgreges {

    private Course cours;           // Référence vers l'objet Course

    // Attributs spécifiques aux résultats
    private double moyenne;
    private int nombreInscrits;
    private int nombreEchecs;
    private String trimestre;
    private int annee;

    // Constructeur par défaut
    /**
     * Constructeur par defaut pour les resultats agreges
     */
    public ResultatsAgreges() {}

    // Constructeur complet
    /**
     * Constructeur des resultats agreges avec parametres
     * @param cours le cours pour lequel les resultats agreges sont publies
     * @param moyenne la moyenne du cours
     * @param nombreInscrits le nombre d'etudiants inscrits au cours
     * @param nombreEchecs le nombre d'echecs au cours
     * @param trimestre le trimestre ou le cours a eu lieu
     * @param annee l'anne ou le cours a eu lieu
     */
    public ResultatsAgreges(Course cours, double moyenne, int nombreInscrits,
                           int nombreEchecs, String trimestre, int annee) {
        this.cours = cours;
        this.moyenne = moyenne;
        this.nombreInscrits = nombreInscrits;
        this.nombreEchecs = nombreEchecs;
        this.trimestre = trimestre;
        this.annee = annee;
    }

    // Getters et Setters
    /**
     * Getter pour le cours
     * @return le cours dont on veut obtenir les resultats
     */
    public Course getCours() {
        return cours;
    }
    /**
     * Setter pour le cours
     * @param cours cours modifie
     */
    public void setCours(Course cours) {
        this.cours = cours;
    }
    /**
     * Getter pour la moyenne d'un cours
     * @return la moyenne d'un cours
     */
    public double getMoyenne() {
        return moyenne;
    }
    /**
     * Setter pour la moyenne d'un cours
     * @param moyenne la moyenne d'un cours modifiee
     */
    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }
    /**
     * Getter pour le nombre d'inscrits a un cours
     * @return le nombre d'inscrits a un cours donne
     */
    public int getNombreInscrits() {
        return nombreInscrits;
    }
    /**
     * Setter pour le nombre d'inscrits a un cours
     * @param nombreInscrits le nombre d'inscits a un cours modifie
     */
    public void setNombreInscrits(int nombreInscrits) {
        this.nombreInscrits = nombreInscrits;
    }
    /**
     * Getter pour le nombre d'echec a un cours
     * @return le nombre d'echecs dans un cours
     */
    public int getNombreEchecs() {
        return nombreEchecs;
    }
    /**
     * Setter pour le nombre d'echecs a un cours 
     * @param nombreEchecs le nombre d'echecs modifie pour un cours
     */
    public void setNombreEchecs(int nombreEchecs) {
        this.nombreEchecs = nombreEchecs;
    }
    /**
     * Getter pour le trimestre ou un cours a eu lieu
     * @return le trimestre ou le cours a eu lieu
     */
    public String getTrimestre() {
        return trimestre;
    }
    /**
     * Setter pour le trimestre ou un cours a eu lieu
     * @param trimestre le trimestre modofie ou un cours a eu lieu
     */
    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
    /**
     * Getter pour l'anne ou un cours a eu lieu
     * @return l'annee ou le cours a eu lieu
     */
    public int getAnnee() {
        return annee;
    }
    /**
     * Setter poue l'annee ou un cours a eu lieu
     * @param annee l,annee mofifiee ou le cours a eu lieu
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }
}
