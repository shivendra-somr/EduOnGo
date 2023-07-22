package com.masai.dao;

import java.util.List;

import com.masai.entity.Course;
import com.masai.entity.EMUtils;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class CourseDaoImpl implements CourseDao {
	@Override
	public void createCourse(Course course) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if course with same name exists or not
			Query query = em.createQuery("Select Count(c) From Course c WHERE title = :title");
			query.setParameter("title", course.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same course title
				throw new SomethingWentWrongException("The course " + course.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(course);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add course, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateCourse(long courseId, Course course) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course updatedCourse = em.find(Course.class, courseId);
			if (updatedCourse == null) {
				throw new NoRecordFoundException("No course found with given id :" + courseId);
			}

			// You are here means course exists with given id
			// check if course is to be renamed
			if (!updatedCourse.getTitle().equals(course.getTitle())) {
				// you are here means course is to be renamed, check for no existing course with
				// new name.
				// check if course with same name exists
				Query query = em.createQuery("SELECT count(c) FROM Course c WHERE title = :title");
				query.setParameter("title", course.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means company with given name exists so throw exceptions
					throw new SomethingWentWrongException("Course already exists with title " + course.getTitle());
				}
			}
			// proceed for update operation
			em.getTransaction().begin();
			updatedCourse.setTitle(course.getTitle());
			updatedCourse.setDescription(course.getDescription());
			updatedCourse.setCourseDuration(course.getCourseDuration());
			updatedCourse.setAssignments(course.getAssignments());
			updatedCourse.setAssessment(course.getAssessment());
			updatedCourse.setInstructor(course.getInstructor());
			updatedCourse.setLessons(course.getLessons());
			updatedCourse.setQuizzes(course.getQuizzes());
			updatedCourse.setEnrollments(course.getEnrollments());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update course, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteCourse(long courseId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course deletedCourse = em.find(Course.class, courseId);
			if (deletedCourse == null) {
				throw new NoRecordFoundException("No course found with given id : " + deletedCourse);
			}

			em.getTransaction().begin();

			em.remove(deletedCourse);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete course, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public Course getCourseById(long courseId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course course = em.find(Course.class, courseId);
			if (course == null) {
				throw new NoRecordFoundException("No course found with given id : " + courseId);
			}
			return course;

		} catch (PersistenceException e) {
			System.out.println("Failed to get course, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Course> getAllCourses() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT c FROM Course c");
			List<Course> courses = query.getResultList();

			if (courses.isEmpty()) {
				System.out.println("No courses found in the database.");
			}

			return courses;
		} catch (PersistenceException e) {
			System.out.println("Failed to get course, please try again");
		} finally {
			em.close();
		}
		return null;
	}
}
