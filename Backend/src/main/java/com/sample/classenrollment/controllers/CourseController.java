package com.sample.classenrollment.controllers;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.repositories.CourseRepository;
import com.sample.classenrollment.services.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private CourseService courseService;

    public CourseController(CourseService courseService){
        this.courseService = courseService;
    }

    @GetMapping("/")
    public ResponseEntity<List<Course>> getCourses() {
        return ResponseEntity.ok().body(courseService.getAllCourses());
    }

    @PostMapping("/create")
    public ResponseEntity<List<Course>> createCourses(@RequestBody Course course) {

        return ResponseEntity.ok().body(courseService.addCourse(course));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<List<Course>> updateCourses(@RequestBody Course course,@PathVariable Long id) {
        Course courseOld = courseService.getCourseById(id);
        courseOld.setName(course.getName());
        courseOld.setSubject(course.getSubject());
        courseOld.setSemester(course.getSemester());
        courseOld.setMaxStudents(course.getMaxStudents());
        return ResponseEntity.ok().body(courseService.addCourse(courseOld));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Course> getCourseById(@PathVariable Long id) {
        return ResponseEntity.ok().body(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Course> deleteCourseById(@PathVariable Long id){
        courseService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}/students")
    public List<Student> getStudentsInClass(@PathVariable Long id) {
        return courseService.getStudentsInClass(id);
    }

    @PostMapping("/{id}/enroll/{studentId}")
    public ResponseEntity<CourseStudent> enrollStudent(@PathVariable Long id, @PathVariable Long studentId) {
        return new ResponseEntity<>(courseService.enrollStudent(id, studentId), HttpStatus.CREATED);
    }

    @DeleteMapping("/enrollments/{enrollmentId}")
    public ResponseEntity<Void> unenrollStudent(@PathVariable Long enrollmentId) {
        courseService.unenrollStudent(enrollmentId);
        return ResponseEntity.ok().build();
    }


}
