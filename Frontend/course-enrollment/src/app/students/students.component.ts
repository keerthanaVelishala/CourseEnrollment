import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { StudentsService } from '../students.service';

export interface Student {
  studentId: number;
  name: string;
  major: string;
  expectedGraduationDate: string;
}


@Component({
  selector: 'app-students',
  templateUrl: './students.component.html',
  styleUrls: ['./students.component.css']
})
export class StudentsComponent implements OnInit {
  studentForm: FormGroup;
  editForm: FormGroup;
  students: Student[] = [];
  editStudentId: number | null = null;


  constructor(private fb: FormBuilder, private http: HttpClient,private studentsService: StudentsService) {
    this.studentForm = this.fb.group({
      name: ['', Validators.required],
      major: ['', Validators.required],
      expectedGraduationDate: ['', Validators.required]
    });
    this.editForm = this.fb.group({
      name: ['', Validators.required],
      major: ['', Validators.required],
      expectedGraduationDate: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchStudents();
  }

  fetchStudents() {
    this.studentsService.getStudents().subscribe(
      (data: Student[]) => this.students = data,
      error => console.error('There was an error!', error)
    );
  }


  onSubmit(): void {
    if (this.studentForm.valid) {

      this.studentsService.createStudent(this.studentForm.value).subscribe(
        response => {
          console.log('Course added!', response);
          this.students.push(response);
          this.studentForm.reset();
          this.fetchStudents();
        },
        error => console.error('Failed to create course!', error)
      );
    }
  }

  onEdit(student: Student): void {
    this.editStudentId = student.studentId;
    this.editForm.setValue({
      name: student.name,
      major: student.major,
      expectedGraduationDate: student.expectedGraduationDate
    });
  }   

  deleteStudent(studentId: number): void {
    this.studentsService.deleteStudent(studentId).subscribe(
      response => {
        console.log('Student deleted successfully');
        this.fetchStudents(); 
      },
      error => console.error('Error deleting student:', error)
    );
  }

  onSave(studentId: number): void {
    if (this.editForm.valid) {
      this.studentsService.updateStudent(studentId, this.editForm.value).subscribe(
        () => {
          this.fetchStudents();  
          this.editStudentId = null; 
        },
        error => console.error('Failed to update course!', error)
      );
    }
  }

  onCancelEdit(): void {
    this.editForm.reset();
    this.editStudentId = null;  
  }
}
