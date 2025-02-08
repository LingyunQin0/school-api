package com.example.schoolapi.service;

import com.example.schoolapi.dto.StudentDto;
import com.example.schoolapi.exception.ResourceNotFoundException;
import com.example.schoolapi.model.Student;
import com.example.schoolapi.model.Teacher;
import com.example.schoolapi.repository.StudentRepository;
import com.example.schoolapi.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SchoolServiceTest {

    @InjectMocks
    private SchoolService schoolService;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    private Teacher teacher;
    private Student student;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        teacher = new Teacher("Mark");
        teacher.setId(1L);
        student = new Student("Salo");
        student.setId(1L);
    }

    @Test
    void testAddStudentToTeacher_Success() {
        StudentDto studentDto = new StudentDto();
        studentDto.setId(1L);
        studentDto.setName("Salo");

        when(teacherRepository.findById(1L)).thenReturn(Optional.of(teacher));
        when(studentRepository.findById(1L)).thenReturn(Optional.of(student));

        schoolService.addStudentToTeacher(1L, studentDto);

        assertTrue(teacher.getStudents().contains(student));
        verify(teacherRepository, times(1)).save(teacher);
    }

    @Test
    void testAddStudentToTeacher_TeacherNotFound() {
        when(teacherRepository.findById(1L)).thenReturn(Optional.empty());

        StudentDto studentDto = new StudentDto();
        studentDto.setName("New Student");

        assertThrows(ResourceNotFoundException.class, () ->
                schoolService.addStudentToTeacher(1L, studentDto)
        );
    }

    @Test
    void testGetStudentsByTeacher_TeacherNotFound() {
        when(teacherRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () ->
                schoolService.getStudentsByTeacher(99L)
        );
    }
}
