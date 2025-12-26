package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Avis {
    private String sigle;
    private String session;
    private String trimestre;
    private int annee;
    private int nivDifficulte;
    private int volumeTravail;
    private String professeur;
    private int nombreAvis;
    private String commentaire;

    /**
     * Constructeur d'avis par defaut
     * 
     */
    public Avis() {}
    /**
    *Constructeur d'avis avec parametres
    *@param sigle le sigle de programme ou cours
    *@param session la session relative a l'avis
    *@param trimestre le trimestre relatif a l'avis
    *@param annee l'anne relative a l'avis
    *@param nivDifficulte le niveau de difficulte du cours
    *@param volumeTravail le volume de travail du cours
    *@param professeur le nom du prfesseur donnant le cours
    *@param nombreAvis le score attribue au cours
    */
    public Avis(String sigle, String session, String trimestre, int annee, int nivDifficulte,
                int volumeTravail, String professeur, int nombreAvis){
        this.sigle = sigle;           
        this.session = session;
        this.trimestre = trimestre;
        this.annee = annee;
        this.nivDifficulte = nivDifficulte;
        this.volumeTravail = volumeTravail;
        this.professeur = professeur;
        this.nombreAvis = nombreAvis;
    }

    // Getters et Setters
    /**
     * Getter pour le sigle 
     * @return sigle du programme/cours
     */
    public String getSigle() {
        return sigle;
    }

    /**
     * Setter pour le sigle 
     * @param sigle - sigle du programme/cours
     */
    public void setSigle(String sigle) {
        this.sigle = sigle;
    }

    /**
     * Getter pour la session
     * @return la session choisie
     */
    public String getSession() { return session; }
    /**
     * Setter pour la session
     * @param session la seesion modifiee
     */
    public void setSession(String session) { this.session = session; }
    /**
     * 
     * Getter pour le trimestre
     * @return le trimestre choisi
     */
    public String getTrimestre() { return trimestre; }
    /**
     * Setter pour le trimestre
     * @param trimestre le trimestre modifie
     */
    public void setTrimestre(String trimestre) { this.trimestre = trimestre; }

    /**
     * Getter pour l'annee
     * @return l'annee de la prise du cours
     */
    public int getAnnee() { return annee; }
    /**
     * Setter pour l'annee
     * @param annee l'annee modofiee de la prise du cours
     */
    public void setAnnee(int annee) { this.annee = annee; }
    /**
     * Getter pour le niveau de difficulte
     * @return le niveau de difficulte du cours
     */
    public int getNivDifficulte() { return nivDifficulte; }
    /**
     * Setter pour le niveau de difficulte
     * @param nivDifficulte le niveau de difficulte modifie
     */
    public void setNivDifficulte(int nivDifficulte) { this.nivDifficulte = nivDifficulte; }
    /**
     * Getter pour le niveau de volume de travail
     * @return le volume de travail du cours
     */
    public int getVolumeTravail() { return volumeTravail; }
    /**
     * Setter pour le volume de travail
     * @param volumeTravail le volume de travail modifie d'un cours
     */
    public void setVolumeTravail(int volumeTravail) { this.volumeTravail = volumeTravail; }
    /**
     * Getter pour le nom du professeur
     * @return le nom du professeur d'un cours choisi
     */
    public String getProfesseur() { return professeur; }
    /**
     * Setter pour le nom de professeur d'un cours
     * @param professeur le nom d'un professeur associe a un cours
     */
    public void setProfesseur(String professeur) { this.professeur = professeur; }
    /**
     * Getter pour le score d'un cours
     * @return le score d'un cours
     */
    public int getNombreAvis() { return nombreAvis; }
    /**
     * Setter pour le score d'un cours
     * @param nombreAvis le score donne pour un cours dans l'avis
     */
    public void setNombreAvis(int nombreAvis) { this.nombreAvis = nombreAvis; }

    /**
     * Getter pour le commentaire d'un avis
     * @return le commentaire d'un avis
     */
    public String getCommentaire() { return commentaire; }

    /**
     * Setter pour le commentaire d'un avis
     * @param commentaire
     */
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
}
