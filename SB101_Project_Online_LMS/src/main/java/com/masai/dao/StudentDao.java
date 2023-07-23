package com.masai.dao;

import java.util.List;

import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface StudentDao {
	Student getById(Long studentId) throws NoRecordFoundException, SomethingWentWrongException;

	void save(Student student) throws SomethingWentWrongException;

	void update(long studentId,Student student) throws NoRecordFoundException,SomethingWentWrongException;

	void delete(long studentId) throws NoRecordFoundException;

	List<Student> getAll();
}
