package com.masai.service;

import java.util.List;

import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Grade;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface StudentService {
    
    /**
     * Retrieves a student's information by their unique identifier (id).
     * 
     * @param id The unique identifier of the student.
     * @return A Student object containing the student's information.
     * @throws NoRecordFoundException if no student record is found with the provided id.
     * @throws SomethingWentWrongException if an unexpected error occurs while fetching the student data.
     */
    Student getById(Long id) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Saves a new student in the system.
     * 
     * @param student The Student object representing the student to be saved.
     * @throws SomethingWentWrongException if an unexpected error occurs while saving the student.
     */
    void save(Student student) throws SomethingWentWrongException;

    /**
     * Updates an existing student's details with the provided student data.
     * 
     * @param studentId The unique identifier of the student to be updated.
     * @param student The Student object containing the updated student data.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the student.
     */
    void update(long studentId, Student student) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes a student record from the system.
     * 
     * @param studentId The unique identifier of the student to be deleted.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     */
    void delete(long studentId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all students available in the system.
     * 
     * @return List of Student objects representing all available students.
     */
    List<Student> getAll();

    /**
     * Registers a new student in the system.
     * 
     * @param student The Student object representing the student to be registered.
     * @throws SomethingWentWrongException if an unexpected error occurs during the registration process.
     */
    void register(Student student) throws SomethingWentWrongException;

    /**
     * Allows a student to log in to the system using their username and password.
     * 
     * @param username The username of the student.
     * @param password The password of the student.
     * @throws SomethingWentWrongException if an unexpected error occurs during the login process.
     */
    void login(String username, String password) throws SomethingWentWrongException;

    /**
     * Enrolls a student in a specific course.
     * 
     * @param studentId The unique identifier of the student.
     * @param courseId The unique identifier of the course to be enrolled in.
     * @throws NoRecordFoundException if no student or course record is found with the provided IDs.
     */
    void enrollStudentInCourse(long studentId, long courseId) throws NoRecordFoundException;

    /**
     * Retrieves a list of courses in which a specific student is enrolled.
     * 
     * @param id The unique identifier of the student.
     * @return List of Course objects representing courses in which the student is enrolled.
     */
    List<Course> getEnrolledCourses(long id);

    /**
     * Retrieves a list of assignments for a specific course.
     * 
     * @param courseId The unique identifier of the course.
     * @return List of Assignment objects representing assignments for the specified course.
     */
    List<Assignment> getAssignmentsByCourseId(long courseId);

    /**
     * Retrieves a list of grades for a specific student and course.
     * 
     * @param studentId The unique identifier of the student.
     * @param courseId The unique identifier of the course.
     * @return List of Grade objects representing the student's grades in the specified course.
     */
    List<Grade> getGradesByCourseId(long studentId, long courseId);

    /**
     * Allows a student to change their password.
     * 
     * @param loggedInUserId The unique identifier of the logged-in student.
     * @param currentPassword The student's current password.
     * @param newPassword The new password to be set.
     * @throws NoRecordFoundException if no student record is found with the provided loggedInUserId.
     * @throws SomethingWentWrongException if an unexpected error occurs during the password change process.
     */
    void changePassword(long loggedInUserId, String currentPassword, String newPassword) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Retrieves a list of available courses for a logged-in student.
     * 
     * @param loggedInUserId The unique identifier of the logged-in student.
     * @return List of Course objects representing available courses for the student.
     */
    List<Course> getAvailableCourses(long loggedInUserId);
}
