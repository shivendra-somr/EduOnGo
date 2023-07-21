package com.masai.ui;

import java.util.Scanner;

import com.masai.entity.Instructor;
import com.masai.exception.SomethingWentWrongException;
import com.masai.service.InstructorService;
import com.masai.service.InstructorServiceImpl;

public class InstructorUI {

	public static void login(Scanner sc) {
		System.out.print("Enter username : ");
		String username = sc.next();

		System.out.print("Enter password : ");
		String password = sc.next();

		// make a service and call the login method here
		InstructorService service = new InstructorServiceImpl();
		try {
			service.login(username, password);
			System.out.println("Logged in successfully");
		} catch (SomethingWentWrongException e) {
			System.out.println(e.getMessage());
		}

	}

	static void registration(Scanner sc) {
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

}
