package com.diro.ift2255.model;

import com.diro.ift2255.model.Course;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechercheCours {

    private List<Course> listeCours;

     public RechercheCours() {}

    public RechercheCours(List<Course> listeCours) {
        this.listeCours = listeCours;
    }

    // --- Getter et Setter ---
    public List<Course> listeCours() {
        return listeCours;
    }

    public void setCours(List<Course> ListeCours) {
        this.listeCours = listeCours;
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
    public List<Course> filtrerNamePart(int credits) {
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
