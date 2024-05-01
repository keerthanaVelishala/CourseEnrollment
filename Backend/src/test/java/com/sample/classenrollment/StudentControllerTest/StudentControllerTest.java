package com.sample.classenrollment.StudentControllerTest;

import com.sample.classenrollment.controllers.StudentController;
import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.services.StudentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class StudentControllerTest {

    @Mock
    private StudentService studentService;

    @InjectMocks
    private StudentController studentController;

    private Student student;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    @BeforeEach
    public void setUp() throws Exception {
        student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");
        student.setMajor("Computer Science");
        student.setExpectedGraduationDate(dateFormat.parse("2024-6-1"));
    }

    @Test
    public void testGetStudents() {
        when(studentService.getAllStudents()).thenReturn(Arrays.asList(student));
        ResponseEntity<List<Student>> response = studentController.getStudents();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testCreateStudent() {
        when(studentService.addStudent(student)).thenReturn(Arrays.asList(student));
        ResponseEntity<List<Student>> response = studentController.createCourses(student);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testUpdateStudent() {
        when(studentService.getStudentById(1L)).thenReturn(student);
        when(studentService.addStudent(student)).thenReturn(Arrays.asList(student));

        ResponseEntity<List<Student>> response = studentController.updateStudent(student, 1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }


    @Test
    public void testGetStudentById() {
        when(studentService.getStudentById(1L)).thenReturn(student);
        ResponseEntity<Student> response = studentController.getStudentById(1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("John Doe", response.getBody().getName());
    }


    @Test
    public void testGetClassesForStudent_StudentExists() {
        student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");

        Course course = new Course();
        course.setCourseCode(1L);
        course.setName("Algorithms");
        when(studentService.getStudentById(1L)).thenReturn(student);
        when(studentService.getClassesForStudent(1L)).thenReturn(Arrays.asList(course));

        ResponseEntity<List<Course>> response = studentController.getClassesForStudent(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Algorithms", response.getBody().get(0).getName());
    }

    @Test
    public void testGetClassesForStudent_StudentDoesNotExist() {
        student = new Student();
        student.setStudentId(1L);
        student.setName("John Doe");

        Course course = new Course();
        course.setCourseCode(1L);
        course.setName("Algorithms");
        when(studentService.getStudentById(1L)).thenReturn(null);

        ResponseEntity<List<Course>> response = studentController.getClassesForStudent(1L);

        assertNull(response.getBody());
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }


}

