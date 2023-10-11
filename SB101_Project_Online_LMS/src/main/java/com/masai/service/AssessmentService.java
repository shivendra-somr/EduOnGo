package com.masai.service;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AssessmentService {

    /**
     * Creates a new assessment record in the system.
     * 
     * @param assessment The Assessment object representing the assessment to be created.
     * @throws SomethingWentWrongException if an unexpected error occurs while creating the assessment.
     */
    void createAssessment(Assessment assessment) throws SomethingWentWrongException;

    /**
     * Updates an existing assessment with the provided assessment data.
     * 
     * @param assessmentId The unique identifier of the assessment to be updated.
     * @param assessment The Assessment object containing the updated assessment data.
     * @throws NoRecordFoundException if no assessment record is found with the provided assessmentId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the assessment.
     */
    void updateAssessment(long assessmentId, Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes an assessment record from the system.
     * 
     * @param assessmentId The unique identifier of the assessment to be deleted.
     * @throws NoRecordFoundException if no assessment record is found with the provided assessmentId.
     */
    void deleteAssessment(long assessmentId) throws NoRecordFoundException;

    /**
     * Retrieves an assessment's information by its unique identifier (assessmentId).
     * 
     * @param assessmentId The unique identifier of the assessment.
     * @return An Assessment object containing the assessment's information.
     * @throws NoRecordFoundException if no assessment record is found with the provided assessmentId.
     */
    Assessment getAssessmentById(long assessmentId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all assessments available in the system.
     * 
     * @return List of Assessment objects representing all available assessments.
     */
    List<Assessment> getAllAssessments();
}
