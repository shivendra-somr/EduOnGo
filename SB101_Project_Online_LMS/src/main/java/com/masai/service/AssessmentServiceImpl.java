package com.masai.service;

import java.util.List;

import com.masai.dao.AssessmentDao;
import com.masai.dao.AssessmentDaoImpl;
import com.masai.entity.Assessment;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class AssessmentServiceImpl implements AssessmentService {

	@Override
	public void createAssessment(Assessment assessment) throws SomethingWentWrongException {
		AssessmentDao assessmentDao = new AssessmentDaoImpl();
		assessmentDao.createAssessment(assessment);
		
	}

	@Override
	public void updateAssessment(long assessmentId,Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException {
		AssessmentDao assessmentDao = new AssessmentDaoImpl();
		assessmentDao.updateAssessment(assessmentId,assessment);
	}

	@Override
	public void deleteAssessment(long assessmentId) throws NoRecordFoundException {
		AssessmentDao assessmentDao = new AssessmentDaoImpl();
		assessmentDao.deleteAssessment(assessmentId);
	}

	@Override
	public Assessment getAssessmentById(long assessmentId) throws NoRecordFoundException {
		AssessmentDao assessmentDao = new AssessmentDaoImpl();
		return assessmentDao.getAssessmentById(assessmentId);
	}

	@Override
	public List<Assessment> getAllAssessments() {
		AssessmentDao assessmentDao = new AssessmentDaoImpl();
		return assessmentDao.getAllAssessments();
	}

}
