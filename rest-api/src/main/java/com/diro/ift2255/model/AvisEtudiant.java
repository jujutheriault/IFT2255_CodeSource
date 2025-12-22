package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AvisEtudiant {
    private String sigle;
    private String auteur;
    private int note;
    private String commentaire;
    private String date;

    public AvisEtudiant() {}

    public AvisEtudiant(String sigle, String auteur, int note, String commentaire, String date) {
        this.sigle = sigle;
        this.auteur = auteur;
        this.note = note;
        this.commentaire = commentaire;
        this.date = date;
    }

    public String getSigle() { return sigle; }
    public void setSigle(String sigle) { this.sigle = sigle; }

    public String getAuteur() { return auteur; }
    public void setAuteur(String auteur) { this.auteur = auteur; }

    public int getNote() { return note; }
    public void setNote(int note) { this.note = note; }

    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }
}