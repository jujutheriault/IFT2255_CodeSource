package com.diro.ift2255.model;

import java.time.LocalDateTime;

public class Etudiant extends User {
    private int matricule;
    private String prenom;
    private String nom;
    private String programme;
    private int cycle;
    private LocalDateTime debutProgramme;
    private String password;

    private String[] preferences;               // On pourra faire une classe Preferences plus tard
    private String[] coursPasse;                // On pourra faire une classe sigle plus tard
    private String[] filtres;

    public Etudiant(int id, String name, String email) {
        super(id, name, email);
    }

    // Getters / Setters
    public void setMatricule(int matricule){
        this.matricule = matricule;
    }
    public int getMatricule(){
        return matricule;
    }

    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    public String getPrenom(){
        return prenom;
    }
    /**
     * Setter pour le nom de l'utilisateur
     * @param nom le nom de l'utilisateur
     */
    public void setNom(String nom){
        this.nom = nom;
    }
    public String getNom(){
        return nom;
    }
    /**
     * Setter pour le programme choisi
     * @param programme
     */
    public void setProgramme(String programme){
        this.programme = programme;
    }
    public String getProgramme(){
        return programme;
    }
    /**
     * Setter pour le cycle d'étude choisi
     * @param cycle le cycle d'étude choisi
     */
    public void setCycle(int cycle){
        this.cycle = cycle;
    }
    public int getCycle(){
        return cycle;
    }
    /**
     * Setter pour la date de debut du programme choisi
     * @param debutProgramme la date de debut du programme choisi
     */
    public void setDebutProgramme(LocalDateTime debutProgramme){
        this.debutProgramme = debutProgramme;
    }
    public LocalDateTime getDebutProgramme(){
        return debutProgramme;
    }

    public void setPreferences(String[] preferences){

    this.preferences = preferences;

    }

    public String[] getPreferences(){
        return preferences;
    }

    public void setCoursPasse(String[] coursPasse){

        this.coursPasse = coursPasse;
    }

    public String[] getCoursPasse(){
        return coursPasse;
    }

    public void setFiltres(String[] filtres){
        this.filtres = filtres;
    }
    public String[] getFiltres(){
        return filtres;
    }
}

    
