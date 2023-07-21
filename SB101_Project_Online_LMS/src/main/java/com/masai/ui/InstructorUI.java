package com.masai.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Enrollment;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.service.InstructorService;
import com.masai.service.InstructorServiceImpl;

public class InstructorUI {
	public static void displayMenu(Scanner sc) {
		System.out.println("""
				1. Course Menu
				2. Lesson Menu
				3. Assignment Menu
				4. Assessment Menu
				5. Quiz Menu
				0. Exit
				""");
		int choice = 0;
		do {
			choice = sc.nextInt();
			switch(choice) {
				case 1 -> courseMenu(sc);
				case 2 -> lessonMenu(sc);
				case 3 -> assignmentMenu(sc);
				case 4 -> assessmentMenu(sc);
				case 5 -> quizMenu(sc);
				case 0 -> {System.out.println("Bye Bye Instructor");
							LoggedInUserId.loggedInUserId = -1;
							}
				default -> System.out.println("Invalid input.");
			}
		} while (choice != 0);
		
		
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
			System.out.println("Registration completed successfully");
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void courseMenu(Scanner sc) {
		System.out.println("""
				1. Create new course
				2. Update existing course
				3. Delete course by Id
				4. Get course by Id
				5. Get list of all courses 
				0. Exit
				""");
		int choice = 0;
		do {
			choice = sc.nextInt();
			switch(choice) {
				case 1 -> {
					System.out.print("Enter the course title:");
	                String title = sc.next();
	                System.out.print("Enter the course description:");
	                String description = sc.next();
	                System.out.print("Enter the course duration:");
	                int duration = sc.nextInt();
	                System.out.println("Enter the instructor ID for the course:");
	                int instructorId = sc.nextInt();
	                InstructorService service = new InstructorServiceImpl();
	                Instructor instructor = new Instructor();
	                try {
						instructor = service.getInstructorById(instructorId);
					} catch (NoRecordFoundException e) {
						System.out.println(e.getMessage());
					}
	                if (instructor == null) {
	                    System.out.println("No instructor found with the given ID.");
	                    break;
	                }
	                List<Lesson> lessons = new ArrayList<>();
	                List<Assignment> assignments = new ArrayList<>();
	                List<Quiz> quizzes = new ArrayList<>();
	                Assessment assessment = null;
	                List<Enrollment> enrollments = new ArrayList<>();
				}
				case 2 -> {
					
				}
				case 3 -> {
					
				}
				case 4 -> {
					
				}
				case 5 -> {
					
				}
				case 0 -> {System.out.println("Back to Main Menu");
							displayMenu(sc);
				}
				
			}
		} while (choice != 0);
	}
	
	public static void lessonMenu(Scanner sc) {
		System.out.println("Lesson Menu:");
	    System.out.println("1. Create Lesson");
	    System.out.println("2. Read Lesson");
	    System.out.println("3. Update Lesson");
	    System.out.println("4. Delete Lesson");
	    System.out.println("0. Go Back to Main Menu");
	    System.out.print("Enter your choice: ");

	    // Read user choice
	    int choice = sc.nextInt();
	    sc.nextLine(); // Consume the newline character left by nextInt()

	    // Instantiate the service implementation class
	    InstructorService lessonService = new InstructorServiceImpl(); // Replace LessonServiceImpl with your actual service implementation class

	    switch (choice) {
	        case 1:
	            // Create Lesson
	            System.out.print("Enter Lesson Title: ");
	            String title = sc.nextLine();
	            System.out.print("Enter Lesson Content: ");
	            String content = sc.nextLine();
	            // Call the createLesson method from the service implementation class
	            
	            Lesson lesson = new Lesson(title, content);
	            lessonService.createLesson(lesson);
	            break;
	        case 2:
	            // Read Lesson
	            System.out.print("Enter Lesson ID: ");
	            long lessonId = sc.nextLong();
	            // Call the getLessonById method from the service implementation class
	            Lesson lesson = lessonService.getLessonById(lessonId);
	            if (lesson != null) {
	                System.out.println("Lesson ID: " + lesson.getLessonId());
	                System.out.println("Title: " + lesson.getTitle());
	                System.out.println("Content: " + lesson.getContent());
	            } else {
	                System.out.println("Lesson not found with ID: " + lessonId);
	            }
	            break;
	        case 3:
	            // Update Lesson
	            System.out.print("Enter Lesson ID: ");
	            long lessonIdToUpdate = sc.nextLong();
	            sc.nextLine(); // Consume the newline character left by nextLong()
	            System.out.print("Enter new Lesson Title: ");
	            String newTitle = sc.nextLine();
	            System.out.print("Enter new Lesson Content: ");
	            String newContent = sc.nextLine();
	            // Call the updateLesson method from the service implementation class
	            boolean isUpdated = lessonService.updateLesson(lessonIdToUpdate, newTitle, newContent);
	            if (isUpdated) {
	                System.out.println("Lesson with ID " + lessonIdToUpdate + " updated successfully.");
	            } else {
	                System.out.println("Failed to update lesson with ID: " + lessonIdToUpdate);
	            }
	            break;
	        case 4:
	            // Delete Lesson
	            System.out.print("Enter Lesson ID to delete: ");
	            long lessonIdToDelete = sc.nextLong();
	            // Call the deleteLesson method from the service implementation class
	            boolean isDeleted = lessonService.deleteLesson(lessonIdToDelete);
	            if (isDeleted) {
	                System.out.println("Lesson with ID " + lessonIdToDelete + " deleted successfully.");
	            } else {
	                System.out.println("Failed to delete lesson with ID: " + lessonIdToDelete);
	            }
	            break;
	        case 0:
	            // Go back to the main menu
	            System.out.println("Going back to the main menu.");
	            break;
	        default:
	            System.out.println("Invalid choice. Please enter a valid option.");
	            break;
	    }
	}
	public static void quizMenu(Scanner sc) {
		
	}

	public static void assignmentMenu(Scanner sc) {
	
	}
	public static void assessmentMenu(Scanner sc) {
	
	}
}
