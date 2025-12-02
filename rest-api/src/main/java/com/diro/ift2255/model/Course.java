package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private String id;
    private String name;
    private String description;


    // Ajouts basés sur le retour de l'API planifium

    private int credits;
    private Map<String, Boolean> available_terms;   //  { "autumn": true, "summer": true }
    private List<String> prerequisite_courses;        // ["IFT1025"] (prérequis)
    private List<String> concomitant_courses;         // []           (corequis)

    //Ajouts futurs
    private String professeur;             // Professeur responsable du cours
    private String cycle;                  // Cycle d'études (ex : baccalauréat, maîtrise)
    private int chargeTravail;          // Charge de travail en heures


    public Course() {}

    public Course(String id, String name) {
    this.id = id;
    this.name = name;
    }

    public Course(String id, String name, String description, String professeur, int credits,
                  Map<String, Boolean>  available_terms, List<String> prerequisite_courses, 
                  List<String> concomitant_courses) {
        this.id = id;
        this.name = name;
        this.description = description;

        //Ajouts 
        // this.professeur = professeur;

        this.credits = credits;
        this.prerequisite_courses = prerequisite_courses;
        this.concomitant_courses = concomitant_courses;
        this.available_terms = available_terms;

        this.professeur = null;
        this.cycle = null;
        this.chargeTravail = 0;   // Si 0, crédit pas encore instancié
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    // Ajouts
    public String getProfesseur() { return professeur; }
    public void setProfesseur(String professeur) { this.professeur = professeur; }

    public String getCycle() { return cycle; }
    public void setCycle(String cycle) { this.cycle = cycle; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }

    public Map<String, Boolean> getTerms() { return available_terms; }
    public void setTerm(Map<String, Boolean> available_terms) { this.available_terms = available_terms; }

    public List<String> getPrerequis() { return prerequisite_courses; }
    public void setPrerequis(List<String> prerequisite_courses) { this.prerequisite_courses = prerequisite_courses; }

    public List<String>  getCorequis() { return concomitant_courses; }
    public void setCorequis(List<String> concomitant_courses) { this.concomitant_courses = concomitant_courses; }

    public int getChargeTravail() { return chargeTravail; }
    public void setChargeTravail(int chargeTravail) { this.chargeTravail = chargeTravail; }
}

