package com.masai.service;

import java.util.List;

import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Grade;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface StudentService {
	Student getById(Long id) throws NoRecordFoundException, SomethingWentWrongException;
    void save(Student student) throws SomethingWentWrongException;
    void update(long studentId,Student student) throws NoRecordFoundException,SomethingWentWrongException;
    void delete(long studentId) throws NoRecordFoundException;
    List<Student> getAll();
	void register(Student student) throws SomethingWentWrongException;
	void login(String username, String password) throws SomethingWentWrongException;
	List<Course> getEnrolledCourses(long id);
	List<Assignment> getAssignmentsByCourseId(long courseId);
	List<Grade> getGradesByCourseId(long studentId, long courseId);
	void changePassword(long loggedInUserId, String currentPassword, String newPassword) throws NoRecordFoundException, SomethingWentWrongException;
}
