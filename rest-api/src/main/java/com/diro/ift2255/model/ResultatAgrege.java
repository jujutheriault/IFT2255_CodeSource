package com.diro.ift2255.model;

public class ResultatAgrege {
    private String sigle;
    private String nom;
    private String moyenne;      // Note lettre (A, B+, C, etc.)
    private double score;         // Score numérique (sur 5)
    private int participants;     // Nombre d'étudiants
    private int trimestres;       // Nombre de trimestres évalués

    public ResultatAgrege() {}

    public ResultatAgrege(String sigle, String nom, String moyenne, double score, int participants, int trimestres) {
        this.sigle = sigle;
        this.nom = nom;
        this.moyenne = moyenne;
        this.score = score;
        this.participants = participants;
        this.trimestres = trimestres;
    }

    // Getters et Setters
    public String getSigle() { return sigle; }
    public void setSigle(String sigle) { this.sigle = sigle; }

    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getMoyenne() { return moyenne; }
    public void setMoyenne(String moyenne) { this.moyenne = moyenne; }

    public double getScore() { return score; }
    public void setScore(double score) { this.score = score; }

    public int getParticipants() { return participants; }
    public void setParticipants(int participants) { this.participants = participants; }

    public int getTrimestres() { return trimestres; }
    public void setTrimestres(int trimestres) { this.trimestres = trimestres; }

    @Override
    public String toString() {
        return "ResultatAgrege{" +
                "sigle='" + sigle + '\'' +
                ", nom='" + nom + '\'' +
                ", moyenne='" + moyenne + '\'' +
                ", score=" + score +
                ", participants=" + participants +
                ", trimestres=" + trimestres +
                '}';
    }
}