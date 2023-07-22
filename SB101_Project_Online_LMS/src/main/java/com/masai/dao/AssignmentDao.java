package com.masai.dao;

import java.util.List;

import com.masai.entity.Assignment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AssignmentDao {


	void createAssignment(Assignment assignment) throws SomethingWentWrongException;

	void updateAssignment(long assignmentId, Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteAssignment(long assignmentId) throws NoRecordFoundException;

	Assignment getAssignmentById(long assignmentId) throws NoRecordFoundException;

	List<Assignment> getAllAssignments();
}
