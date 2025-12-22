package com.diro.ift2255.service;

import com.diro.ift2255.model.ResultatAgrege;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ResultatService {
    
    private static final String CSV_PATH = "src/main/java/com/diro/ift2255/data/resultat_agreges.csv";
    private Map<String, ResultatAgrege> resultatsCache;

    public ResultatService() {
        this.resultatsCache = new HashMap<>();
        chargerDonnees();
    }

    /**
     * Charge les données du CSV en mémoire
     */
    private void chargerDonnees() {
        try (BufferedReader br = new BufferedReader(new FileReader(CSV_PATH))) {
            String line;
            boolean isFirstLine = true;

            while ((line = br.readLine()) != null) {
                // Ignorer la ligne d'en-tête
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }

                // ✅ Parse correctement les lignes avec guillemets
                String[] values = parseCsvLineWithQuotes(line);
                
                if (values.length >= 6) {
                    try {
                        String sigle = values[0].trim();
                        String nom = values[1].trim();
                        String moyenne = values[2].trim();
                        double score = Double.parseDouble(values[3].trim());
                        int participants = Integer.parseInt(values[4].trim());
                        int trimestres = Integer.parseInt(values[5].trim());

                        ResultatAgrege resultat = new ResultatAgrege(sigle, nom, moyenne, score, participants, trimestres);
                        resultatsCache.put(sigle, resultat);
                    } catch (NumberFormatException e) {
                        // Ignorer silencieusement les lignes mal formatées
                    }
                }
            }


        } catch (IOException e) {
            System.err.println("❌ Erreur lors du chargement du fichier CSV : " + e.getMessage());
        }
    }

    /**
     * Parse une ligne CSV en gérant correctement les guillemets
     * @param line La ligne à parser
     * @return Tableau de valeurs
     */
    private String[] parseCsvLineWithQuotes(String line) {
        // Simple regex pour gérer les virgules dans les guillemets
        // Exemple: EDP1504,"Lecture, écriture et grammaire du texte",B,3.64,7,5
        return line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
    }

    /**
     * Récupère les résultats agrégés pour un cours donné
     * @param sigle Le sigle du cours (ex: IFT2255)
     * @return Optional contenant les résultats ou vide si non trouvé
     */
    public Optional<ResultatAgrege> getResultatBySigle(String sigle) {
        if (sigle == null || sigle.isEmpty()) {
            return Optional.empty();
        }
        return Optional.ofNullable(resultatsCache.get(sigle.toUpperCase()));
    }

    /**
     * Vérifie si un cours a des résultats disponibles
     * @param sigle Le sigle du cours
     * @return true si des résultats existent
     */
    public boolean hasResultat(String sigle) {
        return sigle != null && resultatsCache.containsKey(sigle.toUpperCase());
    }
}