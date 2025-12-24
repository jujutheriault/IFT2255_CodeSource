package com.diro.ift2255.model;

import java.nio.charset.StandardCharsets;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.nio.charset.StandardCharsets;


public class CourseAggregates {
    private final String moyenne;     
    private final Double score;        
    private final int participants;  
    private final int trimestres;  

    public CourseAggregates(String moyenne, Double score, int participants, int trimestres) {
        this.moyenne = moyenne;
        this.score = score;
        this.participants = participants;
        this.trimestres = trimestres;
    }

    public String getMoyenne() { return moyenne; }
    public Double getScore() { return score; }
    public int getParticipants() { return participants; }
    public int getTrimestres() { return trimestres; }

    /**
     * Charge les résultats agrégés d’un cours à partir d’un fichier CSV situé dans les resources
     * de l’application, et retourne les agrégats correspondant au sigle du cours fourni.
     * Si aucune ligne ne correspond au sigle fourni, si le fichier CSV est introuvable ou si
     * une erreur survient lors de la lecture, la méthode retourne {@code null}.
     *
     * @param courseId l’identifiant du cours (sigle) pour lequel charger les résultats agrégés
     * @return un objet {@link CourseAggregates} contenant la moyenne, le score, le nombre de
     *         participants et le nombre de trimestres associés au cours, ou {@code null} si
     *         aucun résultat n’est trouvé
    */
    public static CourseAggregates loadFromCsvResource(String courseId) {
        try (InputStream is = CourseAggregates.class
                .getClassLoader()
                .getResourceAsStream("data/resultat_agreges.csv")) {

            if (is == null) {
                System.err.println("CSV introuvable dans resources");
                return null;
            }

            BufferedReader br = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
            String line = br.readLine(); // header

            while ((line = br.readLine()) != null) {
                String[] cols = splitCsv(line);
                if (cols.length < 6) continue;

                String sigle = cols[0].replace("\uFEFF", "").trim();

                if (sigle.equalsIgnoreCase(courseId.trim())) {
                    return new CourseAggregates(
                        cols[2].trim(),                       // moyenne
                        Double.parseDouble(cols[3].trim()),  // score
                        Integer.parseInt(cols[4].trim()),    // participants
                        Integer.parseInt(cols[5].trim())     // trimestres
                    );
                }
            }

        } catch (Exception e) {
            System.err.println("Erreur lecture CSV agrégés: " + e.getMessage());
        }
        return null;
    }

    /**
     * Découpe une ligne de fichier CSV en colonnes, en tenant compte des champs
     * entourés de guillemets doubles.
     * @param line la ligne CSV à découper
     * @return un tableau de chaînes représentant les différentes colonnes de la ligne
     */
    private static String[] splitCsv(String line) {
        StringBuilder cur = new StringBuilder();
        boolean inQuotes = false;
        java.util.ArrayList<String> out = new java.util.ArrayList<>();

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '"') {
                inQuotes = !inQuotes;
                continue;
            }

            if (ch == ',' && !inQuotes) {
                out.add(cur.toString());
                cur.setLength(0);
            } else {
                cur.append(ch);
            }
        }
        out.add(cur.toString());

        return out.toArray(new String[0]);
    }
}
