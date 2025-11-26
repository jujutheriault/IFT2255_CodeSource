package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public class Horaire {
    private Course cours;           // Référence vers l'objet Course
    
    // Informations temporelles spécifiques à l'horaire
    private int annee;              
    private String jourSemaine;    
    private LocalTime heureDebut;  
    private LocalTime heureFin;     
    
    // Emplacement
    private String local;          
    private String pavillon;        

    
    public Horaire(Course cours, int annee, String jourSemaine, 
                   LocalTime heureDebut, LocalTime heureFin,
                   String local, String pavillon) {
        this.cours = cours;
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
}
