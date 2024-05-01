import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Student } from '../students/students.component';


@Component({
  selector: 'app-course-students',
  templateUrl: './course-students.component.html',
  styleUrls: ['./course-students.component.css']
})

export class CourseStudentsComponent implements OnInit {
  courseStudents: Student[] = [];
  allStudents: Student[] = [];
  courseId: number =1;

  constructor(
    private route: ActivatedRoute,
    private http: HttpClient,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe(params => {
      this.courseId = +params['id'];
      this.loadCourseStudents();
      this.loadAllStudents();
    });
  }

  loadCourseStudents() {
    this.http.get<Student[]>(`http://localhost:8080/courses/${this.courseId}/students`)
      .subscribe(students => {
        this.courseStudents = students;
      });
  }

  loadAllStudents() {
    this.http.get<Student[]>(`http://localhost:8080/students/`)
      .subscribe(students => {
        this.allStudents = students;
      });
  }

  isEnrolled(student: Student): boolean {
    return this.courseStudents.some(s => s.studentId === student.studentId);
  }

  addStudent(studentId: number) {
    this.http.post(`http://localhost:8080/courses/${this.courseId}/enroll/${studentId}`, {})
      .subscribe(() => {
        this.loadCourseStudents(); 
        this.loadAllStudents(); 
      });
  }

  deleteStudent(studentId: number) {
    this.http.delete(`http://localhost:8080/courses/${this.courseId}/enroll/${studentId}`)
      .subscribe(() => {
        this.loadCourseStudents(); 
        this.loadAllStudents(); 
      });
  }
}
