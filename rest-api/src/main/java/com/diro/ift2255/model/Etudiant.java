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
    /**
     * Constructeur d'un utilisateur etudiant avec parametres
     * @param id l'identification de l'etudiant
     * @param name le nom de l'etudiant
     * @param email le courriel de l'etudiant
     */
    public Etudiant(int id, String name, String email) {
        super(id, name, email);
    }

    // Getters / Setters
    /**
     * Setter pour le matricule de l'etudiant
     * @param matricule le matricule de l'etudiant modifie/ajoute
     */
    public void setMatricule(int matricule){
        this.matricule = matricule;
    }
    /**
     * Getter pour le matricule de l'etudiant
     * @return le matricule de l'etudiant
     */
    public int getMatricule(){
        return matricule;
    }
    /**
     * 
     * Setter pour le prenom de l'utilisateur
     * @param prenom le prenom de l'utilisateur etudiant
     */
    public void setPrenom(String prenom){
        this.prenom = prenom;
    }
    /**
     * Getter pour le prenom de l'etudiant
     * @return le prenom de l'etudiant
     */
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
    /**
     * Getter pour le nom de l'utilisateur
     * @return le nom de l'etudiant
     * 
     */
    public String getNom(){
        return nom;
    }
    /**
     * Setter pour le programme choisi
     * @param programme le programme choisi modifie
     */
    public void setProgramme(String programme){
        this.programme = programme;
    }
    /**
     * Getter pour le programme choisi
     * @return le programme choisi
     */
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
    /**
     * Getter pour le cycle d'etude choisi
     * @return le cycle d'etude de l'etudiant
     */
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
    /**
     * Getter pour la date de debut du programme choisi
     * @return la date de debut du programme choisi
     */
    public LocalDateTime getDebutProgramme(){
        return debutProgramme;
    }
    /**
     * Setter pour les preferences de l'utilisateur
     * @param preferences  les preferences de l'etudiant  
     */
    public void setPreferences(String[] preferences){

    this.preferences = preferences;

    }
    /**
     * Getter pour les preferences de l'utilisateur 
     * @return les preferences de l'etudiant
     */
    public String[] getPreferences(){
        return preferences;
    }
    /**
     * Setter pour la liste des cours passes par l'etudiant
     * @param coursPasse la liste a jour des cours passes par l'etudiant
     */
    public void setCoursPasse(String[] coursPasse){

        this.coursPasse = coursPasse;
    }
    /**
     * Getter pour la liste des cours passes par l'etudiant
     * @return lla liste des cours passes par l'etudiant
     */
    public String[] getCoursPasse(){
        return coursPasse;
    }
    /**
     * Setter pour les filtres de recherche de l'utilisateur
     * @param filtres les filtres de recherche de l'utilisateur mis a jour
     */
    public void setFiltres(String[] filtres){
        this.filtres = filtres;
    }
    /**
     * Getter pour les filtres de recherche de l'utilisateur
     * @return les filtres de recherche de l'utilisateur
     */
    public String[] getFiltres(){
        return filtres;
    }
}

    
