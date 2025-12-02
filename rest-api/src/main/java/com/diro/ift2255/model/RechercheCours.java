package com.diro.ift2255.model;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.Etudiant;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechercheCours {

    private List<Course> listeCours;
    private Etudiant etudiant;

     public RechercheCours() {}

    public RechercheCours(List<Course> listeCours) {
        this.listeCours = listeCours;
        this.etudiant = null;
    }

    // --- Getter et Setter ---
    public List<Course> getListeCours() {
        return listeCours;
    }

    public void setListeCours(List<Course> listeCours) {
        this.listeCours = listeCours;
    }

    public Etudiant getEtudiant() {
        return etudiant;
    }
    public void setEtudiant(Etudiant etudiant) {
        this.etudiant = etudiant;
    }


    // Personnaliser la rechercher

    public List<Course> personnaliserRecherche(String programme) {

        List<Course> coursPersonnalise = new ArrayList<>();

        // On retourne si aucun etudiant n'est authentifié ou si le programme est vide
        if (etudiant == null || programme == null || programme.isEmpty()) {
            return listeCours; // Retourne la liste complète si l'étudiant ou le programme est nul
        } 

        for (Course cours : listeCours) {
            if (cours.getDescription().contains(programme)) {
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
