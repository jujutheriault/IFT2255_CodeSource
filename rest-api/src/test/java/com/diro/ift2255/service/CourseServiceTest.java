package com.diro.ift2255.service;

import com.diro.ift2255.model.Course;
import com.diro.ift2255.util.HttpClientApi;
import com.fasterxml.jackson.core.type.TypeReference;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.net.URI;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CourseServiceTest {

    @Mock
    private HttpClientApi clientApi;

    @Test
    void getAllCourses_returnListClientApi() {

        CourseService service = new CourseService(clientApi);
        Map<String, String> params = Map.of("CodeProgramme", "909365" );

        Course cours1 = new Course("IFT-2255", "Génie Logiciel");
        Course cours2 = new Course("IFT1025", "Programmation 2");
        List<Course> expected = List.of(cours1, cours2);

        when(clientApi.get(
            any(URI.class),
            ArgumentMatchers.<TypeReference<List<Course>>>any()
        )).thenReturn(expected);

        List<Course> result = service.getAllCourses(params);

        assertEquals(expected, result);
        verify(clientApi, times(1))
            .get(any(URI.class), any(TypeReference.class));

    }

    @Test
    void getAllCourses_withNullParams_useEmptyMap () {
        CourseService service = new CourseService(clientApi);
        List<Course> expected = Collections.emptyList();

        when(clientApi.get(
            any(URI.class), ArgumentMatchers.<TypeReference<List<Course>>>any()
        )).thenReturn(expected);

        List<Course> result = service.getAllCourses(null);

        assertEquals(0, result.size());
        verify(clientApi, times(1))
            .get(any(URI.class), any(TypeReference.class));
    } 

    @Test
    void getCourseById_ReturnCourseIfClientApiSucceed() {

        CourseService service = new CourseService(clientApi);
        String CourseId = "IFT-2255";
        Course course = new Course(CourseId, "Génie Logiciel");

         when(clientApi.get(
            any(URI.class),
            eq(Course.class)
        )).thenReturn(course);

        Optional<Course> result = service.getCourseById(CourseId);

        assertTrue(result.isPresent());
        assertEquals(course, result.get());
        verify(clientApi, times(1))
        .get(any(URI.class), eq(Course.class));
    }

    @Test
    void getCourseById_EmptyIfClientApiThrows() {
        CourseService service = new CourseService(clientApi);
        String CourseId = "MAT1400";

        when(clientApi.get(
            any(URI.class),
            eq(Course.class)
        )).thenThrow(new RuntimeException("Not found"));

        Optional<Course> result = service.getCourseById(CourseId);

        assertTrue(result.isEmpty());
        verify(clientApi, times(1)).get(
            any(URI.class),
            eq(Course.class));
    }

}
