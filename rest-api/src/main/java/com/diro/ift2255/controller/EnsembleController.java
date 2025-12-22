package com.diro.ift2255.controller;

import io.javalin.http.Context;             
import com.diro.ift2255.model.EnsembleCours; 
import com.diro.ift2255.util.ResponseUtil;   
import java.util.Map;                 
import java.util.HashMap;


public class EnsembleController {
    // Simulation de base de données pour garder les ensemble de cours en mémoire
    private final Map<String, EnsembleCours> database = new HashMap<>();

    /**
     * Crée un nouvel ensemble de cours vide dans le système.
     * @param ctx Le contexte HTTP Javalin contenant le paramètre de chemin 'idEnsemble'.
     * @see EnsembleCours
     * * @status 201 (Created) si l'ensemble est créé avec succès, retourne l'objet JSON.
     * @status 400 (Bad Request) si un ensemble avec cet identifiant existe déjà.
     */
    public void createEnsemble(Context ctx) {
        String idEns = ctx.pathParam("idEnsemble"); 

        if (database.containsKey(idEns)) {
            ctx.status(400).json(ResponseUtil.formatError("L'ensemble '" + idEns + "' existe déjà."));
            return;
        }

        EnsembleCours nouvelEnsemble = new EnsembleCours(idEns);
        database.put(idEns, nouvelEnsemble);
        
        ctx.status(201).json(nouvelEnsemble); // 201 = Créé
    }

    /**
     * Ajoute un cours spécifique à un ensemble de cours existant.
     * @param ctx Le contexte HTTP Javalin contenant les paramètres :
     * <ul>
     * <li>{@code idEnsemble} : L'identifiant de l'ensemble cible.</li>
     * <li>{@code courseId} : L'identifiant du cours à ajouter.</li>
     * </ul>
     * * @return Un objet JSON représentant l'état mis à jour de l'ensemble.
     * * @status 200 (OK) Si le cours a été ajouté avec succès.
     * @status 400 (Bad Request) Si l'ID du cours est invalide, si le cours est déjà 
     * présent, ou si la limite maximale de cours est atteinte.
     * @status 404 (Not Found) Si l'ensemble spécifié n'existe pas dans le système.
     */
    public void addCourse(Context ctx) {
        String idEns = ctx.pathParam("idEnsemble");
        String courseId = ctx.pathParam("courseId");

        EnsembleCours ensemble = database.get(idEns);
        
        // Vérification de l'existence de l'ensemble
        if (ensemble == null) {
            ctx.status(404).json(ResponseUtil.formatError("Action impossible : L'ensemble '" + idEns + "' n'existe pas. Veuillez le créer d'abord."));
            return;
        }

        // Validation de l'id du cours à ajouter
        if (!validateCourseId(courseId)) {
            ctx.status(400).json(ResponseUtil.formatError("Le paramètre id n'est pas valide."));
            return;
        }

        // Tentative d'ajout
        if (ensemble.addCourse(courseId)) {
            ctx.status(200).json(ensemble);
        } else {
            ctx.status(400).json(ResponseUtil.formatError("Échec de l'ajout : Le cours est déjà présent ou la limite de " + ensemble.getList().size() + " cours est atteinte."));
        }
    }

    /**
     * Vérifie que l'ID du cours est bien formé
     * @param courseId L'ID du cours à valider
     * @return Valeur booléeene indiquant si l'ID est valide
     */
    private boolean validateCourseId(String courseId) {
        return courseId != null && courseId.trim().length() >= 6;
    }


    /**
     * Supprime un cours spécifique d'un ensemble existant.
     * @param ctx Le contexte HTTP Javalin contenant les paramètres de chemin :
     * <ul>
     * <li>{@code idEnsemble} : L'identifiant de l'ensemble duquel retirer le cours.</li>
     * <li>{@code courseId} : L'identifiant du cours à supprimer.</li>
     * </ul>
     * * @status 200 (OK) Si le cours a été supprimé avec succès de l'ensemble.
     * @status 404 (Not Found) Si l'ensemble n'existe pas ou si le cours spécifié 
     * ne fait pas partie de cet ensemble.
     */
    public void deleteCourse(Context ctx) {
        String idEns = ctx.pathParam("idEnsemble");
        String courseId = ctx.pathParam("courseId");

        EnsembleCours ensemble = database.get(idEns);

        // Vérification de l'existence de l'ensemble
        if (ensemble == null) {
            ctx.status(404).json(ResponseUtil.formatError("Action impossible : L'ensemble '" + idEns + "' est introuvable."));
            return;
        }

        // Tentative de suppression
        if (ensemble.deleteCourse(courseId)) {
            ctx.status(200).json(ensemble);
        } else {
            ctx.status(404).json(ResponseUtil.formatError("Le cours '" + courseId + "' n'est pas dans l'ensemble '" + idEns + "'."));
        }
    }


    /**
     * Récupère et retourne les détails d'un ensemble de cours spécifique.
     * @param ctx Le contexte HTTP Javalin contenant le paramètre de chemin :
     * <ul>
     * <li>{@code idEnsemble} : L'identifiant unique de l'ensemble de cours à consulter.</li>
     * </ul>
     *
     * @status 200 (OK) Si l'ensemble existe, retourne l'objet {@link EnsembleCours} complet.
     * @status 404 (Not Found) Si aucun ensemble ne correspond à l'identifiant fourni.
     * @see EnsembleCours
     */
    public void getEnsembleById(Context ctx) {
        String idEns = ctx.pathParam("idEnsemble");
        EnsembleCours ensemble = database.get(idEns);

        if (ensemble != null) {
            ctx.status(200).json(ensemble);
        } else {
            ctx.status(404).json(ResponseUtil.formatError("L'ensemble '" + idEns + "' n'existe pas."));
        }
    }
}