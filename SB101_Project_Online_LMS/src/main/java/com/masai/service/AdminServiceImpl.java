package com.masai.service;

import java.util.List;

import com.masai.entity.Course;
import com.masai.entity.EMUtils;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

public class AdminServiceImpl implements AdminService {

	@Override
	public List<Course> getAllCourses() {
		CourseService courseService = new CourseServiceImpl();
		return courseService.getAllCourses();
	}

	@Override
	public Student getStudentById(long studentId) throws NoRecordFoundException, SomethingWentWrongException {
		StudentService studentService = new StudentServiceImpl();
		return studentService.getById(studentId);
	}

	@Override
    public List<Course> getEnrolledCourses(long studentId) {
        EntityManager em = EMUtils.getEntityManager();
        try {
            Query query = em.createQuery("SELECT e.course FROM Enrollment e WHERE e.student.studentId = :studentId");
            query.setParameter("studentId", studentId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    @Override
    public int getNumOfStudentsEnrolled(long courseId) {
        EntityManager em = EMUtils.getEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(e.student) FROM Enrollment e WHERE e.course.courseId = :courseId");
            query.setParameter("courseId", courseId);
            return ((Long) query.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
}
