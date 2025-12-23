package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
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


}
