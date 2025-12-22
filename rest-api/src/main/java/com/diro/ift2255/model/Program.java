package com.diro.ift2255.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Program {
    
    @JsonProperty("program_code")
    private String programCode;
    
    private String name;
    private String description;
    
    @JsonProperty("required_courses")
    private List<String> requiredCourses;
    
    @JsonProperty("elective_courses")
    private List<String> electiveCourses;
    
    private int credits;

    public Program() {}

    public Program(String programCode, String name) {
        this.programCode = programCode;
        this.name = name;
    }

    // Getters and Setters
    public String getProgramCode() { return programCode; }
    public void setProgramCode(String programCode) { this.programCode = programCode; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public List<String> getRequiredCourses() { return requiredCourses; }
    public void setRequiredCourses(List<String> requiredCourses) { this.requiredCourses = requiredCourses; }

    public List<String> getElectiveCourses() { return electiveCourses; }
    public void setElectiveCourses(List<String> electiveCourses) { this.electiveCourses = electiveCourses; }

    public int getCredits() { return credits; }
    public void setCredits(int credits) { this.credits = credits; }
}