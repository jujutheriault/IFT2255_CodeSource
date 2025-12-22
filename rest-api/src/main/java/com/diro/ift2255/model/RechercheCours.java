package com.diro.ift2255.model;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class RechercheCours {

    private List<Course> listeCours;
    private User user;

    public RechercheCours() {}

    public RechercheCours(List<Course> listeCours, User user) {
        this.listeCours = listeCours;
        this.user = user;
    }

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

    // Recherche par mot-clé
    public List<Course> rechercher(String motRecherche){
        if (motRecherche == null || motRecherche.isEmpty()) {
            return listeCours;
        }
                
        List<Course> resultat = new ArrayList<>();

        for (Course cours : listeCours) {
            if (
                (cours.getId() != null) && cours.getId().toLowerCase().contains(motRecherche.toLowerCase())
                || (cours.getName() != null) && cours.getName().toLowerCase().contains(motRecherche.toLowerCase())
                || ((cours.getDescription() != null) && cours.getDescription().toLowerCase().contains(motRecherche.toLowerCase()))
            ) {
                resultat.add(cours);
            }
        }
        return resultat;
    }

    // Filtrer par ID partiel
    public List<Course> filtrerIdPart(String idPart) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getId().contains(idPart)) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

    // Filtrer par crédits
    public List<Course> filtrerCredit(int credits) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getCredits() == credits) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

    // Filtrer par terme disponible
    public List<Course> filtrerTermAvailable(String term) {
        List<Course> filtre = new ArrayList<>();
        for (Course cours : listeCours) {
            if (cours.getTerms().get(term) != null) {
                filtre.add(cours);   
            }
        }
        return filtre;
    }

    // Filtrer par charge de travail
    public List<Course> filtrerChargeTravail(int chargeTravail) {
        List<Course> filtre = new ArrayList<>();

        for (Course cours : listeCours) {
            if (cours.getChargeTravail() == chargeTravail) {
                filtre.add(cours);
            }
        }
        return filtre;
    }

    public List<Course> filtrerParTrimestre(String trimestre) {
    List<Course> filtre = new ArrayList<>();
    
    if (trimestre == null || trimestre.isEmpty()) {
        return listeCours;
    }

    String session = convertirTrimestreEnSession(trimestre);
    
    System.out.println("DEBUG: trimestre = " + trimestre);
    System.out.println("DEBUG: session convertie = " + session);
    System.out.println("DEBUG: Nombre total de cours dans listeCours = " + listeCours.size()); // ✅ AJOUTE
    
    if (session == null) {
        System.out.println("DEBUG: session est null!");
        return filtre;
    }

    int count = 0;
    for (Course cours : listeCours) {
        if (count < 3) { // ✅ Afficher les 3 premiers cours peu importe
            System.out.println("DEBUG: Cours " + cours.getId() + " - terms = " + cours.getTerms());
            count++;
        }
        
        if (cours.getTerms() != null) {
            Boolean isAvailable = cours.getTerms().get(session);
            
            if (Boolean.TRUE.equals(isAvailable)) {
                filtre.add(cours);
            }
        }
    }
    
    System.out.println("DEBUG: Nombre de cours trouvés = " + filtre.size());
    return filtre;
}


    // ✅ MÉTHODE MISE À JOUR : Convertir format trimestre (ignore l'année)
private String convertirTrimestreEnSession(String trimestre) {
    if (trimestre == null || trimestre.isEmpty()) {
        return null;
    }

    // Prendre juste la première lettre
    char saison = trimestre.toUpperCase().charAt(0);
    
    switch (saison) {
        case 'H': // Hiver
            return "winter";
        case 'A': // Automne
            return "autumn";
        case 'E': // Été
            return "summer";
        default:
            return null;
    }
}
}