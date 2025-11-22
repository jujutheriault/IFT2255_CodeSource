package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private String id;
    private String name;
    private String description;

    //Ajouts 
    private String professeur;             // Professeur responsable du cours
    private int credits;                   // Nombre de crédits
    private String trimestre;              // Trimestre d'offre (ex : Hiver 2026)
    private String cycle;                  // Cycle d'études (ex : baccalauréat, maîtrise)
    private String[] prerequis;            // Prérequis (sigles des cours requis)
    private String[] corequis;             // Co-requis (sigles des cours à suivre en parallèle)

    public Course() {}

    public Course(String id, String name, String desc, String professeur, int credits,
                  String trimestre, String cycle, String[] prerequis, String[] corequis) {
        this.id = id;
        this.name = name;
        this.description = desc;

        //Ajouts 
        this.professeur = professeur;
        this.credits = credits;
        this.trimestre = trimestre;
        this.cycle = cycle;
        this.prerequis = prerequis;
        this.corequis = corequis;
    }


    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String email) { this.description = email; }

    //Ajouts 
    public String getProfesseur() { return professeur; }
    public void setProfesseur(String professeur) { this.professeur = professeur; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public String getTrimestre() { return trimestre; }
    public void setTrimestre(String trimestre) { this.trimestre = trimestre; }

    public String getCycle() { return cycle; }
    public void setCycle(String cycle) { this.cycle = cycle; }

    public String[] getPrerequis() { return prerequis; }
    public void setPrerequis(String[] prerequis) { this.prerequis = prerequis; }

    public String[] getCorequis() { return corequis; }
    public void setCorequis(String[] corequis) { this.corequis = corequis; }
    
}
