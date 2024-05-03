package com.sample.classenrollment.controllers;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.repositories.CourseStudentRepository;
import com.sample.classenrollment.services.CourseService;
import com.sample.classenrollment.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:4200")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseStudentRepository courseStudentRepository;

    @GetMapping("/")
    public ResponseEntity<List<Student>> getStudents() {
        return ResponseEntity.ok().body(studentService.getAllStudents());
    }

    @PostMapping("/create")
    public ResponseEntity<List<Student>> createCourses(@RequestBody Student student) {

        return ResponseEntity.ok().body(studentService.addStudent(student));
    }

    @PatchMapping("/update/{id}")
    public ResponseEntity<List<Student>> updateStudent(@RequestBody Student student,@PathVariable Long id) {
        Student studentOld = studentService.getStudentById(id);
        studentOld.setName(student.getName());
        studentOld.setMajor(student.getMajor());
        studentOld.setExpectedGraduationDate(student.getExpectedGraduationDate());
        return ResponseEntity.ok().body(studentService.addStudent(studentOld));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudentById(@PathVariable Long id) {
        return ResponseEntity.ok().body(studentService.getStudentById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteStudentById(@PathVariable Long id){
        Student student = studentService.getStudentById(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }

        List<CourseStudent> courseStudents = courseStudentRepository.findByStudentId(student);
        if (!courseStudents.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot delete student, student has enrollments"));
        }

        studentService.deleteById(id);
        return ResponseEntity.ok().body(Map.of("message", "Student deleted successfully"));
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<Course>> getClassesForStudent(@PathVariable Long studentId) {
        Student student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.notFound().build(); // Returns a 404 if the student is not found
        }
        return  ResponseEntity.ok().body(studentService.getClassesForStudent(studentId));
    }


}
