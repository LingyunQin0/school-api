package com.example.schoolapi.controller;

import com.example.schoolapi.dto.StudentDto;
import com.example.schoolapi.dto.TeacherDto;
import com.example.schoolapi.service.SchoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.Set;

@RestController
@RequestMapping("/school")
@Validated
public class SchoolController {

    @Autowired
    private SchoolService schoolService;

    /**
     * POST /school/teacher/{teacherId}/addStudent
     */
    @PostMapping("/teacher/{teacherId}/addStudent")
    public ResponseEntity<TeacherDto> addStudentToTeacher(@PathVariable Long teacherId,
                                                          @Valid @RequestBody StudentDto studentDto) {
        TeacherDto teacherDto = schoolService.addStudentToTeacher(teacherId, studentDto);
        return new ResponseEntity<>(teacherDto, HttpStatus.CREATED);
    }

    /**
     * GET /school/teacher/{teacherId}/students
     */
    @GetMapping("/teacher/{teacherId}/students")
    public ResponseEntity<Set<StudentDto>> getStudentsByTeacher(@PathVariable Long teacherId) {
        Set<StudentDto> students = schoolService.getStudentsByTeacher(teacherId);
        return new ResponseEntity<>(students, HttpStatus.OK);
    }

    /**
     * GET /school/student/{studentId}/teachers
     */
    @GetMapping("/student/{studentId}/teachers")
    public ResponseEntity<Set<TeacherDto>> getTeachersByStudent(@PathVariable Long studentId) {
        Set<TeacherDto> teachers = schoolService.getTeachersByStudent(studentId);
        return new ResponseEntity<>(teachers, HttpStatus.OK);
    }
}
