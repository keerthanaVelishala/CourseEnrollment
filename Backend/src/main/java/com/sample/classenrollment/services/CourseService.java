package com.sample.classenrollment.services;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.EnrollmentInfoDTO;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.repositories.CourseRepository;
import com.sample.classenrollment.repositories.CourseStudentRepository;
import com.sample.classenrollment.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseService {

    private CourseRepository courseRepository;

    private CourseStudentRepository courseStudentRepository;

    private StudentRepository studentRepository;

    public CourseService(CourseRepository courseRepository, StudentRepository studentRepository, CourseStudentRepository courseStudentRepository ){
        this.courseRepository = courseRepository;
        this.studentRepository = studentRepository;
        this.courseStudentRepository = courseStudentRepository;
    }

    public List<Course> getAllCourses(){
        return courseRepository.findAll();
    }

    public List<Course> addCourse(Course course){
        courseRepository.save(course);
        return getAllCourses();
    }

    public Course getCourseById(Long id){
        return courseRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public List<Student> getStudentsInClass(Long classId) {
        Course fetchedCourse = courseRepository.findById(classId).get();
        return courseStudentRepository.findByCourseCode(fetchedCourse).stream()
                .map(CourseStudent::getStudentId)
                .collect(Collectors.toList());
    }

    public CourseStudent enrollStudent(Long classId, Long studentId) {
        Course fetchedCourse = courseRepository.findById(classId).get();
        Student fetchedStudent = studentRepository.findById(studentId).get();
        CourseStudent enrollment = new CourseStudent();
        enrollment.setCourseCode(fetchedCourse);
        enrollment.setStudentId(fetchedStudent);
        return courseStudentRepository.save(enrollment);
    }

    public void unenrollStudent(Course course,Student student) {
        Optional<CourseStudent> enrollment = courseStudentRepository.findByCourseCodeAndStudentId(course, student);
        enrollment.ifPresent(courseStudentRepository::delete);
    }

    public List<EnrollmentInfoDTO> viewAll(){
        List<EnrollmentInfoDTO> allRecords = courseStudentRepository.findAllEnrollmentDetails();
        return allRecords;
    }
}
