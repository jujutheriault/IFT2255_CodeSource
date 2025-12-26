package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.*;

public class CourseService {
    private final HttpClientApi clientApi;

    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1/courses";
    private static final String PROGRAMS_URL = "https://planifium-api.onrender.com/api/v1/programs";

    private final ObjectMapper mapper = new ObjectMapper();

    public CourseService(HttpClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /** recupere tous les cours 
     * @param queryParams les parametres de requete
     * @return une liste de cours 
    */
    public List<Course> getAllCourses(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;

        URI uri = HttpClientApi.buildUri(BASE_URL, params);
        System.out.println("Calling Planifium API : " + uri);

        return clientApi.get(uri, new TypeReference<List<Course>>() {});
    }

    /** recupere un cours par son ID
     * @param courseId l'identifiant du cours
     * @return un appel à la fonction getCourseById 
     */
    public Optional<Course> getCourseById(String courseId) {
        return getCourseById(courseId, null);
    }

    /** recupere un cours par ID selon les parametres de requete 
     * @param courseId l'identifiant du cours
     * @param queryParams les parametres de requete
     * @return un appel à la fonction Optional pour un choix de cours
    */
    public Optional<Course> getCourseById(String courseId, Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;
        URI uri = HttpClientApi.buildUri(BASE_URL + "/" + courseId, params);

        try {
            Course course = clientApi.get(uri, Course.class);
            return Optional.of(course);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    public String normalizeSemester(String input) {
        if (input == null) return null;
        String s = input.trim();

        if (s.matches("^[HAEhae]\\d{2}$")) {
            return ("" + Character.toLowerCase(s.charAt(0)) + s.substring(1));
        }
        
        if (s.matches("^[hae]\\d{2}$")) {
            return s.toLowerCase();
        }

        throw new IllegalArgumentException(
                "Format de trimestre invalide: " + input + " (attendu: H25, A24, E24 ou a25/h25/e24)"
        );
    }

    public JsonNode getProgram(String programId, boolean includeCoursesDetail, String responseLevel) {
        Map<String, String> params = new HashMap<>();
        params.put("programs_list", programId);

        if (includeCoursesDetail) {
            params.put("include_courses_detail", "true");
        }
        if (responseLevel != null && !responseLevel.isBlank()) {
            params.put("response_level", responseLevel); // min/reg/full si supporté
        }

        URI uri = HttpClientApi.buildUri(PROGRAMS_URL, params);

        // On récupère la réponse brute comme String JSON, puis on parse en JsonNode
        String rawJson = clientApi.get(uri, String.class);
        try {
            return mapper.readTree(rawJson);
        } catch (Exception e) {
            throw new RuntimeException("Impossible de parser la réponse Planifium (/programs).", e);
        }
    }

    /* Courses by program and semester */
    public List<Course> getCoursesByProgramAndSemester(String programId, String semesterNormalized) {
        // 1) Récupérer les infos programme avec détails cours
        JsonNode programJson = getProgram(programId, true, "reg");

        // 2) Déterminer la saison (autumn/winter/summer)
        String seasonKey = seasonFromSemester(semesterNormalized);

        // 3) Extraire tous les cours depuis la réponse JSON
        List<Course> allCourses = extractCoursesFromProgramJson(programJson);

        // 4) Filtrer via available_terms
        List<Course> filtered = new ArrayList<>();
        for (Course c : allCourses) {
            Map<String, Boolean> terms = c.getTerms(); // available_terms
            if (terms != null && Boolean.TRUE.equals(terms.get(seasonKey))) {
                filtered.add(c);
            }
        }
        return filtered;
    }

    private String seasonFromSemester(String sem) {
        char c = Character.toLowerCase(sem.charAt(0));
        return switch (c) {
            case 'a' -> "autumn";
            case 'h' -> "winter";
            case 'e' -> "summer";
            default -> throw new IllegalArgumentException("Trimestre invalide: " + sem);
        };
    }

    /**
     * Extrait un tableau de cours depuis la réponse /programs.
     * On est tolérant parce que la structure exacte peut varier.
     */
    private List<Course> extractCoursesFromProgramJson(JsonNode programJson) {
        try {
            JsonNode coursesNode;

            if (programJson == null) return Collections.emptyList();

            // Cas 1: réponse = tableau de programmes
            if (programJson.isArray() && programJson.size() > 0) {
                coursesNode = findCoursesArray(programJson.get(0));
            } else {
                // Cas 2: réponse = objet programme
                coursesNode = findCoursesArray(programJson);
            }

            if (coursesNode == null || !coursesNode.isArray()) {
                return Collections.emptyList();
            }

            return mapper.convertValue(coursesNode, new TypeReference<List<Course>>() {});
        } catch (Exception e) {
            throw new RuntimeException("Impossible d'extraire les cours depuis la réponse Planifium (/programs).", e);
        }
    }

    /**
     * Cherche un tableau de cours dans des champs possibles.
     * Si tu as un 200 mais une liste vide, colle-moi le JSON et on ajuste les clés ici.
     */
    private JsonNode findCoursesArray(JsonNode programNode) {
        if (programNode == null) return null;

        String[] keys = {
                "courses",
                "courses_detail",
                "courses_detail_list",
                "coursesDetails",
                "course_list"
        };

        for (String k : keys) {
            JsonNode n = programNode.get(k);
            if (n != null && n.isArray()) return n;
        }

        // parfois sous "details"
        JsonNode details = programNode.get("details");
        if (details != null) {
            for (String k : keys) {
                JsonNode n = details.get(k);
                if (n != null && n.isArray()) return n;
            }
        }

        return null;
    }

    public Optional<Course> getCourseWithScheduleForSemester(String courseId, String semesterNormalized) {
        Map<String, String> params = new HashMap<>();
        params.put("include_schedule", "true");
        params.put("schedule_semester", semesterNormalized); // ex: h25 / a25 / e24

        return getCourseById(courseId, params);
    }

}