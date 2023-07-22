package com.masai.service;

import java.util.List;

import com.masai.entity.Course;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface CourseService {

	void createCourse(Course course) throws SomethingWentWrongException;

	void updateCourse(long courseId,Course course) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteCourse(long courseId) throws NoRecordFoundException;

	Course getCourseById(long courseId) throws NoRecordFoundException;

	List<Course> getAllCourses();
}
