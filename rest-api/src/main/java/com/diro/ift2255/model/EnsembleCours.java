package com.diro.ift2255.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnsembleCours {
    private String idEnsemble;
    private List<String> ensemble = new ArrayList<>();   // Null à sa création
    
    private int max = 6;                                 // Max de cours dans une liste
     
    /**
     * Constructeur d'un ensemble de cours avec identifiant
     * @param idEnsemble identification de l'ensemble
     */
    public EnsembleCours(String idEnsemble) {
        this.idEnsemble = idEnsemble;
    }

    /**
     * Pour retourner l'identifiant de l'ensemble de cours
     * @return l'identifiant de l'ensemble de cours
     */
    public String getId() { return idEnsemble; }

    /**
     * Setter pour l'identifiant de l'ensemble
     * @param idEnsemble l'identifiant de l'ensemble de cours.
     */
    public void setId(String idEnsemble) { this.idEnsemble = idEnsemble; }

    /**
     * Pour retourner la liste de noms de cours de l'ensemble de cours
     * @return la liste de noms de cours de l'ensemble de cours
     */
    public List<String> getList() { return ensemble; }

    /**
     * Setter pour la liste de cours de l'ensemble
     * @param ensemble est le nouvel ensemble a set.
     */
    public void setList(List<String> ensemble) { this.ensemble = ensemble; }


    /**
     * Supprime un cours de la liste de l'ensemble.
     * @param courseId L'identifiant unique du cours à retirer de l'ensemble.
     * @return {@code true} si le cours a été trouvé et retiré avec succès ; 
     * {@code false} si le cours n'était pas présent dans l'ensemble.
     */
    public boolean deleteCourse(String courseId) {
        return this.ensemble.remove(courseId);
    }


    /**
     * Ajoute un nouveau cours à l'ensemble en respectant les contraintes.
     * @param courseId L'identifiant unique du cours à ajouter.
     * @return {@code true} si le cours a été ajouté avec succès ; 
     * {@code false} si la limite est atteinte ou si le cours est déjà présent.
     */
    public boolean addCourse(String courseId) {
        // On vérifie si l'ensemble est plein
        if (this.ensemble.size() >= this.max) { 
            return false;
        } 
        
        // On vérifie si le cours est déjà présent dans l'ensemble
        if (this.ensemble.contains(courseId)) {
            return false;
        }

        return ensemble.add(courseId); 
    }
}

