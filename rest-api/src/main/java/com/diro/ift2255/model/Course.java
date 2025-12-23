package com.diro.ift2255.model;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    private List<Object> schedules; 

    // Champs internes (pas dans Planifium)
    private String professeur;
    private String cycle;
    private Integer chargeTravail;

    public Course() {}

    public Course(String id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters/Setters 
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getCredits() { return credits; }
    public void setCredits(Double credits) { this.credits = credits; }

    public Map<String, Boolean> getTerms() { return availableTerms; }
    public void setAvailableTerms(Map<String, Boolean> availableTerms) { this.availableTerms = availableTerms; }

    public Map<String, Boolean> getAvailablePeriods() { return availablePeriods; }
    public void setAvailablePeriods(Map<String, Boolean> availablePeriods) { this.availablePeriods = availablePeriods; }

    public List<String> getPrerequisiteCourses() { return prerequisiteCourses; }
    public void setPrerequisiteCourses(List<String> prerequisiteCourses) { this.prerequisiteCourses = prerequisiteCourses; }

    public List<String> getConcomitantCourses() { return concomitantCourses; }
    public void setConcomitantCourses(List<String> concomitantCourses) { this.concomitantCourses = concomitantCourses; }

    public List<String> getEquivalentCourses() { return equivalentCourses; }
    public void setEquivalentCourses(List<String> equivalentCourses) { this.equivalentCourses = equivalentCourses; }

    public String getRequirementText() { return requirementText; }
    public void setRequirementText(String requirementText) { this.requirementText = requirementText; }

    public List<Object> getSchedules() { return schedules; }
    public void setSchedules(List<Object> schedules) { this.schedules = schedules; }

    // Champs internes
    public String getProfesseur() { return professeur; }
    public void setProfesseur(String professeur) { this.professeur = professeur; }

    public String getCycle() { return cycle; }
    public void setCycle(String cycle) { this.cycle = cycle; }

    public Integer getChargeTravail() { return chargeTravail; }
    public void setChargeTravail(Integer chargeTravail) { this.chargeTravail = chargeTravail; }
}
