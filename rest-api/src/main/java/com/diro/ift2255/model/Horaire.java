package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalTime;

/**
 * Représente l'horaire d'un cours
 * Avec relation vers la classe Course
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Horaire {
    // Relation vers Course
    private Course cours;           // Référence vers l'objet Course
    
    // Informations temporelles
    private String trimestre;       // Ex: "Automne", "Hiver", "Été"
    private int annee;              // Ex: 2024
    private String jourSemaine;     // Ex: "Lundi", "Mardi", etc.
    private LocalTime heureDebut;   // Ex: 10:00
    private LocalTime heureFin;     // Ex: 11:30
    
    // Emplacement
    private String local;           // Ex: "M-2104"
    private String pavillon;        // Ex: "Pavillon André-Aisenstadt"
    
    // Constructeur par défaut
    public Horaire() {}
    
    // Constructeur simplifié
    public Horaire(Course cours, String trimestre) {
        this.cours = cours;
        this.trimestre = trimestre;
    }
    
    // Constructeur complet
    public Horaire(Course cours, String trimestre, int annee,
                   String jourSemaine, LocalTime heureDebut, LocalTime heureFin,
                   String local, String pavillon) {
        this.cours = cours;
        this.trimestre = trimestre;
        this.annee = annee;
        this.jourSemaine = jourSemaine;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.local = local;
        this.pavillon = pavillon;
    }
    
    // Getters et Setters
    public Course getCours() {
        return cours;
    }
    
    public void setCours(Course cours) {
        this.cours = cours;
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
    
    public String getJourSemaine() {
        return jourSemaine;
    }
    
    public void setJourSemaine(String jourSemaine) {
        this.jourSemaine = jourSemaine;
    }
    
    public LocalTime getHeureDebut() {
        return heureDebut;
    }
    
    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }
    
    public LocalTime getHeureFin() {
        return heureFin;
    }
    
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
    
    public String getLocal() {
        return local;
    }
    
    public void setLocal(String local) {
        this.local = local;
    }
    
    public String getPavillon() {
        return pavillon;
    }
    
    public void setPavillon(String pavillon) {
        this.pavillon = pavillon;
    }
    
    // Méthodes helper pour accéder facilement aux infos du cours
    public int getSigleCours() {
        return cours != null ? cours.getSigle() : 0;
    }
    
    public String getCodeCours() {
        return cours != null ? cours.getNomCours() : null;
    }
    
    public String getNomCours() {
        return cours != null ? cours.getTitre() : null;
    }
}
