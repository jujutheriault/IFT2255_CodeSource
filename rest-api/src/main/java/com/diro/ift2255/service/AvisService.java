package com.diro.ift2255.service;

import com.diro.ift2255.model.AvisEtudiant;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AvisService {
    
    private static final String JSON_PATH = "src/main/java/com/diro/ift2255/data/avis_etudiants.json";
    private Map<String, List<AvisEtudiant>> avisCache;
    private ObjectMapper objectMapper;

    public AvisService() {
        this.objectMapper = new ObjectMapper();
        this.avisCache = new HashMap<>();
        chargerDonnees();
    }

    private void chargerDonnees() {
        try {
            File file = new File(JSON_PATH);
            if (!file.exists()) {
                System.err.println("⚠️  Fichier avis_etudiants.json non trouvé");
                return;
            }

            // Lire tout le JSON comme une liste d'avis
            List<AvisEtudiant> avisList = objectMapper.readValue(file, new TypeReference<List<AvisEtudiant>>() {});

            // Grouper par sigle
            avisCache = avisList.stream()
                .collect(Collectors.groupingBy(
                    avis -> avis.getSigle().toUpperCase(),
                    Collectors.toList()
                ));

        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement des avis : " + e.getMessage());
        }
    }

    public List<AvisEtudiant> getAvisBySigle(String sigle) {
        if (sigle == null || sigle.isEmpty()) {
            return new ArrayList<>();
        }
        return avisCache.getOrDefault(sigle.toUpperCase(), new ArrayList<>());
    }

    public double getNoteMoyenne(String sigle) {
        List<AvisEtudiant> avis = getAvisBySigle(sigle);
        if (avis.isEmpty()) return 0.0;
        
        double somme = avis.stream().mapToInt(AvisEtudiant::getNote).sum();
        return somme / avis.size();
    }

    public boolean hasAvis(String sigle) {
        return sigle != null && avisCache.containsKey(sigle.toUpperCase());
    }

    /**
     * Recharge les données depuis le fichier JSON
     * Utile après qu'un nouvel avis a été ajouté par le bot Discord
     */
    public void rechargerDonnees() {
        avisCache.clear();
        chargerDonnees();
    }
}