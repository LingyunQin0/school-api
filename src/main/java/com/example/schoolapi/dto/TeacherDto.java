package com.example.schoolapi.dto;

import java.util.HashSet;
import java.util.Set;

public class TeacherDto {
    private Long id;
    private String name;
    private Set<StudentDto> students = new HashSet<>();

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Set<StudentDto> getStudents() {
        return students;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setStudents(Set<StudentDto> students) {
        this.students = students;
    }
}
