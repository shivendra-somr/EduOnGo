package com.masai.service;

import java.util.List;

import com.masai.dao.InstructorDao;
import com.masai.dao.InstructorDaoImpl;
import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class InstructorServiceImpl implements InstructorService {

	public void registration(Instructor instructor) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.registerInstructor(instructor);

	}

	public void login(String username, String password) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.login(username, password);
	}

	@Override
	public Instructor getInstructorById(int instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getInstructorById(instructorId);
	}

	@Override
	public void updateInstructorDetails(Instructor instructor)
			throws NoRecordFoundException, SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateInstructorDetails(instructor);
		;
	}

	@Override
	public void deleteInstructorById(int instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteInstructorById(instructorId);
	}

	@Override
	public List<Instructor> getAllInstructor() throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllInstructor();
	}

}
