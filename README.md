# school-api
spring-boot-h2-one-to-many
Java Springboot: School API

In this project, there is a teacher and student management system for a typical school where a teacher has many students and a student has many teachers. Here we are using a Hibernate many-to-many relationship to manage such information.

Here is an example of a teacher data JSON object:

{ "id": 1, "name": "Mark", "students": "[]" }

Here is an example of a student data JSON object:

{ "id": 1, "name": "Salo", "teachers": "[]" }

The application should adhere to the following API format and response codes in SchoolController:

POST /school/teacher/{teacherId}/addStudent

Assume that the given teacherId already exists in the database.
The request body will have a student instance. It might be a new student or an existing one.
Assume that the given student has never been added to the given teacher.
Return the teacher instance after adding the student, along with status code 201.
GET /school/teacher/{teacherId}/students

Assume that the given teacherId already exists in the database.
Return the list of students associated with that teacherId and status code 200.
GET /school/student/{studentId}/teachers

Assume the given studentId exists in the database.
Return the list of teachers associated with the studentId and status code 200.
