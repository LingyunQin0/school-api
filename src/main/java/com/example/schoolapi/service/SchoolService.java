package com.example.schoolapi.service;

import com.example.schoolapi.dto.StudentDto;
import com.example.schoolapi.dto.TeacherDto;
import com.example.schoolapi.exception.ResourceNotFoundException;
import com.example.schoolapi.model.Student;
import com.example.schoolapi.model.Teacher;
import com.example.schoolapi.repository.StudentRepository;
import com.example.schoolapi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SchoolService {

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private StudentRepository studentRepository;

    // 辅助方法：将 Student 实体转换为 DTO
    private StudentDto toStudentDto(Student student) {
        StudentDto dto = new StudentDto();
        dto.setId(student.getId());
        dto.setName(student.getName());
        return dto;
    }

    // 辅助方法：将 Teacher 实体转换为 DTO
    private TeacherDto toTeacherDto(Teacher teacher) {
        TeacherDto dto = new TeacherDto();
        dto.setId(teacher.getId());
        dto.setName(teacher.getName());
        dto.setStudents(teacher.getStudents().stream()
                .map(this::toStudentDto)
                .collect(Collectors.toSet()));
        return dto;
    }

    /**
     * 添加一个学生到指定教师中。
     * 如果 studentDto 中包含 id，则认为是更新已存在的学生，否则保存新学生。
     */
    @Transactional
    public TeacherDto addStudentToTeacher(Long teacherId, StudentDto studentDto) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));

        Student student;
        if (studentDto.getId() != null) {
            student = studentRepository.findById(studentDto.getId())
                    .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentDto.getId()));
        } else {
            student = new Student(studentDto.getName());
            student = studentRepository.save(student);
        }
        // 如果已存在关联，则可以选择抛出异常或直接返回当前状态
        if (teacher.getStudents().contains(student)) {
            throw new IllegalArgumentException("Student is already added to the teacher");
        }
        // 通过实体中的辅助方法建立双向关联
        teacher.addStudent(student);

        teacherRepository.save(teacher);
        // 由于级联操作或事务，通常不需要额外保存 student

        return toTeacherDto(teacher);
    }

    /**
     * 根据教师 id 返回关联的所有学生
     */
    @Transactional(readOnly = true)
    public Set<StudentDto> getStudentsByTeacher(Long teacherId) {
        Teacher teacher = teacherRepository.findById(teacherId)
                .orElseThrow(() -> new ResourceNotFoundException("Teacher not found with id " + teacherId));
        return teacher.getStudents().stream()
                .map(this::toStudentDto)
                .collect(Collectors.toSet());
    }

    /**
     * 根据学生 id 返回关联的所有教师
     */
    @Transactional(readOnly = true)
    public Set<TeacherDto> getTeachersByStudent(Long studentId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found with id " + studentId));
        return student.getTeachers().stream()
                .map(this::toTeacherDto)
                .collect(Collectors.toSet());
    }
}
