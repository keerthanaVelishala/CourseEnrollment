# Class Enrollment Management Spring Boot Project

This project implements a REST API using Spring Boot for managing courses and student enrollments. The API provides endpoints for creating, retrieving, updating, and deleting courses and students, as well as managing enrollments.

## Features

### Course API Endpoints

- **Get All Courses**:
  - `GET /courses/` - Retrieves a list of all courses.

- **Create Course**:
  - `POST /courses/create` - Allows the creation of a new course.

- **Update Course by ID**:
  - `PATCH /courses/update/{id}` - Updates the details of an existing course specified by its ID.

- **Delete Course by ID**:
  - `DELETE /courses/{id}` - Deletes a course from the database using its ID.

- **Get Course by ID**:
  - `GET /courses/{id}` - Fetches the details of a single course by its unique identifier.

- **Get Students in a Course**:
  - `GET /courses/{id}/students` - Retrieves a list of students enrolled in a specific course.

- **Enroll Student in Course**:
  - `POST /courses/{id}/enroll/{studentId}` - Enrolls a student in a course.

- **Unenroll Student from Course**:
  - `DELETE /courses/{id}/enroll/{studentId}` - Removes a student's enrollment from a course.

### Student API Endpoints

- **Get All Students**:
  - `GET /students/` - Retrieves a list of all students.

- **Create Student**:
  - `POST /students/create` - Allows the creation of a new student.

- **Update Student by ID**:
  - `PATCH /students/update/{id}` - Updates the details of an existing student specified by their ID.

- **Delete Student by ID**:
  - `DELETE /students/{id}` - Deletes a student from the database using their ID.

- **Get Student by ID**:
  - `GET /students/{id}` - Fetches the details of a single student by their unique identifier.

- **Get Courses for Student**:
  - `GET /students/{studentId}/courses` - Retrieves a list of courses that a student is enrolled in.

## Testing

- **Unit Testing**:
  - Unit tests are implemented using JUnit and Mockito. Each endpoint has corresponding test cases to ensure the functionality works as intended.

## Database

- **MySQL**:
  - The project uses SQLite as the database for both development and testing. It is a lightweight, file-based database, which simplifies the configuration and deployment processes.
    
## Manual Testing
- **PostMan**:
  - created a postman collection and tested all the end points.
    
## How to Run

- To run the application, use the following command:
  ```shell
  ./mvnw spring-boot:run
