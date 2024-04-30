package com.sample.classenrollment.models;

import lombok.Data;

import java.io.Serializable;
import java.util.*;

@Data
public class CourseStudentId implements Serializable {

    private Long courseCode;
    private Long studentId;


    public CourseStudentId() {
    }

    public CourseStudentId(Long courseCode, Long studentId) {
        this.courseCode = courseCode;
        this.studentId = studentId;
    }

    @Override
    public boolean equals(Object o){
        if(this==o) return true;
        if (!(o instanceof CourseStudentId that)) return false;
        return Objects.equals(getCourseCode(), that.getCourseCode()) &&
                Objects.equals(getStudentId(), that.getStudentId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseCode(), getStudentId());
    }
}
