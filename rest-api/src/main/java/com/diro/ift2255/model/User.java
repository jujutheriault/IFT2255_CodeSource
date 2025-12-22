package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class User {
    private int id;
    private String name;
    private String email;
    private String password;
    private boolean estAuthentifie;
    private int cycle;
    /**
     * Constructeur par defaut d'un utilisateur
     */
    public User() {}
    /**
     * Constructeur d'un utilisateur avec parametres
     * @param id identifiant de l'utilisateur
     * @param name nom de l'utilisateur 
     * @param email courriel de l'utilisateur
     */
    public User(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
        // Ajouts
        this.password = null;
        this.estAuthentifie = false;
        this.cycle = 1; // ✅ Par défaut : Baccalauréat
    }

    // Getters / Setters
    /**
     * Getter pour l'identidiant de l'utilisateur
     * @return identifiant de l'utilisateur
     */
    public int getId() { return id; }
    /**
     * Setter pour l'identifiant de l'utlisateur
     * @param id identifiant modifie
     */
    public void setId(int id) { this.id = id; }
    /**
     * Getter pour le nom de l'utilisateur
     * @return le nom de l'utilisateur
     */
    public String getName() { return name; }
    /**
     * Setter pour le nom de l'utilisateur
     * @param name nom modifie de l'utilisateur
     */
    public void setName(String name) { this.name = name; }
    /**
     * Getter pour le courriel de l'utilisateur
     * @return le courriel de l'utilisateur
     */
    public String getEmail() { return email; }
    /**
     * Setter pour le courriel de l'utilisateur
     * @param email le courriel modifie de l'utilisateur
     */
    public void setEmail(String email) { this.email = email; }
    /**
     * Getter pour le mot de passe de l'utilisateur
     * @return le mot de passe de l'utilisateur
     */
    public String getPassword() { return password; }
    /**
     * Setter pour le mot de passe de l'utilisateur
     * @param password le mot de passe modifie de l'utilisateur
     */
    public void setPassword(String password) { this.password = password; }
    /**
     * Verification de l'authentification
     * @return l'utilisateur est authentifie ou non
     */
    public boolean isEstAuthentifie() { return estAuthentifie; }
    /**
     * Setter pour l'authentifiaction de l'utilisateur
     * @param estAuthentifie l'etat de l'authentification est modifiee
     */
    public void setEstAuthentifie(boolean estAuthentifie) { this.estAuthentifie = estAuthentifie; }
    public int getCycle() { return cycle; }
    public void setCycle(int cycle) { this.cycle = cycle; }
}
