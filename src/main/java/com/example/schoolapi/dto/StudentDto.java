package com.example.schoolapi.dto;

public class StudentDto {
    private Long id;
    private String name;

    // Getter & Setter
    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
}
