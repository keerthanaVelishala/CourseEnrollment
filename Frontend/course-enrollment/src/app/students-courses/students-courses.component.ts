import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { Course } from '../classes/classes.component';
import { CoursesService } from '../courses.service';

@Component({
  selector: 'app-students-courses',
  templateUrl: './students-courses.component.html',
  styleUrls: ['./students-courses.component.css']
})
export class StudentsCoursesComponent implements OnInit {
  courses :Course[]= [];
  AllCourses:Course[] = [];
  studentId: number=1;

  constructor(private route: ActivatedRoute, private http: HttpClient,private coursesService: CoursesService) {}

  ngOnInit() {
    this.route.params.subscribe(params => {
      this.studentId = params['id'];
      this.fetchCoursesForStudent(this.studentId);
      this.loadAllCourses();
    });
  }

  fetchCoursesForStudent(studentId: number) {
    this.http.get<Course[]>(`http://localhost:8080/students/${studentId}/courses`)
      .subscribe(data => {
        this.courses = data;
      }, error => console.error('Failed to fetch courses for student', error));
  }

  loadAllCourses() {
    this.coursesService.getCourses().subscribe(
      (data: Course[]) => this.AllCourses = data,
      error => console.error('There was an error!', error)
    );
  }

  isEnrolled(course: Course): boolean {
    return this.courses.some(c => c.courseCode === course.courseCode);
  }


  enrollStudent(courseCode: number) {
    this.http.post(`http://localhost:8080/courses/${courseCode}/enroll/${this.studentId}`, {})
      .subscribe(() => {
        this.fetchCoursesForStudent(this.studentId);
        this.loadAllCourses();
      });
  }

  unenrollStudent(courseCode: number) {
    this.http.delete(`http://localhost:8080/courses/${courseCode}/enroll/${this.studentId}`)
      .subscribe(() => {
        this.fetchCoursesForStudent(this.studentId);
        this.loadAllCourses();
      });
  }


}
