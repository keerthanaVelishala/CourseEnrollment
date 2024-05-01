import { Component, OnInit } from '@angular/core';
import { CoursesService } from '../courses.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

export interface Course {
  courseCode: number;
  name: string;
  subject: string;
  maxStudents: number;
  semester: string;
}

@Component({
  selector: 'app-classes',
  templateUrl: './classes.component.html',
  styleUrls: ['./classes.component.css']
})
export class ClassesComponent implements OnInit {
  courses : Course[] = [];
  courseForm: FormGroup;
  editCourseId: number | null = null;
  editForm: FormGroup;

  //constructor(private coursesService: CoursesService) {}
  constructor(private coursesService: CoursesService, private fb: FormBuilder) {
    this.courseForm = this.fb.group({
      name: ['', Validators.required],
      subject: ['', Validators.required],
      maxStudents: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      semester: ['', Validators.required]
    });
    this.editForm = this.fb.group({
      name: ['', Validators.required],
      subject: ['', Validators.required],
      maxStudents: ['', [Validators.required, Validators.pattern('^[0-9]*$')]],
      semester: ['', Validators.required]
    });
  }

  ngOnInit(): void {
    this.fetchCourses();
  }

  fetchCourses() {
    this.coursesService.getCourses().subscribe(
      (data: Course[]) => this.courses = data,
      error => console.error('There was an error!', error)
    );
  }

  onSubmit(): void {
    if (this.courseForm.valid) {
      this.coursesService.createCourse(this.courseForm.value).subscribe(
        response => {
          console.log('Course added!', response);
          this.courses.push(response);
          this.courseForm.reset();
          this.fetchCourses();
        },
        error => console.error('Failed to create course!', error)
      );
    }
  }

  onEdit(course: Course): void {
    this.editCourseId = course.courseCode;
    console.log(course.name);
    this.editForm.setValue({
      name: course.name,
      subject: course.subject,
      maxStudents: course.maxStudents,
      semester: course.semester
    });
  }   

  deleteCourse(courseCode: number): void {
    this.coursesService.deleteCourse(courseCode).subscribe(
      response => {
        console.log('Course deleted successfully');
        this.fetchCourses(); // Reload the courses list
      },
      error => console.error('Error deleting course:', error)
    );
  }

  onSave(courseCode: number): void {
    if (this.editForm.valid) {
      this.coursesService.updateCourse(courseCode, this.editForm.value).subscribe(
        () => {
          this.fetchCourses();  // Refresh the list
          this.editCourseId = null;  // Exit edit mode
        },
        error => console.error('Failed to update course!', error)
      );
    }
  }

  onCancelEdit(): void {
    this.editForm.reset();
    this.editCourseId = null;  // Exit edit mode
  }
  

  

}
