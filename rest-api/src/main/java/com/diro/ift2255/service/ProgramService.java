package com.diro.ift2255.service;

import com.diro.ift2255.model.Program;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
import java.net.URI;
import java.util.*;

public class ProgramService {
    private final HttpClientApi clientApi;
    private static final String BASE_URL = "https://planifium-api.onrender.com/api/v1/programs";

    public ProgramService(HttpClientApi clientApi) {
        this.clientApi = clientApi;
    }

    /**
     * Get program by ID
     * @param programId Program code (e.g., "117510")
     */
    public Optional<Program> getProgramById(String programId) {
        return getProgramById(programId, false);
    }

    /**
     * Get program by ID with optional course details
     * @param programId Program code
     * @param includeCoursesDetail Include full course details
     */
    public Optional<Program> getProgramById(String programId, boolean includeCoursesDetail) {
        Map<String, String> params = new HashMap<>();
        params.put("programs_list", programId);
        
        if (includeCoursesDetail) {
            params.put("include_courses_detail", "true");
        }

        URI uri = HttpClientApi.buildUri(BASE_URL, params);
        
        try {
            List<Program> programs = clientApi.get(uri, new TypeReference<List<Program>>() {});
            return programs.isEmpty() ? Optional.empty() : Optional.of(programs.get(0));
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    /**
     * Get multiple programs
     * @param programIds List of program codes
     */
    public List<Program> getProgramsByIds(List<String> programIds) {
        if (programIds == null || programIds.isEmpty()) {
            return Collections.emptyList();
        }

        Map<String, String> params = new HashMap<>();
        params.put("programs_list", String.join(",", programIds));

        URI uri = HttpClientApi.buildUri(BASE_URL, params);
        
        try {
            return clientApi.get(uri, new TypeReference<List<Program>>() {});
        } catch (RuntimeException e) {
            return Collections.emptyList();
        }
    }
}