package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.model.User;

public class EligibiliteService {

    /**
     * Vérifie si un étudiant est éligible à un cours
     * @param user L'étudiant
     * @param course Le cours
     * @return true si éligible
     */
    public static boolean estEligible(User user, Course course) {
        if (user == null || course == null) {
            return false;
        }

        // Extraire le cycle du cours depuis son sigle
        int cycleCours = extraireCycleCours(course.getId());
        int cycleEtudiant = user.getCycle();

        // Un étudiant peut prendre un cours de son cycle ou inférieur
        // Ex: Un étudiant de 2e cycle peut prendre des cours de 1er cycle
        return cycleEtudiant >= cycleCours;
    }

    /**
     * Extrait le cycle d'un cours depuis son sigle
     * @param sigle Le sigle du cours (ex: IFT2255)
     * @return 1 (1er cycle), 2 (2e cycle), ou 0 si invalide
     */
    private static int extraireCycleCours(String sigle) {
        if (sigle == null || sigle.length() < 4) {
            return 0;
        }

        try {
            // Extraire les chiffres (ex: "IFT2255" -> "2255")
            String chiffres = sigle.replaceAll("[^0-9]", "");
            
            if (chiffres.isEmpty()) {
                return 0;
            }

            int niveau = Integer.parseInt(chiffres.substring(0, 1));

            // Déterminer le cycle
            if (niveau >= 1 && niveau <= 5) {
                return 1; // 1er cycle (1000-5999)
            } else if (niveau >= 6) {
                return 2; // 2e cycle (6000+)
            }

        } catch (Exception e) {
            return 0;
        }

        return 0;
    }

    /**
     * Obtient une description du cycle d'un cours
     * @param sigle Le sigle du cours
     * @return Description du cycle
     */
    public static String getDescriptionCycle(String sigle) {
        int cycle = extraireCycleCours(sigle);
        
        switch (cycle) {
            case 1:
                return "1er cycle (Baccalauréat)";
            case 2:
                return "2e cycle (Maîtrise/Doctorat)";
            default:
                return "Cycle inconnu";
        }
    }
}
