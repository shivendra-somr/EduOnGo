package com.masai.dao;

import java.util.List;

import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface StudentDao {

    /**
     * Retrieves a student's information by their unique identifier (studentId) from the data store.
     * 
     * @param studentId The unique identifier of the student.
     * @return A Student object containing the student's information.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     * @throws SomethingWentWrongException if an unexpected error occurs while fetching the student data.
     */
    Student getById(Long studentId) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Saves a new student record in the data store.
     * 
     * @param student The Student object representing the student to be saved.
     * @throws SomethingWentWrongException if an unexpected error occurs while saving the student.
     */
    void save(Student student) throws SomethingWentWrongException;

    /**
     * Updates an existing student's details with the provided student data in the data store.
     * 
     * @param studentId The unique identifier of the student to be updated.
     * @param student The Student object containing the updated student data.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the student.
     */
    void update(long studentId, Student student) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes a student record from the data store.
     * 
     * @param studentId The unique identifier of the student to be deleted.
     * @throws NoRecordFoundException if no student record is found with the provided studentId.
     */
    void delete(long studentId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all students available in the data store.
     * 
     * @return List of Student objects representing all available students.
     */
    List<Student> getAll();
}
