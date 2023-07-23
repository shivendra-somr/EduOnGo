package com.masai.ui;

import java.util.Scanner;

public class App {
	static void displayAdminMenu() {
		System.out.println("\tAdmin Menu");
		System.out.println("***********************************");
		System.out.println("1. Monitor Course Progress");
		System.out.println("2. Track Student Performance by Id");
		System.out.println("3. Generate Report on Course Statistics");
		System.out.println("0. Logout");
		System.out.println("***********************************");
	}

	static void adminMenu(Scanner sc) {
		int choice = 0;
		do {
			displayAdminMenu();
			System.out.print("Enter your choice : ");
			choice = sc.nextInt();
			switch(choice) {
				case 1:
					AdminUI.monitorCourseProgress(sc);
					break;
				case 2:
					AdminUI.trackStudent(sc);
					break;
				case 3:
					AdminUI.generateReportOnCourseStatistics(sc);
					break;
				case 0:
					System.out.println("Bye Bye Admin");
    				break;
    			default:
    				System.out.println("Invalid Selection, try again");
			}
		} while (choice != 0);

	}

	

	static void adminLogin(Scanner sc) {
		System.out.print("Enter username ");
		String username = sc.next();
		System.out.print("Enter password ");
		String password = sc.next();
		if (username.equals("admin") && password.equals("admin")) {
			adminMenu(sc);
		} else {
			System.out.println("Invalid Username of Password");
		}
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		int choice = 0;
		do {
			System.out.println("\tWelcome to EduOnGo");
			System.out.println("***********************************");
			System.out.println("1. Admin Login");
			System.out.println("2. Intructor Login");
			System.out.println("3. Instructor Registration");
			System.out.println("4. Student Login");
			System.out.println("5. Student Registration");
			System.out.println("0. Exit");
			System.out.println("***********************************");
			System.out.print("Enter Selection ");
			choice = sc.nextInt();
			switch (choice) {
			case 1:
				adminLogin(sc);
				break;
			case 2:
				InstructorUI.login(sc);
				break;
			case 3:
				InstructorUI.registration(sc);
				break;
			case 4:
				StudentUI.studentLogin(sc);
				break;
			case 5:
				StudentUI.studentRegistration(sc);
				break;
			case 0:
				System.out.println("Thanks for using the services");
				break;
			default:
				System.out.println("Invalid Selection, try again");
			}
		} while (choice != 0);
		sc.close();
	}

}
