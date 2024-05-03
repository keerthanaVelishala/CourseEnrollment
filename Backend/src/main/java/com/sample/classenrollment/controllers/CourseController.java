package com.sample.classenrollment.controllers;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.EnrollmentInfoDTO;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.repositories.CourseRepository;
import com.sample.classenrollment.repositories.CourseStudentRepository;
import com.sample.classenrollment.services.CourseService;
import com.sample.classenrollment.services.StudentService;
import jakarta.transaction.Transactional;
import org.springframework.data.jdbc.core.JdbcAggregateOperations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/courses")
@Transactional
@CrossOrigin(origins = "http://localhost:4200")
public class CourseController {

    private CourseService courseService;

    private CourseStudentRepository courseStudentRepository;

    private StudentService studentService;

    public CourseController(CourseService courseService, CourseStudentRepository courseStudentRepository, StudentService studentService){
        this.courseService = courseService;
        this.courseStudentRepository = courseStudentRepository;
        this.studentService = studentService;
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
        Course course = courseService.getCourseById(id);
        if(course==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(courseService.getCourseById(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteCourseById(@PathVariable Long id){
        Course course = courseService.getCourseById(id);
        if(course==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<CourseStudent> courseStudents = courseStudentRepository.findByCourseCode(course);
        if (!courseStudents.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Cannot delete course, course has student enrollments"));
        }
        courseService.deleteById(id);
        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/{id}/students")
    public ResponseEntity<List<Student>> getStudentsInClass(@PathVariable Long id) {
        Course course = courseService.getCourseById(id);
        if(course==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return ResponseEntity.ok().body(courseService.getStudentsInClass(id));
    }

    @PostMapping("/{id}/enroll/{studentId}")
    public ResponseEntity<CourseStudent> enrollStudent(@PathVariable Long id, @PathVariable Long studentId) {
        return new ResponseEntity<>(courseService.enrollStudent(id, studentId), HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}/enroll/{studentId}")
    public ResponseEntity<Void> unenrollStudent(@PathVariable Long id, @PathVariable Long studentId) {
        Course course = courseService.getCourseById(id);
        if(course==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = studentService.getStudentById(studentId);
        if(student==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        courseService.unenrollStudent(course,student);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/viewAllRecords")
    public ResponseEntity<List<EnrollmentInfoDTO>> viewAll(){
        List<EnrollmentInfoDTO> allRecords = courseService.viewAll();
        return new ResponseEntity<>(allRecords,HttpStatus.OK);
    }


    @PutMapping("/update-grade")
    public ResponseEntity<String> updateStudentGrade(
            @RequestParam Long courseId,
            @RequestParam Long studentId,
            @RequestParam String newGrade) {

        Course course = courseService.getCourseById(courseId);
        if(course==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Student student = studentService.getStudentById(studentId);
        if(student==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Optional<CourseStudent> courseStudentOptional = courseStudentRepository.findByCourseCodeAndStudentId(course, student);

        if (courseStudentOptional.isPresent()) {
            CourseStudent courseStudent = courseStudentOptional.get();
            courseStudentRepository.updateGradeById(courseStudent.getId(),newGrade);
            return ResponseEntity.ok("Grade updated successfully");
        } else {
            return ResponseEntity.badRequest().body("CourseStudent not found with given courseId and studentId");
        }
    }


}
