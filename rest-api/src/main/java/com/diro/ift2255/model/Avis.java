package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Avis {
    private String session;
    private String trimestre;
    private int annee;
    private int nivDifficulte;
    private int volumeTravail;
    private String professeur;
    private int nombreAvis;
    
    // Constructeur par défaut
    public Avis() {}
    
    // Constructeur avec paramètres
    public Avis(String session, String trimestre, int annee, int nivDifficulte,
                int volumeTravail, String professeur, int nombreAvis) {
        this.session = session;
        this.trimestre = trimestre;
        this.annee = annee;
        this.nivDifficulte = nivDifficulte;
        this.volumeTravail = volumeTravail;
        this.professeur = professeur;
        this.nombreAvis = nombreAvis;
    }
    
    // Getters et Setters
    public String getSession() {
        return session;
    }
    
    public void setSession(String session) {
        this.session = session;
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
    
    public int getNivDifficulte() {
        return nivDifficulte;
    }
    
    public void setNivDifficulte(int nivDifficulte) {
        this.nivDifficulte = nivDifficulte;
    }
    
    public int getVolumeTravail() {
        return volumeTravail;
    }
    
    public void setVolumeTravail(int volumeTravail) {
        this.volumeTravail = volumeTravail;
    }
    
    public String getProfesseur() {
        return professeur;
    }
    
    public void setProfesseur(String professeur) {
        this.professeur = professeur;
    }
    
    public int getNombreAvis() {
        return nombreAvis;
    }
    
    public void setNombreAvis(int nombreAvis) {
        this.nombreAvis = nombreAvis;
    }
}
