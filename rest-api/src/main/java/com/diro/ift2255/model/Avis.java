package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)

public class Avis {
    private String coursSigle;            // Sigle du cours concerné
    private String session;               // Trimestre/année
    private int annee;                    // Année de l'avis
    private int volumeTravail;            // Charge estimée (heures)
    private int niveauDifficulte;         // Échelle de difficulté (à adapter)
    private String professeur;            // Prof attribué (optionnel)
    private int nombreAvis;               // Nombre d'avis agrégés (optionnel)

    public Avis() {}

    public Avis(String coursSigle, String session, int annee, int volumeTravail, int niveauDifficulte,
                String professeur, int nombreAvis) {
        this.coursSigle = coursSigle;
        this.session = session;
        this.annee = annee;
        this.volumeTravail = volumeTravail;
        this.niveauDifficulte = niveauDifficulte;
        this.professeur = professeur;
        this.nombreAvis = nombreAvis;
    }

    public String getCoursSigle() { return coursSigle; }
    public void setCoursSigle(String coursSigle) { this.coursSigle = coursSigle; }

    public String getSession() { return session; }
    public void setSession(String session) { this.session = session; }

    public int getAnnee() { return annee; }
    public void setAnnee(int annee) { this.annee = annee; }

    public int volumeTravail() { return volumeTravail; }
    public void volumeTravail(int volumeTravail) { this.volumeTravail = volumeTravail; }

    public int getNiveauDifficulte() { return niveauDifficulte; }
    public void setNiveauDifficulte(int niveauDifficulte) { this.niveauDifficulte = niveauDifficulte; }

    public String getProfesseur() { return professeur; }
    public void setProfesseur(String professeur) { this.professeur = professeur; }

    public int getNombreAvis() { return nombreAvis; }
    public void setNombreAvis(int nombreAvis) { this.nombreAvis = nombreAvis; }
}
