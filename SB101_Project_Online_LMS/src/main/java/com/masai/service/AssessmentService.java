package com.masai.service;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AssessmentService {

	void createAssessment(Assessment assessment) throws SomethingWentWrongException;

	void updateAssessment(long assessmentId,Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException;

	void deleteAssessment(long assessmentId) throws NoRecordFoundException;

	Assessment getAssessmentById(long assessmentId) throws NoRecordFoundException;

	List<Assessment> getAllAssessments();
}
