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

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add student, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void update(Student student) throws SomethingWentWrongException {
		
	}

	@Override
	public void delete(Student student) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<Student> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

}
