package com.masai.service;

import java.util.List;

import com.masai.dao.CourseDao;
import com.masai.dao.CourseDaoImpl;
import com.masai.entity.Course;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class CourseServiceImpl implements CourseService {

	@Override
	public void createCourse(Course course) throws SomethingWentWrongException {
		CourseDao courseDao = new CourseDaoImpl();
		courseDao.createCourse(course);
	}

	@Override
	public void updateCourse(long courseId,Course course) throws SomethingWentWrongException, NoRecordFoundException {
		CourseDao courseDao = new CourseDaoImpl();
		courseDao.updateCourse(courseId,course);
	}

	@Override
	public void deleteCourse(long courseId) throws NoRecordFoundException {
		CourseDao courseDao = new CourseDaoImpl();
		courseDao.deleteCourse(courseId);
	}

	@Override
	public Course getCourseById(long courseId) throws NoRecordFoundException {
		CourseDao courseDao = new CourseDaoImpl();
		return courseDao.getCourseById(courseId);
	}

	@Override
	public List<Course> getAllCourses() {
		CourseDao courseDao = new CourseDaoImpl();
		return courseDao.getAllCourses();
	}

}
