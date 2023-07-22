package com.masai.dao;

import java.util.List;

import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface StudentDao {
	Student getById(Long id) throws NoRecordFoundException, SomethingWentWrongException;
    void save(Student student) throws SomethingWentWrongException;
    void update(Student student) throws SomethingWentWrongException;
    void delete(Student student);
    List<Student> getAll();
}
