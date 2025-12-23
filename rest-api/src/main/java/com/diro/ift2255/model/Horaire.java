package com.diro.ift2255.model;

import java.time.LocalTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Horaire {
    // On stocke maintenant le sigle (id) du cours plutôt que l'objet Course
    private String sigle;

    // Informations temporelles
    private int annee;
    private String jourSemaine;
    private LocalTime heureDebut;
    private LocalTime heureFin;

    // Emplacement
    private String local;
    private String pavillon;
    /**
     * Constructeur par defaut d'un horaire
     */
    public Horaire() {}
    /**
     * Constructeur d'un horaire avec parameters
     * @param sigle le sigle du cours
     * @param annee l'annee de l'horaire
     * @param jourSemaine les jours par semaine de l'horaire
     * @param heureDebut les heures de debut des cours
     * @param heureFin les heures de fin des cours
     * @param local  les locaux des cours de l'horaire
     * @param pavillon les pavillons ou les cours ont lieu
     */
    public Horaire(String sigle, int annee, String jourSemaine,
                   LocalTime heureDebut, LocalTime heureFin,
                   String local, String pavillon) {
        this.sigle = sigle;
        this.annee = annee;
        this.jourSemaine = jourSemaine;
        this.heureDebut = heureDebut;
        this.heureFin = heureFin;
        this.local = local;
        this.pavillon = pavillon;
    }

    // Getters / Setters
    /**
     * Getter pour le sigle du cours
     * @return le sigle du cours
     */
    public String getSigle() {
        return sigle;
    }
    /**
     * Setter pour le sigle du cours
     * @param sigle le sigle du cours modifie
     */
    public void setSigle(String sigle) {
        this.sigle = sigle;
    }
    /**
     * Getter pour l'annee des cours entrepris
     * @return l'annee de l'horaire
     */
    public int getAnnee() {
        return annee;
    }
    /**
     * Setter pour l'annee des cours entrepris
     * @param annee l'annee modifiee de l'horaire de cours
     */
    public void setAnnee(int annee) {
        this.annee = annee;
    }
    /**
     * Getter pour le jour de la semaine
     * @return les jours de semaine des cours
     */
    public String getJourSemaine() {
        return jourSemaine;
    }
    /**
     * Setter pour les jours par semaine des cours
     * @param jourSemaine les jours par semaine modifiee des cours
     */
    public void setJourSemaine(String jourSemaine) {
        this.jourSemaine = jourSemaine;
    }
    /**
     * Getter pour l'heure de debut d'un cours
     * @return l'heure de debut d'un cours
     */
    public LocalTime getHeureDebut() {
        return heureDebut;
    }
    /**
     * Setter pour l'heure de debut d'uncours
     * @param heureDebut heure de debut de cours modifiee
     */
    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }
    /**
     * Getter pour l'heure de fin d'un cours
     * @return l'heure de fin d'un cours
     */
    public LocalTime getHeureFin() {
        return heureFin;
    }
    /**
     * Setter pour l'heure de fin de cours
     * @param heureFin l'heure de fin d'un cours modifiee
     */
    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }
    /**
     * Getter pour le local d'un cours
     * @return le local d'un cours
     */
    public String getLocal() {
        return local;
    }
    /**
     * Setter pour le local d'un cours
     * @param local local de cours modifiee
     */
    public void setLocal(String local) {
        this.local = local;
    }
    /**
     * Getter pour le pavillon ou un cours a lieu
     * @return pavillon ou le cours a lieu
     */
    public String getPavillon() {
        return pavillon;
    }
    /**
     * Setter pour le pavillon ou un cours a lieu
     * @param pavillon pavillon modifiee
     */
    public void setPavillon(String pavillon) {
        this.pavillon = pavillon;
    }
}
