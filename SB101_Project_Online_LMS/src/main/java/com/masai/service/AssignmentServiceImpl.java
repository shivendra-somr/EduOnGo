package com.masai.service;

import java.util.List;

import com.masai.dao.AssignmentDao;
import com.masai.dao.AssignmentDaoImpl;
import com.masai.entity.Assignment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class AssignmentServiceImpl implements AssignmentService {

	@Override
	public void createAssignment(Assignment assignment) throws SomethingWentWrongException {
		AssignmentDao assignmentDao = new AssignmentDaoImpl();
		assignmentDao.createAssignment(assignment);
		
	}

	@Override
	public void updateAssignment(long assignmentId,Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		AssignmentDao assignmentDao = new AssignmentDaoImpl();
		assignmentDao.updateAssignment(assignmentId,assignment);
	}

	@Override
	public void deleteAssignment(long assignmentId) throws NoRecordFoundException {
		AssignmentDao assignmentDao = new AssignmentDaoImpl();
		assignmentDao.deleteAssignment(assignmentId);
	}

	@Override
	public Assignment getAssignmentById(long assignmentId) throws NoRecordFoundException {
		AssignmentDao assignmentDao = new AssignmentDaoImpl();
		return assignmentDao.getAssignmentById(assignmentId);
	}

	@Override
	public List<Assignment> getAllAssignments() {
		AssignmentDao assignmentDao = new AssignmentDaoImpl();
		return assignmentDao.getAllAssignments();
	}

}
