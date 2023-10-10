

# EduOnGo - Online Learning Management System (OLMS)

The EduOnGo (OLMS) is a comprehensive software solution designed to support remote education and training. The system aims to provide educators with tools for creating and delivering online courses, while also offering students a user-friendly interface for accessing course materials, participating in assignments, and tracking their progress. With features such as progress tracking, performance analytics, and communication tools, the online learning management system enhances the teaching and learning experience.

![ER-Diagram](https://github.com/shivendra-somr/EduOnGo/assets/123854927/50a9debe-c4b8-4f3f-8fd9-74bf733a1763)



## Table of Contents
- [Domain Description](#domain-description)
- [Types of Users](#types-of-users)
- [Role of Administrator](#role-of-administrator)
- [Role of Instructor/Educator](#role-of-instructoreducator)
- [Role of Student/Learner](#role-of-studentlearner)
- [Database Design](#database-design)

## Domain Description

The EduOnGo (OLMS) facilitates remote education and training, providing administrators, instructors, and students with a seamless platform for managing and accessing educational content. The system aims to foster a conducive environment for effective learning and teaching.

## Types of Users

The EduOnGo accommodates the following types of users:

1. **Administrator**: Responsible for managing the overall system and overseeing course progress and statistics.

2. **Instructor/Educator**: Facilitates the creation and management of course content, assessments, and assignments.

3. **Student/Learner**: Accesses course materials, submits assignments, and tracks their progress.

## Role of Administrator

The Administrator plays a vital role in overseeing the entire system's functionality. Their responsibilities include:

- Logging in to the administrator account using predefined credentials.
- Monitoring course progress, tracking student performance, and generating reports on course statistics.
- Logging out from the administrator account.

## Role of Instructor/Educator

Instructors are responsible for managing course content and assessments. Their role involves:

- Registering for an instructor account by providing necessary information.
- Logging in to the instructor account using the registered username and password.
- Performing CRUD operations for course content, including creating, reading, updating, and deleting lessons, assignments, quizzes, and assessments.
- Logging out from the instructor account.

## Role of Student/Learner

Students utilize the OLMS to access course materials, submit assignments, and monitor their learning progress. Their role includes:

- Registering for a student account by providing necessary personal information.
- Logging in to the student account using the registered username and password.
- Accessing course materials, including lectures, readings, videos, and assignments.
- Submitting assignments and tracking their progress.
- Logging out from the student account.

## Database Design

The EduOnGo utilizes a relational database to capture and store information related to users, courses, enrollments, assignments, grades, assessments, and discussions. The database design includes the following tables:

1. **Student**: Stores information about students, including student ID, username, password, email, contact, and date of birth.

2. **Instructor**: Contains details of instructors, such as instructor ID, username, password, email, contact, and date of birth.

3. **Course**: Holds data regarding courses, including course ID, title, description, instructor ID (foreign key reference to the instructor table), and course duration.

4. **Lessons**: Stores lesson information, including lesson ID, title, content, and course ID (foreign key reference to the course table).

5. **Assignment**: Contains assignment details, including assignment ID, title, description, course ID (foreign key reference to the course table), and due date.

6. **Quiz**: Stores quiz information, including quiz ID, title, description, course ID (foreign key reference to the course table), and time limit.

7. **Grade**: Holds grade information, including grade ID, student ID (foreign key reference to the student table), course ID (foreign key reference to the course table), and marks.

8. **Assessment**: Contains assessment details, including assessment ID, title, description, and course ID (foreign key reference to the course table).

9. **Result**: Stores assessment results, including result ID, student ID (foreign key reference to the student table), and assessment ID (foreign key reference to the assessment table), and score.

Please see the [ER-Diagram](./ER-Diagram.png) for a visual representation of the database structure and relationships.

To maintain flexibility and security, database credentials are stored in a properties file separate from the application code.

## Contributing

Contributions to the EduOnGo project are welcome! If you have any suggestions, improvements, or find issues, please feel free to open an issue or submit a pull request.

## License

This project is licensed under the [MIT License](./LICENSE). Feel free to use, modify, and distribute the code as per the terms of the license.
