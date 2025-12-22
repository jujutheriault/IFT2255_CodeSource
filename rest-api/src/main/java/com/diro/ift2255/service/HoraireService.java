package com.diro.ift2255.service;

import com.diro.ift2255.model.Horaire;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class HoraireService {

    /**
     * Récupère l'horaire d'un utilisateur (mock pour le moment)
     * @param userId L'ID de l'utilisateur
     * @return Liste des horaires de l'utilisateur
     */
    public List<Horaire> getHoraireByUserId(int userId) {
        // ✅ Données fictives pour la démo
        // Plus tard, tu pourras charger depuis un CSV ou une base de données
        
        List<Horaire> horaires = new ArrayList<>();

        if (userId == 1) { // Alice
            horaires.add(new Horaire(
                "IFT1015", 
                2025, 
                "Lundi", 
                LocalTime.of(8, 30), 
                LocalTime.of(10, 30), 
                "1140", 
                "Pavillon André-Aisenstadt"
            ));
            
            horaires.add(new Horaire(
                "IFT2255", 
                2025, 
                "Mercredi", 
                LocalTime.of(13, 0), 
                LocalTime.of(15, 0), 
                "3190", 
                "Pavillon André-Aisenstadt"
            ));
            
            horaires.add(new Horaire(
                "IFT1025", 
                2025, 
                "Vendredi", 
                LocalTime.of(10, 30), 
                LocalTime.of(12, 30), 
                "1140", 
                "Pavillon André-Aisenstadt"
            ));
        } else if (userId == 2) { // Bob
            horaires.add(new Horaire(
                "IFT2015", 
                2025, 
                "Mardi", 
                LocalTime.of(9, 0), 
                LocalTime.of(11, 0), 
                "2120", 
                "Pavillon André-Aisenstadt"
            ));
            
            horaires.add(new Horaire(
                "IFT2125", 
                2025, 
                "Jeudi", 
                LocalTime.of(14, 0), 
                LocalTime.of(16, 0), 
                "3120", 
                "Pavillon André-Aisenstadt"
            ));
        }

        return horaires;
    }

    /**
     * Vérifie si un utilisateur a un horaire
     * @param userId L'ID de l'utilisateur
     * @return true si l'utilisateur a au moins un cours
     */
    public boolean hasHoraire(int userId) {
        return !getHoraireByUserId(userId).isEmpty();
    }
}