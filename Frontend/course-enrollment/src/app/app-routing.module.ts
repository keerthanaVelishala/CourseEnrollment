import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { DashboardComponent } from './dashboard/dashboard.component';
import { ClassesComponent } from './classes/classes.component';
import { StudentsComponent } from './students/students.component';
import { CourseStudentsComponent } from './course-students/course-students.component';
import { StudentsCoursesComponent } from './students-courses/students-courses.component';

const routes: Routes = [
  { path: '', redirectTo: '/dashboard', pathMatch: 'full' },
  { path: 'dashboard', component: DashboardComponent },
  { path: 'classes', component: ClassesComponent },
  { path: 'students', component: StudentsComponent },
  { path: 'courses/:id/students', component: CourseStudentsComponent },
  { path: 'students/:id/courses', component: StudentsCoursesComponent }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
