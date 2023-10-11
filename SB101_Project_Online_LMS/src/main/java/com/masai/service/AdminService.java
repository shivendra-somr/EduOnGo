package com.masai.service;

import java.util.List;

import com.masai.entity.Course;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AdminService {
    
    /**
     * Retrieves a list of all available courses.
     * 
     * @return List of Course objects representing all available courses.
     * @throws SomethingWentWrongException if an unexpected error occurs while fetching the course data.
     */
    List<Course> getAllCourses();

    /**
     * Retrieves a student's information by their unique identifier (studentId).
     * 
     * @param studentId The unique identifier of the student.
     * @return A Student object containing the student's information.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     * @throws SomethingWentWrongException if an unexpected error occurs while fetching the student data.
     */
    Student getStudentById(long studentId) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Retrieves a list of courses in which a specific student is enrolled.
     * 
     * @param studentId The unique identifier of the student.
     * @return List of Course objects representing courses in which the student is enrolled.
     */
    List<Course> getEnrolledCourses(long studentId);

    /**
     * Get the number of students enrolled in a specific course.
     * 
     * @param courseId The unique identifier of the course.
     * @return The number of students enrolled in the course.
     */
    int getNumOfStudentsEnrolled(long courseId);
}
