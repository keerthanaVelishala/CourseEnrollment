package com.sample.classenrollment.repositories;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {


    List<CourseStudent> findByCourseCode(Course courseCode);
    List<CourseStudent> findByStudentId(Student studentId);

    List<CourseStudent> findByCourseCodeAndStudentId(Course course, Student student);

}
