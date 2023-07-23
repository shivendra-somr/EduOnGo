package com.masai.ui;

import java.util.List;
import java.util.Scanner;

import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Grade;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.service.StudentService;
import com.masai.service.StudentServiceImpl;

public class StudentUI {

	private static void displayStudentMenu(Scanner sc) {
		System.out.println("\tStudent Menu");
		System.out.println("***********************************");
		System.out.println("1. Access Course Materials");
		System.out.println("2. Submit Assignments");
		System.out.println("3. Track Progress");
		System.out.println("4. Update Details");
		System.out.println("5. Change Password");
		System.out.println("6. Delete Account");
		System.out.println("0. Exit");
		System.out.println("***********************************");
	}

	public static void studentLogin(Scanner sc) {
		System.out.println("Student Login");

		System.out.print("Enter your username: ");
		String username = sc.next();
		System.out.print("Enter your password: ");
		String password = sc.next();

		StudentService studentService = new StudentServiceImpl();
		try {
			studentService.login(username, password);
			System.out.println("Login successful!");

			studentMenu(sc);
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void studentRegistration(Scanner sc) {
		System.out.println("\tStudent Registration");
		System.out.println("***********************************");
		System.out.print("Enter your first name: ");
		String firstName = sc.next();
		System.out.print("Enter your last name: ");
		String lastName = sc.next();
		System.out.print("Enter your username: ");
		String username = sc.next();
		System.out.print("Enter your password: ");
		String password = sc.next();
		System.out.print("Enter your email: ");
		String email = sc.next();
		System.out.print("Enter your contact number: ");
		String contact = sc.next();
		System.out.print("Enter your date of birth (in the format yyyy-MM-dd): ");
		String dobStr = sc.next();

		// Create a Student object with the user-provided data
		Student student = new Student(firstName, lastName, username, password, email, contact, dobStr);

		// Create an instance of the StudentService
		StudentService studentService = new StudentServiceImpl();

		try {
			// Attempt to register the student
			studentService.register(student);
			System.out.println("Registration successful! You can now log in with your credentials.");
			System.out.println("***********************************");
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void studentMenu(Scanner sc) {
		int choice = 0;
		do {
			displayStudentMenu(sc);
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				accessCourseMaterials(sc);
				break;
			case 2:
				submitAssignments(sc);
				break;
			case 3:
				trackProgress(sc);
				break;
			case 4:
				updateDetails(sc);
				break;
			case 5:
				changePassword(sc);
				break;
			case 6:
				deleteAccount(sc);
				System.out.println("Account deleted successfully!");
				System.out.println("***********************************");
				LoggedInUserId.loggedInUserId = -1;
				return;
			case 0:
				System.out.println("Logged out successfully!");
				System.out.println("***********************************");
				LoggedInUserId.loggedInUserId = -1;
				return;
			default:
				System.out.println("Invalid Selection, try again");
				break;
			}
		} while (choice != 0);
	}

	static void deleteAccount(Scanner sc) {
		System.out.println("Delete Account");
		System.out.print("Are you sure you want to delete your account? (yes/no): ");
		String confirmation = sc.next();

		if (confirmation.equalsIgnoreCase("yes")) {
			// Call the corresponding StudentService method to delete the account from the
			// database
			StudentService studentService = new StudentServiceImpl();

			try {
				studentService.delete(LoggedInUserId.loggedInUserId);
				LoggedInUserId.loggedInUserId = -1;
			} catch (NoRecordFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Account deletion cancelled.");
		}
	}

	static void changePassword(Scanner sc) {
		System.out.println("\tChange Password");
		System.out.println("***********************************");
		System.out.print("Enter current password: ");
		String currentPassword = sc.next();
		System.out.print("Enter new password: ");
		String newPassword = sc.next();
		System.out.print("Confirm new password: ");
		String confirmPassword = sc.next();

		// Verify the current password against the one stored in the database
		// Call the corresponding StudentService method to update the password
		if (newPassword.equals(confirmPassword)) {
			StudentService studentService = new StudentServiceImpl();

			try {
				studentService.changePassword(LoggedInUserId.loggedInUserId, currentPassword, newPassword);

				System.out.println("Password changed successfully!");
				System.out.println("***********************************");
			} catch (SomethingWentWrongException | NoRecordFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("New password and confirm password doesnot matches!");
			System.out.println("***********************************");
		}

	}

	static void updateDetails(Scanner sc) {
		System.out.println("\tUpdate Student Details");
		System.out.println("***********************************");
		System.out.print("Enter new email: ");
		String newEmail = sc.next();
		System.out.print("Enter new contact number: ");
		String newContact = sc.next();
		System.out.print("Enter new date of birth (in the format yyyy-MM-dd): ");
		String newDobStr = sc.next();

		// Call the corresponding StudentService method to update the details in the
		// database
		StudentService studentService = new StudentServiceImpl();

		try {
			Student student = studentService.getById(LoggedInUserId.loggedInUserId);
			Student updatedStudent = new Student(student.getFirstName(), student.getLastName(), student.getUsername(),
					student.getPassword(), newEmail, newContact, newDobStr);
			studentService.update(LoggedInUserId.loggedInUserId, updatedStudent);
			System.out.println("Student details updated successfully!");
			System.out.println("***********************************");
		} catch (NoRecordFoundException | SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}
	}

	static void trackProgress(Scanner sc) {
		StudentService studentService = new StudentServiceImpl();

		// Get enrolled courses for the student
		long studentId = LoggedInUserId.loggedInUserId;
		List<Course> enrolledCourses = studentService.getEnrolledCourses(studentId);
		if (enrolledCourses.isEmpty()) {
			System.out.println("You are not enrolled in any courses.");
			return;
		}

		// Display enrolled courses
		System.out.println("\tEnrolled Courses");
		System.out.println("***********************************");
		for (Course course : enrolledCourses) {
			System.out.println("Course ID: " + course.getCourseId());
			System.out.println("Title: " + course.getTitle());
			System.out.println("Description: " + course.getDescription());
			System.out.println("-----------------------------------");
		}

		System.out.print("Enter the course ID to track progress: ");
		long courseId = sc.nextLong();

		// Check if the student is enrolled in the selected course
		boolean isEnrolled = false;
		for (Course course : enrolledCourses) {
			if (course.getCourseId() == courseId) {
				isEnrolled = true;
				break;
			}
		}

		if (!isEnrolled) {
			System.out.println("You are not enrolled in the selected course.");
			return;
		}

		// Get the student's grades for the selected course
		List<Grade> grades = studentService.getGradesByCourseId(studentId, courseId);

		// Display the student's grades for the selected course
		System.out.println("Grades for " + enrolledCourses.get((int) courseId - 1).getTitle() + ":");
		System.out.println("***********************************");
		for (Grade grade : grades) {
			System.out.println("Assignment ID: " + grade.getGradeId());
			System.out.println("Title: " + grade.getCourse().getTitle());
			System.out.println("Description: " + grade.getCourse().getDescription());
			int marks = grade.getMarks();
			String gradeValue;
			if (marks >= 90) {
				gradeValue = "A";
			} else if (marks >= 80) {
				gradeValue = "B";
			} else if (marks >= 70) {
				gradeValue = "C";
			} else if (marks >= 60) {
				gradeValue = "D";
			} else {
				gradeValue = "F";
			}

			System.out.println("Grade: " + gradeValue);
			System.out.println("-----------------------------------");
		}
		System.out.println("***********************************");
	}

	static void accessCourseMaterials(Scanner sc) {
		// Create an instance of the StudentService
		StudentService studentService = new StudentServiceImpl();
		// Get enrolled courses for the student
		long id = LoggedInUserId.loggedInUserId;
		List<Course> enrolledCourses = studentService.getEnrolledCourses(id);
		if (enrolledCourses.isEmpty()) {
			System.out.println("You are not enrolled in any courses.");
			return;
		}

		// Display enrolled courses
		System.out.println("\tEnrolled Courses");
		System.out.println("***********************************");
		for (Course course : enrolledCourses) {
			System.out.println("Course ID: " + course.getCourseId());
			System.out.println("Title: " + course.getTitle());
			System.out.println("Description: " + course.getDescription());
			System.out.println("-----------------------------------");
		}

		System.out.print("Enter the course ID to access course materials: ");
		long courseId = sc.nextLong();

		// Check if the student is enrolled in the selected course
		boolean isEnrolled = false;
		for (Course course : enrolledCourses) {
			if (course.getCourseId() == courseId) {
				isEnrolled = true;
				break;
			}
		}

		if (!isEnrolled) {
			System.out.println("You are not enrolled in the selected course.");
			return;
		}
		System.out.println("***********************************");
	}

	static void submitAssignments(Scanner sc) {
		StudentService studentService = new StudentServiceImpl();
		// Get enrolled courses for the student
		List<Course> enrolledCourses = studentService.getEnrolledCourses(LoggedInUserId.loggedInUserId);
		if (enrolledCourses.isEmpty()) {
			System.out.println("You are not enrolled in any courses.");
			return;
		}

		// Display enrolled courses
		System.out.println("\tEnrolled Courses");
		System.out.println("***********************************");
		for (Course course : enrolledCourses) {
			System.out.println("Course ID: " + course.getCourseId());
			System.out.println("Title: " + course.getTitle());
			System.out.println("Description: " + course.getDescription());
			System.out.println("-----------------------------------");
		}

		System.out.print("Enter the course ID to submit an assignment: ");
		long courseId = sc.nextLong();

		// Check if the student is enrolled in the selected course
		boolean isEnrolled = false;
		for (Course course : enrolledCourses) {
			if (course.getCourseId() == courseId) {
				isEnrolled = true;
				break;
			}
		}

		if (!isEnrolled) {
			System.out.println("You are not enrolled in the selected course.");
			return;
		}

		// Get assignments for the selected course
		List<Assignment> assignments = studentService.getAssignmentsByCourseId(courseId);

		// Display assignments
		System.out.println("Assignments for " + enrolledCourses.get((int) courseId - 1).getTitle() + ":");
		for (Assignment assignment : assignments) {
			System.out.println("Assignment ID: " + assignment.getAssignmentId());
			System.out.println("Title: " + assignment.getTitle());
			System.out.println("Description: " + assignment.getDescription());
			System.out.println("Status: " + assignment.getDueDate());
			System.out.println("-----------------------------------");
		}

		// Prompt student to submit an assignment
	    System.out.print("Enter the assignment ID to submit: ");
	    long assignmentId = sc.nextLong();

	    // Check if the assignment exists for the selected course
	    boolean isAssignmentExists = false;
	    for (Assignment assignment : assignments) {
	        if (assignment.getAssignmentId() == assignmentId) {
	            isAssignmentExists = true;
	            break;
	        }
	    }

	    if (!isAssignmentExists) {
	        System.out.println("Invalid assignment ID. Please try again.");
	        return;
	    }


	    
	}

}
