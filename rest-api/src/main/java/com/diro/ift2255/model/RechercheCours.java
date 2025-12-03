package com.diro.ift2255.model;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.Etudiant;
import com.diro.ift2255.model.User;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechercheCours {

    private List<Course> listeCours;
    private User user;

     public RechercheCours() {}

    public RechercheCours(List<Course> listeCours, User user) {
        this.listeCours = listeCours;
        this.user = user;
    }

    // --- Getter et Setter ---
    public List<Course> getListeCours() {
        return listeCours;
    }

    public void setListeCours(List<Course> listeCours) {
        this.listeCours = listeCours;
    }

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


    // Personnaliser la rechercher

    public List<Course> personnaliserRecherche() {

        List<Course> coursPersonnalise = new ArrayList<>();

        // On retourne si aucun user n'est pas un étudiant
        if (!(user instanceof Etudiant etudiant)) {
            return listeCours;
        }

        // Retourne la liste complète si l'étudiant ou le programme est nul
        if (etudiant.getProgramme() == null) {
            return listeCours;
        }

        String programme = etudiant.getProgramme();

        for (Course cours : listeCours) {
            if (cours.getId().contains(programme)) {
                coursPersonnalise.add(cours);
            }
        }
        return coursPersonnalise;
    }


    // Filtrer une recherche par id
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
