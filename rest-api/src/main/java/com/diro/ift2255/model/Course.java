package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Course {
    private String id;
    private String name;
    private String description;

    public Course() {}

    public Course(String id, String name, String desc) {
        this.id = id;
        this.name = name;
        this.description = desc;
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
     * Setter pour la description du cours
     * @param email la description du cours
     */
    public void setDescription(String email) { this.description = email; }
}
