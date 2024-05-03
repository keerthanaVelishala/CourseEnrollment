package com.sample.classenrollment.models;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentInfoDTO {
    private Long courseId;
    private String courseName;
    private int maxStudents;
    private Long studentId;
    private String studentName;
    private String subject;
    private String major;
    private Date expectedGraduationDate;
    private String semester;
    private String grade;
}
