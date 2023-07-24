package com.masai.dao;

import java.util.List;

import com.masai.entity.EMUtils;
import com.masai.entity.Student;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class StudentDaoImpl implements StudentDao {

	@Override
	public Student getById(Long id) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			Student student = em.find(Student.class, id);
			if (student == null) {
				throw new NoRecordFoundException("Student not found with provided id");
			}
			return student;
		} catch (PersistenceException e) {
			System.out.println("Something went wrong, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public void save(Student student) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if student with same name exists or not
			Query query = em.createQuery("Select Count(s) From Student s WHERE username = :username");
			query.setParameter("username", student.getUsername());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same student username
				throw new SomethingWentWrongException("The student " + student.getUsername() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(student);
			System.out.println("Registration successful! You can now log in with your credentials.");
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add student, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void update(long studentId, Student student) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();

			// Check if the student with the given ID exists
			Student updatedStudent = em.find(Student.class, studentId);
			if (updatedStudent == null) {
				throw new NoRecordFoundException("No student found with ID: " + studentId);
			}

			// You are here means the student exists with the given ID
			// Check if student email is being updated and if the new email already exists
			if (!updatedStudent.getEmail().equals(student.getEmail())) {
				Query query = em.createQuery("SELECT count(s) FROM Student s WHERE email = :email");
				query.setParameter("email", student.getEmail());
				if ((Long) query.getSingleResult() > 0) {
					throw new SomethingWentWrongException(
							"Student with email " + student.getEmail() + " already exists.");
				}
			}

			em.getTransaction().begin();
			updatedStudent.setFirstName(student.getFirstName());
			updatedStudent.setLastName(student.getLastName());
			updatedStudent.setUsername(student.getUsername());
			updatedStudent.setPassword(student.getPassword());
			updatedStudent.setEmail(student.getEmail());
			updatedStudent.setContact(student.getContact());
			updatedStudent.setDob(student.getDob());
			em.getTransaction().commit();

		} catch (SomethingWentWrongException e) {
			em.getTransaction().rollback();
			throw new SomethingWentWrongException("Failed to update student, please try again");
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void delete(long studentId) throws NoRecordFoundException {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			// check if student with given id exists
			Student deletedStudent = em.find(Student.class, studentId);
			if (deletedStudent == null) {
				throw new NoRecordFoundException("No student found with given id: " + studentId);
			}

			em.getTransaction().begin();
			deletedStudent.setDeleted(true);
			em.getTransaction().commit();
		} catch (NoRecordFoundException e) {
			em.getTransaction().rollback();
			throw new NoRecordFoundException("Failed to delete student, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public List<Student> getAll() {
		EntityManager em = null;
		try {
			em = EMUtils.getEntityManager();
			// Create and execute the query to get all students
			Query query = em.createQuery("SELECT s FROM Student s");
			List<Student> students = query.getResultList();
			if (students.isEmpty()) {
				System.out.println("No students found in the database.");
			}
			return students;
		} catch (PersistenceException e) {
			System.out.println("Failed to get students, please try again");
		} finally {
			em.close();
		}
		return null;
	}

}
