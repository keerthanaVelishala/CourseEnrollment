package com.sample.classenrollment.CourseControllerTest;

import com.sample.classenrollment.controllers.CourseController;
import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.repositories.CourseStudentRepository;
import com.sample.classenrollment.services.CourseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class CourseControllerTest {

    @Mock
    private CourseService courseService;

    @Mock
    private CourseStudentRepository courseStudentRepository;

    @InjectMocks
    private CourseController courseController;

    private Course course;
    private Student student;
    private CourseStudent courseStudent;

    @BeforeEach
    public void setUp() {
        course = new Course();
        course.setCourseCode(1L);
        course.setName("Course Name");
        course.setSubject("Subject");
        course.setMaxStudents(30);
        course.setSemester("Fall 2022");

        student = new Student();
        student.setStudentId(1L);
        student.setName("Student Name");

        courseStudent = new CourseStudent();
        courseStudent.setId(1L);
        courseStudent.setCourseCode(course);
        courseStudent.setStudentId(student);
    }

    @Test
    public void testGetCourses() {
        when(courseService.getAllCourses()).thenReturn(Arrays.asList(course));
        ResponseEntity<List<Course>> response = courseController.getCourses();
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testCreateCourses() {
        when(courseService.addCourse(course)).thenReturn(Arrays.asList(course));
        ResponseEntity<List<Course>> response = courseController.createCourses(course);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testUpdateCourses() {
        when(courseService.getCourseById(1L)).thenReturn(course);
        when(courseService.addCourse(course)).thenReturn(Arrays.asList(course));

        ResponseEntity<List<Course>> response = courseController.updateCourses(course, 1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    public void testGetCourseById() {
        when(courseService.getCourseById(1L)).thenReturn(course);
        ResponseEntity<Course> response = courseController.getCourseById(1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Course Name", response.getBody().getName());
    }


//    @Test
//    public void testDeleteCourseById() {
//        doNothing().when(courseService).deleteById(1L);
//        ResponseEntity<Course> response = courseController.deleteCourseById(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//        assertNull(response.getBody());
//    }


    @Test
    public void testGetStudentsInClass_WithStudents() {
        when(courseService.getCourseById(1L)).thenReturn(course);
        when(courseService.getStudentsInClass(1L)).thenReturn(Arrays.asList(student));

        ResponseEntity<List<Student>> response = courseController.getStudentsInClass(1L);

        assertNotNull(response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertEquals("Student Name", response.getBody().get(0).getName());
    }


    @Test
    public void testGetStudentsInClass_CourseNotFound() {
        when(courseService.getCourseById(1L)).thenReturn(null);

        ResponseEntity<List<Student>> response = courseController.getStudentsInClass(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }


    @Test
    public void testEnrollStudent() {
        when(courseService.enrollStudent(1L, 1L)).thenReturn(courseStudent);
        ResponseEntity<CourseStudent> response = courseController.enrollStudent(1L, 1L);
        assertNotNull(response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(Long.valueOf(1), response.getBody().getId());
    }


//    @Test
//    public void testUnenrollStudent() {
//        doNothing().when(courseService).unenrollStudent(1L);
//        ResponseEntity<Void> response = courseController.unenrollStudent(1L);
//        assertEquals(HttpStatus.OK, response.getStatusCode());
//    }


}

