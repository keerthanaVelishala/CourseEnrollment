import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Course } from './classes/classes.component'; 

@Injectable({
  providedIn: 'root'
})
export class CoursesService {
  private coursesUrl = 'http://localhost:8080/courses/';
  private createCourseUrl = 'http://localhost:8080/courses/create';


  constructor(private http: HttpClient) { }

  getCourses(): Observable<Course[]> {
    return this.http.get<Course[]>(this.coursesUrl);
  }

  createCourse(courseData: Course): Observable<Course> {
    return this.http.post<Course>(this.createCourseUrl, courseData);
  }

  updateCourse(courseCode: number, courseData: Course): Observable<any> {
    return this.http.patch(`http://localhost:8080/courses/update/${courseCode}`, courseData);
  }

  deleteCourse(courseCode: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/courses/${courseCode}`);
  }
  
}
