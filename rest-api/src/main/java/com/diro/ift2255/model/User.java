package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String email;
    /**
     * utilisateur par defaut,non enregistre
     */
    public User() {}
    /**
     * utilisateur enregistre
     * @param id l'identifiant de l'utilisateur
     * @param name le nom de l'utilisateur
     * @param email l'adresse courriel de l'utilisateur
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }
    /**
     * Getter pour l'identifiant de l'utilisateur
     * @return l'identifiant de l'utilisateur
     */
    public int getId() { return id; }
    /**
     * Setter pour l'identifiant de l'utilisateur
     * @param id le nouvel identifiant de l'utilisateur
     */
    public void setId(int id) { this.id = id; }
    /**
     * Getter pour le nom de l'utilisateur
     * @return le nom de l'utilisateur
     */
    public String getName() { return name; }
    /**
     * Setter pour le nom de l'utilisateur
     * @param name le nouveau nom de l'utilisateur
     */
    public void setName(String name) { this.name = name; }
    /**
     * Getter pour l'adresse courriel de l'utilisateur
     * @return l'adresse courriel de l'utilisateur
     */
    public String getEmail() { return email; }
    /**
     * Setter pour l'adresse courriel de l'utilisateur
     * @param email le nouveau courriel de l'utilisateur
     */
    public void setEmail(String email) { this.email = email; }
}
