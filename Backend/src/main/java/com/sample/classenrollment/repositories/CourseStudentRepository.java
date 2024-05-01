package com.sample.classenrollment.repositories;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {


    List<CourseStudent> findByCourseCode(Course courseCode);
    List<CourseStudent> findByStudentId(Student studentId);

    Optional<CourseStudent> findByCourseCodeAndStudentId(Course course, Student student);

}
