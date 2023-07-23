package com.masai.ui;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Enrollment;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.service.AssessmentService;
import com.masai.service.AssessmentServiceImpl;
import com.masai.service.AssignmentService;
import com.masai.service.AssignmentServiceImpl;
import com.masai.service.CourseService;
import com.masai.service.CourseServiceImpl;
import com.masai.service.InstructorService;
import com.masai.service.InstructorServiceImpl;
import com.masai.service.LessonService;
import com.masai.service.LessonServiceImpl;
import com.masai.service.QuizService;
import com.masai.service.QuizServiceImpl;
import com.masai.service.StudentService;
import com.masai.service.StudentServiceImpl;

public class InstructorUI {
	public static void displayMenu(Scanner sc) {

		int choice = 0;

		do {
			System.out.println("\tInstructor Menu");
			System.out.println("***********************************");
			System.out.println("""
					1. Course Menu
					2. Lesson Menu
					3. Assignment Menu
					4. Assessment Menu
					5. Quiz Menu
					6. Change password
					7. Update details
					8. Delete account
					0. Exit
					""");
			System.out.println("***********************************");
			System.out.print("Enter you choice");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				courseMenu(sc);
				break;
			case 2:
				lessonMenu(sc);
				break;
			case 3:
				assignmentMenu(sc);
				break;
			case 4:
				assessmentMenu(sc);
				break;
			case 5:
				quizMenu(sc);
				break;
			case 6:
				changePassword(sc);
				break;
			case 7:
				updateDetails(sc);
				break;
			case 8:
				deleteAccount(sc);
				break;
			case 0:
				System.out.println("Bye Bye Instructor");
				LoggedInUserId.loggedInUserId = -1;
				break;
			default:
				System.out.println("Invalid input.");
				break;
			}
		} while (choice != 0);

	}

	static void deleteAccount(Scanner sc) {
		System.out.println("\tDelete Account");
		System.out.print("Are you sure you want to delete your account? (yes/no): ");
		String confirmation = sc.next();

		if (confirmation.equalsIgnoreCase("yes")) {
			// Call the corresponding StudentService method to delete the account from the
			// database
			InstructorService instrutorService = new InstructorServiceImpl();

			try {
				instrutorService.deleteInstructorById(LoggedInUserId.loggedInUserId);

				System.out.println("Account deleted successfully!");
				System.out.println("***********************************");
				LoggedInUserId.loggedInUserId = -1;
			} catch (NoRecordFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("Account deletion cancelled.");
			System.out.println("***********************************");
		}
	}

	static void updateDetails(Scanner sc) {
		System.out.println("Update Instructor Details");
		System.out.println("***********************************");
		System.out.print("Enter new email: ");
		String newEmail = sc.next();
		System.out.print("Enter new contact number: ");
		String newContact = sc.next();
		System.out.print("Enter new date of birth (in the format yyyy-MM-dd): ");
		String newDobStr = sc.next();

		Instructor instructor = new Instructor(newEmail, newContact, newDobStr);
		// Call the corresponding InstructorService method to update the details in the
		// database
		InstructorService instructorService = new InstructorServiceImpl();

		try {
			instructorService.updateInstructorDetails(LoggedInUserId.loggedInUserId, instructor);
			System.out.println("Instructor details updated successfully!");
			System.out.println("***********************************");
		} catch (SomethingWentWrongException | NoRecordFoundException e) {
			System.out.println(e.getMessage());
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

		// Verify the new password and confirm password match
		if (newPassword.equals(confirmPassword)) {
			InstructorService instructorService = new InstructorServiceImpl();

			try {
				// Call the corresponding InstructorService method to update the password
				instructorService.changePassword(LoggedInUserId.loggedInUserId, currentPassword, newPassword);

				System.out.println("Password changed successfully!");
				System.out.println("***********************************");
			} catch (SomethingWentWrongException | NoRecordFoundException e) {
				System.out.println(e.getMessage());
			}
		} else {
			System.out.println("New password and confirm password do not match!");
			System.out.println("***********************************");
		}
	}

	public static void login(Scanner sc) {
		System.out.print("Enter username : ");
		String username = sc.next();

		System.out.print("Enter password : ");
		String password = sc.next();

		InstructorService service = new InstructorServiceImpl();
		try {
			service.login(username, password);
			System.out.println("Logged in successfully");
			displayMenu(sc);
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void registration(Scanner sc) {
		System.out.println("\tInstructor Registration");
		System.out.println("***********************************");
		System.out.print("Enter username : ");
		String username = sc.next();

		System.out.print("Enter password : ");
		String password = sc.next();

		System.out.print("Enter email : ");
		String email = sc.next();

		System.out.print("Enter contact : ");
		String contact = sc.next();

		System.out.print("Enter date of birth : ");
		String dob = sc.next();

		Instructor instructor = new Instructor(username, password, email, contact, dob);

		InstructorService service = new InstructorServiceImpl();
		try {
			service.registration(instructor);
			System.out.println("Registration successful! You can now log in with your credentials.");
			System.out.println("***********************************");
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void courseMenu(Scanner sc) {

		int choice = 0;
		do {
			System.out.println("\tCourse Menu");
			System.out.println("***********************************");
			System.out.println("""
					1. Create new course
					2. Update existing course
					3. Delete course by Id
					4. Get course by Id
					5. Get list of all courses
					0. Exit
					""");
			System.out.println("***********************************");
			System.out.print("Enter you choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Create new course
				System.out.print("Enter the course title:");
				String title = sc.next();
				System.out.print("Enter the course description:");
				String description = sc.next();
				System.out.print("Enter the course duration:");
				int duration = sc.nextInt();
				;

				Course course = new Course(title, description, duration);

				CourseService service = new CourseServiceImpl();
				try {
					service.createCourse(course);
					System.out.println("Course created successfully");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				// Update existing course
				System.out.print("Enter the course Id:");
				long courseId = sc.nextLong();

				System.out.print("Enter the course title:");
				String newTitle = sc.next();
				System.out.print("Enter the course description:");
				String newDescription = sc.next();
				System.out.print("Enter the course duration:");
				int newDuration = sc.nextInt();

				Course updatedCourse = new Course(newTitle, newDescription, newDuration);

				CourseService updateService = new CourseServiceImpl();
				try {
					updateService.updateCourse(courseId, updatedCourse);
					System.out.println("Course updated successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException | NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				// delete a course
				System.out.print("Enter the course Id:");
				long deleteCourseId = sc.nextLong();

				CourseService deleteService = new CourseServiceImpl();
				try {
					deleteService.deleteCourse(deleteCourseId);
					System.out.println("Course deleted successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				// Get course by id
				System.out.print("Enter the course Id:");
				long getCourseId = sc.nextLong();

				CourseService getService = new CourseServiceImpl();
				try {
					Course getCourse = getService.getCourseById(getCourseId);

					System.out.println(getCourse);
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:

				CourseService allService = new CourseServiceImpl();
				List<Course> courseList = allService.getAllCourses();

				courseList.forEach(c -> System.out.println(c));
				System.out.println("***********************************");
				break;
			case 0:
				System.out.println("Back to Main Menu");
				System.out.println("***********************************");
				displayMenu(sc);
				break;
			default:
				System.out.println("Invalid details, please try again!");
				break;
			}
		} while (choice != 0);
	}

	public static void lessonMenu(Scanner sc) {

		int choice = 0;

		// Instantiate the service implementation class
		LessonService lessonService = new LessonServiceImpl(); // Replace LessonServiceImpl with your actual service
																// implementation class
		do {
			System.out.println("\tLessong Menu");
			System.out.println("***********************************");
			System.out.println("Lesson Menu:");
			System.out.println("1. Create Lesson");
			System.out.println("2. Read Lesson");
			System.out.println("3. Update Lesson");
			System.out.println("4. Delete Lesson");
			System.out.println("5. Get All Lessons");
			System.out.println("0. Go Back to Main Menu");
			System.out.println("***********************************");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Create Lesson
				System.out.print("Enter Lesson Title: ");
				String title = sc.next();
				System.out.print("Enter Lesson Content: ");
				String content = sc.next();
				// Call the createLesson method from the service implementation class

				Lesson lesson = new Lesson(title, content);
				try {
					lessonService.createLesson(lesson);
					System.out.println("Lesson creatred successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				// Read Lesson
				System.out.print("Enter Lesson ID: ");
				long lessonId = sc.nextLong();
				// Call the getLessonById method from the service implementation class
				try {
					Lesson readLesson = lessonService.getLessonById(lessonId);
					System.out.println("Lesson ID: " + readLesson.getLessonId());
					System.out.println("Title: " + readLesson.getTitle());
					System.out.println("Content: " + readLesson.getContent());
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				// Update Lesson
				System.out.print("Enter Lesson ID: ");
				long lessonIdToUpdate = sc.nextLong();
				sc.nextLine(); // Consume the newline character left by nextLong()
				System.out.print("Enter new Lesson Title: ");
				String newTitle = sc.next();
				System.out.print("Enter new Lesson Content: ");
				String newContent = sc.next();
				// Call the updateLesson method from the service implementation class
				Lesson updatedLesson = new Lesson(newTitle, newContent);
				try {
					lessonService.updateLesson(lessonIdToUpdate, updatedLesson);
					System.out.println("Lesson updated successfully");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException | NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				// Delete Lesson
				System.out.print("Enter Lesson ID to delete: ");
				long lessonIdToDelete = sc.nextLong();
				// Call the deleteLesson method from the service implementation class
				try {
					lessonService.deleteLesson(lessonIdToDelete);
					System.out.println("Lesson deleted successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				List<Lesson> list = lessonService.getAllLessons();
				list.forEach(l -> System.out.println(l));
				System.out.println("***********************************");
				break;
			case 0:
				// Go back to the main menu
				System.out.println("Going back to the main menu.");
				System.out.println("***********************************");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		} while (choice != 0);
	}

	public static void quizMenu(Scanner sc) {

		// Read user choice
		int choice = 0; // Consume the newline character left by nextInt()

		// Instantiate the service implementation class
		QuizService quizService = new QuizServiceImpl(); // Replace QuizServiceImpl with your actual service
															// implementation class
		do {
			// Show menu options
			System.out.println("\tQuiz Menu");
			System.out.println("***********************************");
			System.out.println("1. Create Quiz");
			System.out.println("2. Read Quiz");
			System.out.println("3. Update Quiz");
			System.out.println("4. Delete Quiz");
			System.out.println("0. Go Back to Main Menu");
			System.out.println("***********************************");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Create Quiz
				System.out.print("Enter Quiz Title: ");
				String title = sc.next();

				System.out.print("Enter Quiz Description: ");
				String description = sc.next();

				System.out.print("Enter Quiz Time Limit: ");
				int timeLimit = sc.nextInt();
				// Call the createQuiz method from the service implementation class
				Quiz createdQuiz = new Quiz(title, description, timeLimit);
				try {
					quizService.createQuiz(createdQuiz);
					System.out.println("Quiz created successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 2:
				// Read Quiz
				System.out.print("Enter Quiz ID: ");
				long quizId = sc.nextLong();
				// Call the getQuizById method from the service implementation class
				try {
					Quiz readQuiz = quizService.getQuizById(quizId);
					if (readQuiz != null) {
						System.out.println("Quiz ID: " + readQuiz.getQuizId());
						System.out.println("Title: " + readQuiz.getTitle());
						System.out.println("Desription: " + readQuiz.getDescription());
					}
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				// Update Quiz
				System.out.print("Enter Quiz ID: ");
				long quizIdToUpdate = sc.nextLong();
				sc.nextLine(); // Consume the newline character left by nextLong()
				System.out.print("Enter new Quiz Title: ");
				String newTitle = sc.next();

				System.out.print("Enter new Quiz Title: ");
				String newDescription = sc.next();

				System.out.print("Enter new Quiz Title: ");
				int newTimeLimit = sc.nextInt();

				Quiz updatedQuiz = new Quiz(newTitle, newDescription, newTimeLimit);

				// Call the updateQuiz method from the service implementation class
				try {
					quizService.updateQuiz(quizIdToUpdate, updatedQuiz);
					System.out.println("Quiz updated successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException | SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 4:
				// Delete Quiz
				System.out.print("Enter Quiz ID to delete: ");
				long quizIdToDelete = sc.nextLong();
				// Call the deleteQuiz method from the service implementation class
				try {
					quizService.deleteQuiz(quizIdToDelete);
					System.out.println("Quiz deleted successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 5:
				List<Quiz> list = quizService.getAllQuizzes();
				list.forEach(q -> System.out.println(q));
				System.out.println("***********************************");
				break;
			case 0:
				// Go back to the main menu
				System.out.println("Going back to the main menu.");
				System.out.println("***********************************");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		} while (choice != 0);
	}

	public static void assignmentMenu(Scanner sc) {
		// Show menu options

		int choice = 0;
		AssignmentService assignmentService = new AssignmentServiceImpl();
		do {
			System.out.println("\tAssignment Menu");
			System.out.println("***********************************");
			System.out.println("1. Create Assignment");
			System.out.println("2. Read Assignment");
			System.out.println("3. Update Assignment");
			System.out.println("4. Delete Assignment");
			System.out.println("5. Get All Assignments");
			System.out.println("0. Go Back to Main Menu");
			System.out.println("***********************************");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Create Assignment
				System.out.print("Enter Assignment Title: ");
				String title = sc.next();

				System.out.print("Enter Assignment Description: ");
				String description = sc.next();

				System.out.print("Enter Assignment Due Date: ");
				LocalDate date = LocalDate.parse(sc.next());
				// Call the createAssignment method from the service implementation class
				Assignment createdAssignment = new Assignment(title, description, date);
				try {
					assignmentService.createAssignment(createdAssignment);
					System.out.println("Assignment created successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 2:
				// Read Assignment
				System.out.print("Enter Assignment ID: ");
				long assignmentId = sc.nextLong();
				// Call the getAssignmentById method from the service implementation class

				try {
					Assignment assignment = assignmentService.getAssignmentById(assignmentId);
					if (assignment != null) {
						System.out.println("Assignment ID: " + assignment.getAssignmentId());
						System.out.println("Title: " + assignment.getTitle());
						System.out.println("Description: " + assignment.getDescription());
					}
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 3:
				// Update Assignment
				System.out.print("Enter Assignment ID: ");
				long assignmentIdToUpdate = sc.nextLong();

				System.out.print("Enter new Assignment Title: ");
				String newTitle = sc.nextLine();

				System.out.print("Enter Assignment Description: ");
				String newDescription = sc.nextLine();

				System.out.print("Enter Assignment Due Date: ");
				LocalDate newDate = LocalDate.parse(sc.next());
				// Call the createAssignment method from the service implementation class
				Assignment updatedAssignment = new Assignment(newTitle, newDescription, newDate);
				// Call the updateAssignment method from the service implementation class
				try {
					assignmentService.updateAssignment(assignmentIdToUpdate, updatedAssignment);
					System.out.println("Assignment updated successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException | NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 4:
				// Delete Assignment
				System.out.print("Enter Assignment ID to delete: ");
				long assignmentIdToDelete = sc.nextLong();
				// Call the deleteAssignment method from the service implementation class
				try {
					assignmentService.deleteAssignment(assignmentIdToDelete);
					System.out.println("Assignment deleted successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 5:
				List<Assignment> list = assignmentService.getAllAssignments();
				list.forEach(a -> System.out.println(a));
				System.out.println("***********************************");
				break;
			case 0:
				// Go back to the main menu
				System.out.println("Going back to the main menu.");
				System.out.println("***********************************");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		} while (choice != 0);
	}

	public static void assessmentMenu(Scanner sc) {
		// Show menu options

		int choice = 0;
		AssessmentService assessmentService = new AssessmentServiceImpl();
		do {
			System.out.println("\tAssessment Menu");
			System.out.println("***********************************");
			System.out.println("1. Create Assessment");
			System.out.println("2. Read Assessment");
			System.out.println("3. Update Assessment");
			System.out.println("4. Delete Assessment");
			System.out.println("5. Get All Assessments");
			System.out.println("0. Go Back to Main Menu");
			System.out.println("***********************************");
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				// Create Assignment
				System.out.print("Enter Assessment Title: ");
				String title = sc.next();

				System.out.print("Enter Assessment Description: ");
				String description = sc.next();

				// Call the createAssignment method from the service implementation class
				Assessment createdAssignment = new Assessment(title, description);
				try {
					assessmentService.createAssessment(createdAssignment);
					System.out.println("Assessment created successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 2:
				// Read Assignment
				System.out.print("Enter Assessment ID: ");
				long assessmentId = sc.nextLong();
				// Call the getAssignmentById method from the service implementation class

				try {
					Assessment assessment = assessmentService.getAssessmentById(assessmentId);
					if (assessment != null) {
						System.out.println("Assessment ID: " + assessment.getAssessmentId());
						System.out.println("Title: " + assessment.getTitle());
						System.out.println("Description: " + assessment.getDescription());
					}
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 3:
				// Update Assignment
				System.out.print("Enter Assessment ID: ");
				long assessmentIdToUpdate = sc.nextLong();

				System.out.print("Enter Assessment Title: ");
				String newTitle = sc.next();

				System.out.print("Enter Assessment Description: ");
				String newDescription = sc.next();

				Assessment assessment = new Assessment(newTitle, newDescription);
				// Call the updateAssignment method from the service implementation class
				try {
					assessmentService.updateAssessment(assessmentIdToUpdate, assessment);
					System.out.println("Assessment updated successfully!");
					System.out.println("***********************************");
				} catch (SomethingWentWrongException | NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 4:
				// Delete Assignment
				System.out.print("Enter Assignment ID to delete: ");
				long assessmentIdToDelete = sc.nextLong();
				// Call the deleteAssignment method from the service implementation class
				try {
					assessmentService.deleteAssessment(assessmentIdToDelete);
					System.out.println("Assessment deleted successfully!");
					System.out.println("***********************************");
				} catch (NoRecordFoundException e) {
					System.out.println(e.getMessage());
				}

				break;
			case 5:
				List<Assessment> list = assessmentService.getAllAssessments();
				list.forEach(a -> System.out.println(a));
				System.out.println("***********************************");
				break;
			case 0:
				// Go back to the main menu
				System.out.println("Going back to the main menu.");
				System.out.println("***********************************");
				break;
			default:
				System.out.println("Invalid choice. Please enter a valid option.");
				break;
			}
		} while (choice != 0);
	}
}
