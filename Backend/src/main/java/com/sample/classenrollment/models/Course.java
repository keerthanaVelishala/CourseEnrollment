package com.sample.classenrollment.models;

import jakarta.annotation.Generated;
import jakarta.persistence.*;
import lombok.Data;

import java.util.*;

@Data
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_code")
    private Long courseCode;
    private String name;
    private String subject;
    private int maxStudents;
    private String semester;

}
