package com.sample.classenrollment.repositories;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {

}
