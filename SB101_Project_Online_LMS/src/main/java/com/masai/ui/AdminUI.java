package com.masai.ui;

import java.util.List;
import java.util.Scanner;

import com.masai.entity.Course;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;
import com.masai.service.AdminService;
import com.masai.service.AdminServiceImpl;

public class AdminUI {

	static void monitorCourseProgress(Scanner sc) {
		AdminService adminService = new AdminServiceImpl();
        List<Course> allCourses = adminService.getAllCourses();

        System.out.println("\tAll Courses");
        for (Course course : allCourses) {
            System.out.println("Course ID: " + course.getCourseId());
            System.out.println("Title: " + course.getTitle());
            System.out.println("Description: " + course.getDescription());
            System.out.println("Duration: " + course.getCourseDuration());
            System.out.println("***********************************");
        }
	}

	static void trackStudent(Scanner sc) {
		System.out.print("Enter the student ID: ");
        long studentId = sc.nextLong();

        AdminService adminService = new AdminServiceImpl();
        try {
			try {
				Student student = adminService.getStudentById(studentId);
				System.out.println("Student Details:");
	            System.out.println("Username: " + student.getUsername());
	            System.out.println("Email: " + student.getEmail());
	            System.out.println("Contact: " + student.getContact());
	            System.out.println("Date of Birth: " + student.getDob());
	            System.out.println("Enrolled Courses:");
			} catch (SomethingWentWrongException e) {
				System.out.println(e.getMessage());
			}

            

            List<Course> enrolledCourses = adminService.getEnrolledCourses(studentId);
            for (Course course : enrolledCourses) {
                System.out.println("Course ID: " + course.getCourseId());
                System.out.println("Title: " + course.getTitle());
                System.out.println("Description: " + course.getDescription());
                System.out.println("Duration: " + course.getCourseDuration());
                System.out.println("***********************************");
            }
        } catch (NoRecordFoundException e) {
            System.out.println(e.getMessage());
        }
	}

	static void generateReportOnCourseStatistics(Scanner sc) {
		AdminService adminService = new AdminServiceImpl();
        List<Course> allCourses = adminService.getAllCourses();

        System.out.println("Course Statistics Report");
        for (Course course : allCourses) {
            System.out.println("Course Title: " + course.getTitle());
            System.out.println("Number of Students Enrolled: " + adminService.getNumOfStudentsEnrolled(course.getCourseId()));
            System.out.println("-----------------------------------");
        }
        System.out.println("***********************************");
	}
}
