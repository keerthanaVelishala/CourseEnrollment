package com.sample.classenrollment.models;

import jakarta.persistence.*;
import lombok.Data;
import java.util.*;

@Entity
@Data
@Table(name = "Student")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private Long studentId;

    private String name;

    private String major;

    private Date expectedGraduationDate;


}