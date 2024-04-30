package com.sample.classenrollment.controllers;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.services.CourseService;
import com.sample.classenrollment.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;


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
    public ResponseEntity<Student> deleteStudentById(@PathVariable Long id){
        studentService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{studentId}/courses")
    public List<Course> getClassesForStudent(@PathVariable Long studentId) {
        return studentService.getClassesForStudent(studentId);
    }
}
