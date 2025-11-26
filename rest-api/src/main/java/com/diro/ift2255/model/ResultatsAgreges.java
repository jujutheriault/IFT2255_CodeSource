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
    public ResultatsAgreges() {}
    
    // Constructeur avec Course
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
    public Course getCours() {
        return cours;
    }
    
    public void setCours(Course cours) {
        this.cours = cours;
    }
    
    public double getMoyenne() {
        return moyenne;
    }
    
    public void setMoyenne(double moyenne) {
        this.moyenne = moyenne;
    }
    
    public int getNombreInscrits() {
        return nombreInscrits;
    }
    
    public void setNombreInscrits(int nombreInscrits) {
        this.nombreInscrits = nombreInscrits;
    }
    
    public int getNombreEchecs() {
        return nombreEchecs;
    }
    
    public void setNombreEchecs(int nombreEchecs) {
        this.nombreEchecs = nombreEchecs;
    }
    
    public String getTrimestre() {
        return trimestre;
    }
    
    public void setTrimestre(String trimestre) {
        this.trimestre = trimestre;
    }
    
    public int getAnnee() {
        return annee;
    }
    
    public void setAnnee(int annee) {
        this.annee = annee;
    }
}
