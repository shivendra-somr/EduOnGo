package com.masai.service;

import java.util.List;

import com.masai.dao.StudentDao;
import com.masai.dao.StudentDaoImpl;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class StudentServiceImpl implements StudentService {

	@Override
	public Student getById(Long id) throws NoRecordFoundException, SomethingWentWrongException {
		StudentDao studentDao = new StudentDaoImpl();
		return studentDao.getById(id);
	}

	@Override
	public void save(Student student) throws SomethingWentWrongException {
		StudentDao studentDao = new StudentDaoImpl();
		studentDao.save(student);

	}

	@Override
	public void update(Student student) throws SomethingWentWrongException {
		StudentDao studentDao = new StudentDaoImpl();
		studentDao.update(student);
	}

	@Override
	public void delete(Student student) {
		StudentDao studentDao = new StudentDaoImpl();
		studentDao.delete(student);
	}

	@Override
	public List<Student> getAll() {
		StudentDao studentDao = new StudentDaoImpl();
		return studentDao.getAll();
	}

}
