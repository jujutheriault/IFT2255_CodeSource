package com.diro.ift2255.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechercheCours {

    private List<Course> listeCours;
    private User user;
    /**
     * Constructeur de recherche de cours par defaut
     */
    public RechercheCours() {}
    /**
     * Constructeur de recherche de cours avec parametres
     * @param listeCours la liste de cours
     * @param user l'utilisateur effectuant la recherche
     */
    public RechercheCours(List<Course> listeCours, User user) {
        this.listeCours = listeCours;
        this.user = user;
    }

    // --- Getter et Setter ---
    /**
     * Getter pour la liste de cours
     * @return la liste de cours
     */
    public List<Course> getListeCours() {
        return listeCours;
    }
    /**
     * Setter pour la liste de cours
     * @param listeCours la liste de cours modifiee
     */
    public void setListeCours(List<Course> listeCours) {
        this.listeCours = listeCours;
    }
    /**
     * Getter pour l'utilisateur
     * @return l'utilisateur  pour la recherche de cours
     */
    public User getUser() {
        return user;
    }
    /**
     * Setter pour l'utilisatuer
     * @param user l"utilisateur pour la recherche de cours modifie
     */
    public void setUser(User user) {
        this.user = user;
    }

    // Recherche de cours parsigle partiel, ou mot-clé
    /**
     * recher de cours par mots-cles
     * @param motRecherche mot utilise pour la recherche
     * @return la liste des cours correspondant aux parametres de recherche
     */
    public List<Course> rechercher(String motRecherche){

        // Si le mot de recherche est vide, on retourne la liste complète
        if (motRecherche == null || motRecherche.isEmpty()) {
            return listeCours;
        }
                
        List<Course> resultat = new ArrayList<>();

        for (Course cours : listeCours) {
            if (
                (cours.getId() != null) && cours.getId().toLowerCase().contains(motRecherche.toLowerCase())
                || (cours.getName() != null) && cours.getName() != null && cours.getName().toLowerCase().contains(motRecherche.toLowerCase())
                || ((cours.getDescription() != null) && cours.getDescription() != null && cours.getDescription().toLowerCase().contains(motRecherche.toLowerCase()))
            ) {
                resultat.add(cours);
            }
        }
        return resultat;
    }


    // Personnaliser la rechercher

    /* public List<Course> personnaliserRecherche() {

        List<Course> coursPersonnalise = new ArrayList<>();

        // On retourne si aucun user n'est pas un étudiant
        if (!(user instanceof Etudiant etudiant)) {
            return listeCours;
        }

        // Retourne la liste complète si l'étudiant ou le programme est nul
        if (etudiant.getProgramme == null) {
            return listeCours;
        }

        String programme = etudiant.getProgramme();

        for (Course cours : listeCours) {
            if (cours.getId().contains(programme)) {
                coursPersonnalise.add(cours);
            }
        }
        return coursPersonnalise;
    } */


    // Filtrer une recherche par id
    /**
     * Filtrage d'une recherche par identification 
     * @param idPart l'identification d'un cours
     * @return liste des cours filtree par identification
     */
    public List<Course> filtrerIdPart(String idPart) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getId().contains(idPart)) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

    // Filtrer une recherche par crédit
    /**
     * filtrage d'un cours par credits
     * @param credits le nombre de credits d'un cours
     * @return liste de cours avec les credits choisis
     */
    public List<Course> filtrerCredit(int credits) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getCredits() == credits) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

    // Filtrer une recherche par terme disponible
    /**
     * filtrage d'un cours par les termes de recherche
     * @param term termes de recherche
     * @return liste des cours correspôndants aux termes de la recherche
     */
    public List<Course> filtrerTermAvailable(String term) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getTerms().get(term) != null) {
                filtre.add(cours);   
            }
        }
        return filtre;
    }

    // Filtrer une recherche par charge de travail
    /**
     * filtrage d'un cours par la charge de travail
     * @param chargeTravail la de travail d'un cours en heures
     * @return liste des cours correspondants a la charge de travail entree
     */
    public List<Course> filtrerChargeTravail(int chargeTravail) {
        List<Course> filtre = new ArrayList<>();

        for (Course cours : listeCours) {
            if (cours.getChargeTravail() == chargeTravail) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

}
