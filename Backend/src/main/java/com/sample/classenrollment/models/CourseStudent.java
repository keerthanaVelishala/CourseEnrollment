package com.sample.classenrollment.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courseCode")
    private Course courseCode;

    @ManyToOne
    @JoinColumn(name = "student_Id")
    private Student studentId;

    private String grade;

}
