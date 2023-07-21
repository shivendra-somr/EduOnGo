package com.masai.service;

import com.masai.dao.InstructorDao;
import com.masai.dao.InstructorDaoImpl;
import com.masai.entity.Instructor;
import com.masai.exception.SomethingWentWrongException;

public class InstructorServiceImpl implements InstructorService {

	public void registration(Instructor instructor) throws SomethingWentWrongException{
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.registerInstructor(instructor);

	}

	public void login(String username, String password) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.login(username, password);
	}

}
