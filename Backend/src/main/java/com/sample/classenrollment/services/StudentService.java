package com.sample.classenrollment.services;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.Student;
import com.sample.classenrollment.repositories.CourseStudentRepository;
import com.sample.classenrollment.repositories.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {

    private StudentRepository studentRepository;

    private CourseStudentRepository courseStudentRepository;

    public StudentService(StudentRepository studentRepository, CourseStudentRepository courseStudentRepository){
        this.studentRepository = studentRepository;
        this.courseStudentRepository = courseStudentRepository;
    }

    public List<Student> getAllStudents(){
        return studentRepository.findAll();
    }

    public List<Student> addStudent(Student student){
        studentRepository.save(student);
        return getAllStudents();
    }

    public Student getStudentById(Long id){
        return studentRepository.findById(id).get();
    }

    public void deleteById(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Course> getClassesForStudent(Long studentId) {
        Student fetchedStudent = studentRepository.findById(studentId).get();
        return courseStudentRepository.findByStudentId(fetchedStudent).stream()
                .map(CourseStudent::getCourseCode)
                .collect(Collectors.toList());
    }
}
