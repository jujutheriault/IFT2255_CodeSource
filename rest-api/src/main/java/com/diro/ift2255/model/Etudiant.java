package com.diro.ift2255.model;

import java.time.LocalDateTime;

public class Etudiant {
    private int matricule;
    private String prenom;
    private String nom;
    private String programme;
    private int cycle;
    private LocalDateTime debutProgramme;
    private Preferences preferences;
    private Sigle[] coursPasse;
    private String[] filtres;


    /**
     * 
     * Setter pour le matricule
     * @param matricule le nouveau matricule de l'utilisateur
     */
    public void setmatricule(int matricule){

        this.matricule = matricule;
    }
    /**
     * Getter pour le matricule
     * @return le matricule de l'utilisateur
     */
    public int getMatricule(){

        return matricule;
    }

    /**
     * Setter pour le prenom de l'utilisateur
     * @param prenom le nouveau prenom de l'utilisateur
     */
    public void setPrenom(String prenom){

        this.prenom = prenom;
    }
    /**
     * 
     * Getter pour le prenom de l'utilisateur
     * @return le prenom de l'utilisateur
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
     * @return le nom de l'utilisateur
     */
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
    /**
     * 
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
     * Getter pour le cycle d'étude choisi
     * @return le cycle d'étude choisi
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
     * Getter pour la date du debut de programme choisi
     * @return la date de debut du programme choisi
     */
    public LocalDateTime getDebutProgramme(){

        return debutProgramme;
    }
    /**
     * Setter pour les preferences choisies par l'utilisateur
     * @param preferences les preferences choisies par l'utilisateur
     */
    public void setPreferences(Preferences preferences){

    this.preferences = preferences;

    }
    /**
     *Getter pour les preferences choisies par l'utilisateur 
     * @return les preferences choisies par l'utilisateur
     */
    public Preferences getPreferences(){

        return preferences;
    }
    /**
     * Setter pour les cours passes par l'utilisateur
     * @param coursPasse les cours passes par l'utilisateur
     */
    public void setCoursPasse(sigle[] coursPasse){

        this.coursPasse = coursPasse;
    }
    /**
     * Getter pour les cours passes par l'utilisateur
     * @return les cours passes par l'utilisateur
     */
    public sigle[] getcoursPasse(){

        return coursPasse;
    }

    /**
     * Setter pour les filtres
     * @param filtres les filtres choisis par l'utilisateur
     */
    public void setFiltres(String[] filtres){

        this.filtres = filtres;
    }
    /**
     * Getter pour les filtres
     * @return les filtres choisis par l'utilisateur
     */
    public String[] getFiltres(){

        return filtres;
    }

    /**
     * Modofie le profil de l'utilisateur
     */
    public void modifierProfil(){



    }

    /**
     * Ajoute un filtre dans la liste de filtres de l'utilisateur
     */
    public void ajouterFiltre(){


    }

    /**
     *  Sauvegarde la selection de cours de l'utlisateur
     */
    public void sauvegarderSelection(){

        
    }
}

    
