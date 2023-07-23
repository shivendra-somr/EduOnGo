package com.masai.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.masai.dao.CourseDao;
import com.masai.dao.CourseDaoImpl;
import com.masai.dao.StudentDao;
import com.masai.dao.StudentDaoImpl;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.EMUtils;
import com.masai.entity.Enrollment;
import com.masai.entity.Grade;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.Query;

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
	public void update(long studentId, Student student) throws NoRecordFoundException, SomethingWentWrongException {
		StudentDao studentDao = new StudentDaoImpl();
		studentDao.update(studentId, student);
	}

	@Override
	public void delete(long studentId) throws NoRecordFoundException {
		StudentDao studentDao = new StudentDaoImpl();
		studentDao.delete(studentId);
	}

	@Override
	public List<Student> getAll() {
		StudentDao studentDao = new StudentDaoImpl();
		return studentDao.getAll();
	}

	@Override
	public void login(String username, String password) throws SomethingWentWrongException {
		try (EntityManager em = EMUtils.getEntityManager()) {
			// Check if student with the provided username and password exists
			Query query = em.createQuery(
					"SELECT s FROM Student s WHERE s.username = :username AND s.password = :password AND s.isDeleted = false");
			query.setParameter("username", username);
			query.setParameter("password", password);

			try {
				Student student = (Student) query.getSingleResult();
				LoggedInUserId.loggedInUserId = student.getStudentId();
			} catch (NoResultException e) {
				throw new SomethingWentWrongException("The username or password is incorrect");
			}

		} catch (SomethingWentWrongException e) {
			throw new SomethingWentWrongException("Failed to login student, please try again");
		}
	}

	@Override
	public void register(Student student) throws SomethingWentWrongException {
		try (EntityManager em = EMUtils.getEntityManager()) {
			// Check if student with same username exists or not
			Query query = em.createQuery("SELECT COUNT(s) FROM Student s WHERE username = :username");
			query.setParameter("username", student.getUsername());
			if ((long) query.getSingleResult() > 0) {
				// Throwing error message for same username
				throw new SomethingWentWrongException("The username " + student.getUsername() + " already exists!");
			}

			em.getTransaction().begin();

			em.persist(student);

			em.getTransaction().commit();
		} catch (SomethingWentWrongException e) {
			System.out.println("Failed to register student, please try again");
		}
	}

	@Override
	public List<Course> getEnrolledCourses(long id) {
		StudentDao studentDao = new StudentDaoImpl();
		Student student = new Student();
		try {
			student = studentDao.getById(id);
			if (student == null) {
				System.out.println("Student not found with ID: " + id);
				return new ArrayList<>(); // Return an empty list if student not found
			}

		} catch (NoRecordFoundException | SomethingWentWrongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Return the list of enrolled courses for the student
		return student.getEnrollments().stream().map(Enrollment::getCourse).collect(Collectors.toList());

	}

	@Override
	public List<Assignment> getAssignmentsByCourseId(long courseId) {
		CourseDao courseDao = new CourseDaoImpl();

		Course course = new Course();
		try {
			course = courseDao.getCourseById(courseId);
		} catch (NoRecordFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (course == null) {
			System.out.println("Course not found with ID: " + courseId);
			return new ArrayList<>(); // Return an empty list if course not found
		}

		// Return the list of assignments for the course
		return course.getAssignments();
	}

	@Override
	public List<Grade> getGradesByCourseId(long studentId, long courseId) {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT g FROM Grade g WHERE g.student.studentId = :studentId "
					+ "AND g.course.courseId = :courseId");
			query.setParameter("studentId", studentId);
			query.setParameter("courseId", courseId);
			return query.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void changePassword(long loggedInUserId, String currentPassword, String newPassword)
			throws NoRecordFoundException, SomethingWentWrongException {
		// Retrieve the Student entity from the database
		StudentDao studentDao = new StudentDaoImpl();
		Student student = studentDao.getById(loggedInUserId);

		// Check if the student exists
		if (student == null) {
			throw new SomethingWentWrongException("Student with ID " + loggedInUserId + " not found.");
		}

		// Check if the provided currentPassword matches the existing password of the
		// student
		if (!student.getPassword().equals(currentPassword)) {
			throw new SomethingWentWrongException("Current password is incorrect.");
		}

		// Update the password with the new password
		student.setPassword(newPassword);

		// Save the updated Student entity back to the database
		studentDao.update(loggedInUserId, student);
	}

	@Override
	public void enrollStudentInCourse(long studentId, long courseId) throws NoRecordFoundException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			// Check if student with given id exists
			Student student = em.find(Student.class, studentId);
			if (student == null) {
				throw new NoRecordFoundException("No student found with given id: " + studentId);
			}

			// Check if course with given id exists
			Course course = em.find(Course.class, courseId);
			if (course == null) {
				throw new NoRecordFoundException("No course found with given id: " + courseId);
			}

			// Create enrollment for the student and course
			Enrollment enrollment = new Enrollment();
			enrollment.setStudent(student);
			enrollment.setCourse(course);

			// Save the enrollment
			em.getTransaction().begin();
			em.persist(enrollment);
			em.getTransaction().commit();
		} catch (NoRecordFoundException e) {
			throw new NoRecordFoundException("No record found!");
		} finally {
			em.close();
		}
	}

	@Override
	public List<Course> getAvailableCourses(long loggedInUserId) {
		EntityManager em = EMUtils.getEntityManager();

		try {
			// Query to get all courses that the student is not enrolled in
			String jpql = "SELECT c FROM Course c WHERE c.courseId NOT IN "
					+ "(SELECT e.course.courseId FROM Enrollment e WHERE e.student.studentId = :studentId)";
			Query query = em.createQuery(jpql);
			query.setParameter("studentId", loggedInUserId);

			return query.getResultList();
		} finally {
			em.close();
		}
	}

}
