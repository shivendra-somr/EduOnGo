package com.masai.service;

import java.util.List;

import com.masai.entity.Course;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface AdminService {

	List<Course> getAllCourses();

	Student getStudentById(long studentId) throws NoRecordFoundException, SomethingWentWrongException;

	List<Course> getEnrolledCourses(long studentId);

	int getNumOfStudentsEnrolled(long courseId);

}
