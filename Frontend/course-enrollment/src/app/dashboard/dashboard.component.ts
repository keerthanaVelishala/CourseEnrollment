import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';

export interface CourseStudent {
  courseId: number;
  courseName: string;
  maxStudents: number;
  studentId: number;
  studentName: string;
  subject: string;
  major: string;
  expectedGraduationDate: string;
  semester: string;
  grade: string | null;
}

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  public courses: CourseStudent[] = [];

  constructor(private http: HttpClient) { }

  ngOnInit(): void {
    this.fetch();
  }

  fetch(){
    let headers = new HttpHeaders().set('Cache-Control', 'no-cache').set('Pragma', 'no-cache');
    this.http.get<CourseStudent[]>('http://localhost:8080/courses/viewAllRecords', { headers }).subscribe({
      next: data => {
        this.courses = data;
      },
      error: error => {
        console.error('Failed to fetch courses:', error);
        alert('Failed to refresh data. Please try reloading the page manually.');
      }
    });
  }

  updateGrade(course: CourseStudent, newGrade: string | null): void {
    if (newGrade == null || !['A', 'B', 'C', 'D', 'E', 'F', ''].includes(newGrade)) {
      alert("Invalid grade. Allowed values are A, B, C, D, E, F.");
      return;
    }

    let params = new HttpParams()
      .set('courseId', course.courseId.toString())
      .set('studentId', course.studentId.toString())
      .set('newGrade', newGrade);
      
      this.http.put(`http://localhost:8080/courses/update-grade`, {},{ params,responseType: 'text' })
      .subscribe({
        next: () => {
          alert('Grade updated successfully!');
          this.fetch();
        },
        error: error => {
          alert('Error updating grade: ' + error.message);
          this.fetch();
        }
      });
  }

}
