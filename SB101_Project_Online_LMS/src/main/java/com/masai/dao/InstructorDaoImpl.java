package com.masai.dao;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.EMUtils;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class InstructorDaoImpl implements InstructorDao {

	public void registerInstructor(Instructor instructor) throws SomethingWentWrongException {

		try (EntityManager em = EMUtils.getEntityManager()) {
			// check if instructor with same username exists or not
			Query query = em.createQuery("Select Count(i) From Instructor i WHERE username = :username");
			query.setParameter("username", instructor.getUsername());
			if ((long) query.getSingleResult() > 0) {
				// throwing error msg for same username
				throw new SomethingWentWrongException("The username " + instructor.getUsername() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(instructor);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			System.out.println("Failed to register instructor, please try again");
		}

	}

	@Override
	public void login(String username, String password) throws SomethingWentWrongException {

		try (EntityManager em = EMUtils.getEntityManager()) {
			// check if instructor with same username exists or not
			Query query = em.createQuery(
					"Select i From Instructor i WHERE i.username = :username AND i.password =:password AND i.isDeleted = false");
			query.setParameter("username", username);
			query.setParameter("password", password);
			List<Instructor> instructors = query.getResultList();
			if (instructors.isEmpty()) {
				// you are here means instructor with given name does not exists so throw
				// exceptions
				throw new SomethingWentWrongException("The username or password is incorrect");
			}
			LoggedInUserId.loggedInUserId = instructors.get(0).getInstructorId();
		} catch (PersistenceException e) {
			System.out.println("Failed to login instructor, please try again ");
		}
	}

	@Override
	public Instructor getInstructorById(long instructorId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor exists or not
			Instructor instructor = em.find(Instructor.class, instructorId);

			if (instructor == null) {
				throw new NoRecordFoundException("No instructor found with given id : " + instructorId);
			}
			return instructor;
		} catch (PersistenceException e) {
			System.out.println("Failed to register instructor, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public void updateInstructorDetails(long instructorId, Instructor instructor)
			throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor with given id exists
			Instructor updatedInstructor = em.find(Instructor.class, instructorId);
			if (updatedInstructor == null) {
				throw new NoRecordFoundException("No instructor found with given id :" + instructorId);
			}

			// You are here means instructor exists with given id
			// check if instructor is to be renamed
			if (!updatedInstructor.getUsername().equals(instructor.getUsername())) {
				// you are here means instructor is to be renamed, check for no existing
				// instructor with
				// new name.
				// check if instructor with same name exists
				Query query = em.createQuery("SELECT i FROM Instructor i WHERE username = :username");
				query.setParameter("title", instructor.getUsername());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means instructor with given name exists so throw exceptions
					throw new SomethingWentWrongException(
							"Instructor already exists with title " + instructor.getUsername());
				}
			}
			// proceed for update operation
			em.getTransaction().begin();
			updatedInstructor.setUsername(instructor.getUsername());
			updatedInstructor.setPassword(instructor.getPassword());
			updatedInstructor.setContact(instructor.getContact());
			updatedInstructor.setDob(instructor.getDob());
			updatedInstructor.setEmail(instructor.getEmail());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update instructor, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteInstructorById(long instructorId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor exists or not
			Instructor instructor = em.find(Instructor.class, instructorId);

			if (instructor == null) {
				throw new NoRecordFoundException("No instructor found with given id : " + instructorId);
			}
			em.getTransaction().begin();
			instructor.setDeleted(true);
			em.merge(instructor);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete instructor, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public List<Instructor> getAllInstructor() throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT i FROM Instructor i");
			List<Instructor> instructors = query.getResultList();

			if (instructors.isEmpty()) {
				System.out.println("No instructors found in the database.");
			}

			return instructors;
		} catch (PersistenceException e) {
			System.out.println("Failed to get instructors, please try again");
		} finally {
			em.close();
		}
		return null;
	}

}
