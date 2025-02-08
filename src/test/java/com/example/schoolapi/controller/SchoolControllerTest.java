package com.example.schoolapi.controller;

import com.example.schoolapi.dto.StudentDto;
import com.example.schoolapi.model.Teacher;
import com.example.schoolapi.repository.TeacherRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SchoolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Teacher teacher;

    @BeforeEach
    void setUp() {
        teacherRepository.deleteAll();
        teacher = new Teacher("Mark");
        teacher = teacherRepository.save(teacher);
    }

    @Test
    void testAddStudentToTeacher_Success() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Salo");

        mockMvc.perform(post("/school/teacher/{teacherId}/addStudent", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(teacher.getId()))
                .andExpect(jsonPath("$.students", hasSize(1)));
    }

    @Test
    void testGetStudentsByTeacher_Success() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Salo");

        // 先添加学生
        mockMvc.perform(post("/school/teacher/{teacherId}/addStudent", teacher.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isCreated());

        // 再查询学生
        mockMvc.perform(get("/school/teacher/{teacherId}/students", teacher.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testAddStudentToNonExistentTeacher_ShouldReturn404() throws Exception {
        StudentDto studentDto = new StudentDto();
        studentDto.setName("Non-Existent Teacher Test");

        mockMvc.perform(post("/school/teacher/{teacherId}/addStudent", 999L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentDto)))
                .andExpect(status().isNotFound());
    }
}
