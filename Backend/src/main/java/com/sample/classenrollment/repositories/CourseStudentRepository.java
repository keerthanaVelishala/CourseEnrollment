package com.sample.classenrollment.repositories;

import com.sample.classenrollment.models.Course;
import com.sample.classenrollment.models.CourseStudent;
import com.sample.classenrollment.models.EnrollmentInfoDTO;
import com.sample.classenrollment.models.Student;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CourseStudentRepository extends JpaRepository<CourseStudent, Long> {


    List<CourseStudent> findByCourseCode(Course courseCode);
    List<CourseStudent> findByStudentId(Student studentId);

    Optional<CourseStudent> findByCourseCodeAndStudentId(Course course, Student student);

    @Query("SELECT new com.sample.classenrollment.models.EnrollmentInfoDTO(cs.courseCode.courseCode, cs.courseCode.name,cs.courseCode.maxStudents,s.studentId, s.name,cs.courseCode.subject, s.major,s.expectedGraduationDate, cs.courseCode.semester,cs.grade) FROM CourseStudent cs JOIN cs.courseCode c JOIN cs.studentId s")
    List<EnrollmentInfoDTO> findAllEnrollmentDetails();

    @Transactional
    @Modifying
    @Query("UPDATE CourseStudent cs SET cs.grade = :grade WHERE cs.id = :id")
    void updateGradeById(Long id, String grade);

}
