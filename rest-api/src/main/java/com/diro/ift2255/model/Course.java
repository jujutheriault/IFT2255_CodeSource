package com.diro.ift2255.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    @JsonProperty("_id")
    private String id;
    private String name;
    private String description = null;


    // Ajouts basés sur le retour de l'API planifium

    private int credits;
    private Map<String, Boolean> available_terms;   //  { "autumn": true, "summer": true }
    private List<String> prerequisite_courses;        // ["IFT1025"] (prérequis)
    private List<String> concomitant_courses;         // []           (corequis)

    //Ajouts futurs
    private String professeur;             // Professeur responsable du cours
    private String cycle;                  // Cycle d'études (ex : baccalauréat, maîtrise)
    private int chargeTravail;          // Charge de travail en heures

    /**
     * Constructeur d'un cours par defaut
     */
    public Course() {}
    /**
     * Constructeur d'un cours avec identifiant et nom
     * @param id identification d'un cours
     * @param name nom d'un cours
     */
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
    /**
     * Pour retourner l'identifiant du cours
     * @return l'identifiant du cours.
     */
    public String getId() { return id; }
    /**
     * Setter pour l'identifiant du cours
     * @param id l'identifiant du cours.
     */
    public void setId(String id) { this.id = id; }
    /**
     * Getter pour le nom du cours
     * @return le nom du cours
     */
    public String getName() { return name; }
    /**
     * Setter pour le nom du cours
     * @param name le nom du cours
     */
    public void setName(String name) { this.name = name; }
    /**
     * Getter pour la description du cours
     * @return
     */
    public String getDescription() { return description; }
    /**
     * Setter pour la description d'un cours
     * @param description la description d'un cours modifiee
     */
    public void setDescription(String description) { this.description = description; }

    // Ajouts
    /**
     * Getter pour le nom du professeur
     * @return le nom du professeur donnant le cours choisi
     */
    public String getProfesseur() { return professeur; }
    /**
     * Setter pour le nom du professeur d'un cours
     * @param professeur le nom d'un professeur modofie
     */
    public void setProfesseur(String professeur) { this.professeur = professeur; }
    /**
     * Getter pour le cycle d'etudes
     * @return le cycle d'etudes 
     */
    public String getCycle() { return cycle; }
    /**
     * Setter pour le cycle d'etudes
     * @param cycle le cycle d'etudes modifie
     */
    public void setCycle(String cycle) { this.cycle = cycle; }
    /**
     * Getter pour le nombre de credits
     * @return le nombre de credits du cours choisi
     */
    public int getCredits() { return credits; }
    /**
     * Setter pour le nombre de credits
     * @param credits le nombre de credits modifie du cours choisi
     */
    public void setCredits(int credits) { this.credits = credits; }
    /**
     * Getter pour les termes disponibles dans la recherche de cours
     * @return le termes disponibles dans la recherche de cours
     */
    public Map<String, Boolean> getTerms() { return available_terms; }
    /**
     * Setter pour les termes disponibles dans la recherche de cours
     * @param available_terms liste de termes modifiee
     */
    public void setTerm(Map<String, Boolean> available_terms) { this.available_terms = available_terms; }
    /**
     * Getter pour les prerequis pour un cours
     * @return
     */
    public List<String> getPrerequis() { return prerequisite_courses; }
    /**
     * Setter pour les prerequis pour un cours
     * @param prerequisite_courses prerequis modifies
     */
    public void setPrerequis(List<String> prerequisite_courses) { this.prerequisite_courses = prerequisite_courses; }
    /**
     * Getter pour les corequis d'un cours
     * @return les corequis associes a un cours
     */
    public List<String>  getCorequis() { return concomitant_courses; }
    /**
     * Setter pour les corequis d'un cours
     * @param concomitant_courses la liste modifiee des cours corequis
     */
    public void setCorequis(List<String> concomitant_courses) { this.concomitant_courses = concomitant_courses; }
    /**
     * Getter pour la charge de travail associee a un cours
     * @return la charge de travail d'un cours
     */
    public int getChargeTravail() { return chargeTravail; }
    /**
     * Setter pour la charge de travail associee a un cours
     * @param chargeTravail la charge de travail d'un cours modifiee
     */
    public void setChargeTravail(int chargeTravail) { this.chargeTravail = chargeTravail; }
}

