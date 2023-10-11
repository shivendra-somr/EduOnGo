package com.masai.dao;

import java.util.List;

import com.masai.entity.Instructor;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface InstructorDao {

    /**
     * Registers a new instructor in the data store.
     * 
     * @param instructor The Instructor object representing the instructor to be registered.
     * @throws SomethingWentWrongException if an unexpected error occurs during the registration process.
     */
    void registerInstructor(Instructor instructor) throws SomethingWentWrongException;

    /**
     * Allows an instructor to log in to the system using their username and password.
     * 
     * @param username The username of the instructor.
     * @param password The password of the instructor.
     * @throws SomethingWentWrongException if an unexpected error occurs during the login process.
     */
    void login(String username, String password) throws SomethingWentWrongException;

    /**
     * Retrieves an instructor's information by their unique identifier (instructorId) from the data store.
     * 
     * @param instructorId The unique identifier of the instructor.
     * @return An Instructor object containing the instructor's information.
     * @throws NoRecordFoundException if no instructor record is found with the provided instructorId.
     */
    Instructor getInstructorById(long instructorId) throws NoRecordFoundException;

    /**
     * Updates an instructor's details with the provided instructor data in the data store.
     * 
     * @param instructorId The unique identifier of the instructor to be updated.
     * @param instructor The Instructor object containing the updated instructor data.
     * @throws NoRecordFoundException if no instructor record is found with the provided instructorId.
     * @throws SomethingWentWrongException if an unexpected error occurs during the update process.
     */
    void updateInstructorDetails(long instructorId, Instructor instructor) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes an instructor record from the data store.
     * 
     * @param instructorId The unique identifier of the instructor to be deleted.
     * @throws NoRecordFoundException if no instructor record is found with the provided instructorId.
     */
    void deleteInstructorById(long instructorId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all instructors available in the data store.
     * 
     * @return List of Instructor objects representing all available instructors.
     * @throws NoRecordFoundException if no instructor records are found.
     */
    List<Instructor> getAllInstructor() throws NoRecordFoundException;
}
