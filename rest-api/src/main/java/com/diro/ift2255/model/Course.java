package com.diro.ift2255.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.Set;
import java.util.ArrayList;




@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Course {

    @JsonAlias({"id"})   
    private String id;

    private String name;

    private String description;

    private Double credits;

    @JsonProperty("available_terms")
    private Map<String, Boolean> availableTerms;

    @JsonProperty("available_periods")
    private Map<String, Boolean> availablePeriods;

    @JsonProperty("prerequisite_courses")
    private List<String> prerequisiteCourses;

    @JsonProperty("concomitant_courses")
    private List<String> concomitantCourses;

    @JsonProperty("equivalent_courses")
    private List<String> equivalentCourses;

    @JsonProperty("requirement_text")
    private String requirementText;

    @JsonProperty("schedules")
    private JsonNode schedules;

    // Champs internes (pas dans Planifium)
    private String professeur;
    private String cycle;
    private Integer chargeTravail;

    // Champ pour les résultats agrégés
    private String moyenne;
    private Double score;
    private int participants;
    private int trimestres; 

    public Course() {}

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters/Setters API Planifium
    /**
     * Getter pour l'identifiant du cours
     * @return l'identifiant du cours
     */
    public String getId() { return id; }
    /**
     * Setter pour l'identifiant d'un cours
     * @param id le nouvel identifiant du cours selectionne
     */
    public void setId(String id) { this.id = id; }
    /**
     * Getter pour le nom d'un cours
     * @return le nom du cours selectionne
     */
    public String getName() { return name; }
    /**
     * Setter pour le nom d'un cours
     * @param name le nom modofie du cours selectionne
     */
    public void setName(String name) { this.name = name; }
    /**
     * Getter pour la description d'un cours
     * @return la description d'un cours
     */
    public String getDescription() { return description; }
    /**
     * Setter pour la description d'un cours
     * @param description la description d'un cours modifiee
     */
    public void setDescription(String description) { this.description = description; }
    /**
     * Getter pour le nombre de credit d'un cours
     * @return le nombre de credits associe a un cours
     */
    public Double getCredits() { return credits; }
    /**
     * Setter pour le nombre de credits associes a un cours
     * @param credits le nombre de credits pour un cours mis a jour
     */
    public void setCredits(Double credits) { this.credits = credits; }
    /**
     * Getter pour les termes relatifs a un cours
     * @return  les termes relatifs a un cours
     */
    public Map<String, Boolean> getTerms() { return availableTerms; }
    /**
     * Setter pour les termes associes a un cours
     * @param availableTerms les termes associes a un cours modifie
     */
    public void setAvailableTerms(Map<String, Boolean> availableTerms) { this.availableTerms = availableTerms; }
    /**
     * Getter pour les periodes de cours disponibles
     * @return les periodes disponibles pour un cours
     */
    public Map<String, Boolean> getAvailablePeriods() { return availablePeriods; }
    /**
     * Setter pour les periodes de disponibilite pour un cours
     * @param availablePeriods les periodes d'un cours mis a jour
     */
    public void setAvailablePeriods(Map<String, Boolean> availablePeriods) { this.availablePeriods = availablePeriods; }
    /**
     * Getter pour les prerequis a un cours
     * @return les prerequis relies au cours selectionne
     */
    public List<String> getPrerequisiteCourses() { return prerequisiteCourses; }
    /**
     * Setter pour les prerequis a un cours
     * @param prerequisiteCourses les prerequis a un cours  mis a jour
     */
    public void setPrerequisiteCourses(List<String> prerequisiteCourses) { this.prerequisiteCourses = prerequisiteCourses; }
    /**
     * Getter pour les cours concomitant entre eux
     * @return les cours concomitants au cours selectionne
     */
    public List<String> getConcomitantCourses() { return concomitantCourses; }
    /**
     * Setter pour les cours concomitants entre eux
     * @param concomitantCourses la liste des cours concomitants au cours selectionne
     */
    public void setConcomitantCourses(List<String> concomitantCourses) { this.concomitantCourses = concomitantCourses; }
    /**
     * Getter pour les cours equivalents
     * @return une liste de cours equivalents au cours selectionne
     */
    public List<String> getEquivalentCourses() { return equivalentCourses; }
    /**
     * Setter pour les cours equivalents
     * @param equivalentCourses la liste des cours equivalents mise a jour
     */
    public void setEquivalentCourses(List<String> equivalentCourses) { this.equivalentCourses = equivalentCourses; }
    /**
     * Getter pour les prerequis a un cours
     * @return les prerequis a un cours
     */
    public String getRequirementText() { return requirementText; }
    /**
     * Setter pour les prerequis d'un cours
     * @param requirementText un texte des prerequis a un cours mis a jour
     */
    public void setRequirementText(String requirementText) { this.requirementText = requirementText; }
    /**
     * Gettter pour un horaire de cours
     * @return un horaire de cours
     */
public JsonNode getSchedules() { return schedules; }
/**
 * Setter pour un horaire de cours
 * @param schedules un horaire de cours modifie
 */
public void setSchedules(JsonNode schedules) { this.schedules = schedules; }

    // Champs internes
    /**
     * Getter pour un professeur
     * @return un professeur associe a un cours
     */
    public String getProfesseur() { return professeur; }
    /**
     * Setter pour un professeur aoocie a un cours
     * @param professeur un nouveau professeur associe a un cours
     */
    public void setProfesseur(String professeur) { this.professeur = professeur; }
    /**
     * Getter pour un cycle d'etudes
     * @return un cycle d'etudes
     */
    public String getCycle() { return cycle; }
    /**
     * Setter pour un cycle d'etudes
     * @param cycle un cycle d'etudes mi a jour pour un cours
     */
    public void setCycle(String cycle) { this.cycle = cycle; }
    /**
     * Getter pour la charge de travail associee a un cours
     * @return une charge de travail pour un cours 
     */
    public Integer getChargeTravail() { return chargeTravail; }
    /**
     * Setter pour la charge de travail reliee a un cours
     * @param chargeTravail charge de travail mise a jour
     */
    public void setChargeTravail(Integer chargeTravail) { this.chargeTravail = chargeTravail; }

    // Getters/Setters résultats agrégés
    /**
     * Getter pour la moyenne d'un cours
     * @return la moyenne d'un cours
     */
    public String getMoyenne() { return moyenne; }
    /**
     * Setter pour la moyenne d'un cours
     * @param moyenne la moyenne d'un cours mise a jour
     */
    public void setMoyenne(String moyenne) { this.moyenne = moyenne; }
    /**
     * Getter pour le score d'un cours
     * @return le score associe a un cours
     */          
    public Double getScore() { return score; }
    /**
     * Setter pour le score d'un cours
     * @param score le score modidfie d'un cours
     */
    public void setScore(Double score) { this.score = score; }
    /**
     * Getter pour le nombre de participants a un cours
     * @return le nombre de participants a un cours
     */
    public int getParticipants() { return participants; }
    /**
     * Setter pour le nombre de participants a un cours
     * @param participants nombre participants mis a jour
     */
    public void setParticipants(int participants) { this.participants = participants; }
    /**
     * Getter pour le trimestre associe a un cours
     * @return le trimestre associe a un cours
     */
    public int getTrimestres() { return trimestres; }
    /**
     * Setter pour le trimestre associe a un cours
     * @param trimestres trimestre associe a un cours mis a jour
     */
    public void setTrimestres(int trimestres) { this.trimestres = trimestres; }

    /**
     * Charge et assigne les résultats agrégés d'un cours à partir d'une source CSV.
     * Si aucune donnée agrégée n'est trouvée pour le cours, les champs
     * restent inchangés.
     *
     * @see CourseAggregates
     */
    public void setAggregates() {
        CourseAggregates aggregates = CourseAggregates.loadFromCsvResource(this.id);

        if (aggregates != null) {
            this.moyenne = aggregates.getMoyenne();
            this.score = aggregates.getScore();
            this.participants = aggregates.getParticipants();
            this.trimestres = aggregates.getTrimestres();
        }
    }

    /**
     * Détermine la liste des prérequis manquants pour un cours donné.
     * Si aucun prérequis n'est défini pour le cours ou si tous les prérequis
     * ont été complétés, la liste retournée est vide.
     *
     * @param completedCourses Ensemble des sigles de cours déjà complétés
     *                         par l'étudiant
     * @return Une liste des sigles de cours prérequis qui n'ont pas encore
     *         été complétés
     */
    public List<String> missingPrerequisites(Set<String> completedCourses) {
        if (completedCourses == null) completedCourses = Set.of();

        if (prerequisiteCourses == null || prerequisiteCourses.isEmpty()) {
            return List.of();
        }

        List<String> missing = new ArrayList<>();
        for (String p : prerequisiteCourses) {
            if (p == null) continue;
            String sigle = p.trim().toUpperCase();
            if (!sigle.isEmpty() && !completedCourses.contains(sigle)) {
                missing.add(sigle);
            }
        }
        return missing;
    }

}
