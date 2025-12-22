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

    /** 
     * Fetch all courses with optional filters
     * @param queryParams Query parameters (courses_sigle, name, description, include_schedule, etc.)
     */
    public List<Course> getAllCourses(Map<String, String> queryParams) {
        Map<String, String> params = (queryParams == null) ? Collections.emptyMap() : queryParams;

        URI uri = HttpClientApi.buildUri(BASE_URL, params);
        System.out.println("Calling Planifium API : " + uri);

        return clientApi.get(uri, new TypeReference<List<Course>>() {});
    }

    /** 
     * Fetch courses by sigles
     * @param sigles List of course codes (e.g., "IFT1015,IFT2255")
     */
    public List<Course> getCoursesBySigles(List<String> sigles) {
        if (sigles == null || sigles.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, String> params = new HashMap<>();
        params.put("courses_sigle", String.join(",", sigles));

        return getAllCourses(params);
    }

    /** 
     * Search courses by name
     * @param name Search term for course name
     */
    public List<Course> searchByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, String> params = new HashMap<>();
        params.put("name", name);

        return getAllCourses(params);
    }

    /** 
     * Search courses by description
     * @param description Search term for course description
     */
    public List<Course> searchByDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, String> params = new HashMap<>();
        params.put("description", description);

        return getAllCourses(params);
    }

    /** 
     * Fetch a course by ID
     */
    public Optional<Course> getCourseById(String courseId) {
        return getCourseById(courseId, null);
    }

    /** 
     * Fetch a course by ID with optional query params (include_schedule, schedule_semester)
     * @param courseId Course code
     * @param queryParams Additional parameters (include_schedule, schedule_semester)
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

    /** 
     * Get course with schedule for a specific semester
     * @param courseId Course code
     * @param semester Semester code (e.g., "a25", "h26")
     */
    public Optional<Course> getCourseWithSchedule(String courseId, String semester) {
        Map<String, String> params = new HashMap<>();
        params.put("include_schedule", "true");
        
        if (semester != null && !semester.trim().isEmpty()) {
            params.put("schedule_semester", semester.toLowerCase());
        }

        return getCourseById(courseId, params);
    }
}