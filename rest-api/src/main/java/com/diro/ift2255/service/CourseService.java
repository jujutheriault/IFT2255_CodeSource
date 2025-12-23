package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.util.*;

public class CourseService {
    private final HttpClientApi clientApi;
    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1/courses";

    public CourseService(HttpClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /** Fetch all courses */
    public List<Course> getAllCourses(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;

        URI uri = HttpClientApi.buildUri(BASE_URL, params);
        System.out.println("Calling Planifium API : " + uri);

        return clientApi.get(uri, new TypeReference<List<Course>>() {});
    }

    /** Fetch a course by ID */
    public Optional<Course> getCourseById(String courseId) {
        return getCourseById(courseId, null);
    }

    /** Fetch a course by ID with optional query params */
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

    /** Courses by program */
    public List<String> getCoursesByProgram(Map<String, String> queryParams) {

        Map<String, String> params =
                (queryParams == null) ? Collections.emptyMap() : queryParams;

        String PROGRAM_URL = "https://planifium-api.onrender.com/api/v1/programs";
        URI uri = HttpClientApi.buildUri(PROGRAM_URL, params);

        try {
            List<Map<String, Object>> programs =
                    clientApi.get(uri, new TypeReference<>() {});

            if (programs.isEmpty()) return Collections.emptyList();

            @SuppressWarnings("unchecked")
            List<String> courses =
                    (List<String>) programs.get(0).get("courses");

            return (courses == null) ? Collections.emptyList() : courses;

        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

    /* Courses by program and semester */
    public List<Course> getCoursesByProgramAndSemester(Map<String, String> queryParams, String semester) {

        // 1) récupérer les sigles du programme
        List<String> sigles = getCoursesByProgram(queryParams);
        System.out.println("[DEBUG] Sigles récupérés pour le programme : " + sigles);
        if (sigles.isEmpty()) return Collections.emptyList();

        // 2) construire query params pour l'endpoint courses
        Map<String, String> params = new HashMap<>();
        params.put("courses_sigle", String.join(",", sigles).toLowerCase());
        params.put("schedule_semester", semester.toLowerCase()); // ex a25
        params.put("include_schedule", "true");

        String COURSES_URL = "https://planifium-api.onrender.com/api/v1/courses";
        URI uri = HttpClientApi.buildUri(COURSES_URL, params);

        System.out.println("[DEBUG] Appel Planifium URI : " + uri);

        try {
            // Cet endpoint devrait renvoyer directement une liste d'objets cours
            return clientApi.get(uri, new TypeReference<List<Course>>() {});

        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

}
