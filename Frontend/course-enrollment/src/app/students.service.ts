import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Student } from './students/students.component';

@Injectable({
  providedIn: 'root'
})
export class StudentsService {

  private studentsUrl = 'http://localhost:8080/students/';
  private createStudentUrl = 'http://localhost:8080/students/create';


  constructor(private http: HttpClient) { }

  getStudents(): Observable<Student[]> {
    return this.http.get<Student[]>(this.studentsUrl);
  }

  createStudent(studentData: Student): Observable<Student> {
    return this.http.post<Student>(this.createStudentUrl, studentData);
  }

  updateStudent(studentId: number, studentData: Student): Observable<any> {
    return this.http.patch(`http://localhost:8080/students/update/${studentId}`, studentData);
  }

  deleteStudent(studentId: number): Observable<any> {
    return this.http.delete(`http://localhost:8080/students/${studentId}`);
  }
}
