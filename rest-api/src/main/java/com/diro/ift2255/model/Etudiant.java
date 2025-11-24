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
    private sigle[] coursPasse;
    private String[] filtres;



    public void setmatricule(int matricule){

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

    public void setNom(String nom){

        this.nom = nom;
    }

    public String getNom(){

        return nom;
    }

    public void setProgramme(String programme){

        this.programme = programme;
    }

    public String getProgramme(){

        return programme;
    }

    public void setCycle(int cycle){

        this.cycle = cycle;
    }

    public int getCycle(){

        return cycle;
    }

    public void setDebutProgramme(LocalDateTime debutProgramme){

        this.debutProgramme = debutProgramme;
    }

    public LocalDateTime getDebutProgramme(){

        return debutProgramme;
    }

    public void setPreferences(Preferences preferences){

    this.preferences = preferences;

    }

    public Preferences getPreferences(){

        return preferences;
    }

    public void setCoursPasse(sigle[] coursPasse){

        this.coursPasse = coursPasse;
    }

    public sigle[] getcoursPasse(){

        return coursPasse;
    }


    public void setFiltres(String[] filtres){

        this.filtres = filtres;
    }

    public String[] getFiltres(){

        return filtres;
    }


    public void modifierProfil(){



    }


    public void ajouterFiltre(){


    }


    public void sauvegarderSelection(){

        
    }
}

    
