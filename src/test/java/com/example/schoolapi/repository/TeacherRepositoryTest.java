package com.example.schoolapi.repository;

import com.example.schoolapi.model.Teacher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class TeacherRepositoryTest {

    @Autowired
    private TeacherRepository teacherRepository;

    @Test
    void testSaveAndFindTeacher() {
        Teacher teacher = new Teacher("Mark");
        Teacher savedTeacher = teacherRepository.save(teacher);

        assertThat(savedTeacher.getId()).isNotNull();
        assertThat(savedTeacher.getName()).isEqualTo("Mark");

        Teacher foundTeacher = teacherRepository.findById(savedTeacher.getId()).orElse(null);
        assertThat(foundTeacher).isNotNull();
        assertThat(foundTeacher.getName()).isEqualTo("Mark");
    }
}
