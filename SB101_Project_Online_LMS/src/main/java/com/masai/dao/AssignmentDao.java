package com.masai.dao;

import java.util.List;

import com.masai.entity.Assignment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AssignmentDao {

    /**
     * Creates a new assignment record in the data store.
     * 
     * @param assignment The Assignment object representing the assignment to be created.
     * @throws SomethingWentWrongException if an unexpected error occurs while creating the assignment.
     */
    void createAssignment(Assignment assignment) throws SomethingWentWrongException;

    /**
     * Updates an existing assignment record in the data store with the provided assignment data.
     * 
     * @param assignmentId The unique identifier of the assignment to be updated.
     * @param assignment The Assignment object containing the updated assignment data.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the assignment.
     * @throws NoRecordFoundException if no assignment record is found with the provided assignmentId.
     */
    void updateAssignment(long assignmentId, Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException;

    /**
     * Deletes an assignment record from the data store.
     * 
     * @param assignmentId The unique identifier of the assignment to be deleted.
     * @throws NoRecordFoundException if no assignment record is found with the provided assignmentId.
     */
    void deleteAssignment(long assignmentId) throws NoRecordFoundException;

    /**
     * Retrieves an assignment's information by its unique identifier (assignmentId) from the data store.
     * 
     * @param assignmentId The unique identifier of the assignment.
     * @return An Assignment object containing the assignment's information.
     * @throws NoRecordFoundException if no assignment record is found with the provided assignmentId.
     */
    Assignment getAssignmentById(long assignmentId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all assignments available in the data store.
     * 
     * @return List of Assignment objects representing all available assignments.
     */
    List<Assignment> getAllAssignments();
}
