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
    public List<Course> getCoursesByProgram(Map<String, String> queryParams) {

        Map<String, String> params =
                (queryParams == null) ? Collections.emptyMap() : queryParams;

        String PROGRAM_URL = "https://planifium-api.onrender.com/api/v1/programs";
        URI uri = HttpClientApi.buildUri(PROGRAM_URL, params);

        try {
            Map<String, Object> response =
                    clientApi.get(uri, new TypeReference<>() {});

            Object rawCourses = response.get("courses");
            if (!(rawCourses instanceof List<?> list)) return Collections.emptyList();

            // IMPORTANT: ici, list contient des Map (pas des Course directement)
            ObjectMapper mapper = new ObjectMapper();

            List<Course> courses = new ArrayList<>();
            for (Object item : list) {
                Course c = mapper.convertValue(item, Course.class);
                courses.add(c);
            }

            return courses;

        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }

}
