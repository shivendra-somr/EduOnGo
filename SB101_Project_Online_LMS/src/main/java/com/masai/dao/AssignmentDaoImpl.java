package com.masai.dao;

import java.util.List;

import com.masai.entity.Assignment;
import com.masai.entity.EMUtils;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AssignmentDaoImpl implements AssignmentDao {

	@Override
	public void createAssignment(Assignment assignment) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if assignment with same name exists or not
			Query query = em.createQuery("Select Count(s) From Assignment a WHERE title = :title");
			query.setParameter("title", assignment.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same assignment title
				throw new SomethingWentWrongException("The assignment " + assignment.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(assignment);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateAssignment(long assignmentId,Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment updatedAssignment = em.find(Assignment.class,assignmentId);
			if (updatedAssignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignmentId);
			}

			// You are here means assignment exists with given id
			// check if assignment is to be renamed
			if (!updatedAssignment.getTitle().equals(assignment.getTitle())) {
				// you are here means assignment is to be renamed, check for no existing
				// assignment with
				// new name.
				// check if assignment with same name exists
				Query query = em.createQuery("SELECT count(a) FROM Assignment WHERE title = :title");
				query.setParameter("title", assignment.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means assignment with given name exists so throw exceptions
					throw new SomethingWentWrongException(
							"Assignment already exists with title : " + assignment.getTitle());
				}
			}
			em.getTransaction().begin();

			updatedAssignment.setCourse(assignment.getCourse());
			updatedAssignment.setDescription(assignment.getDescription());
			updatedAssignment.setDueDate(assignment.getDueDate());
			updatedAssignment.setTitle(assignment.getTitle());

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAssignment(long assignmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment deletedAssignment = em.find(Assignment.class, assignmentId);
			if (deletedAssignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignmentId);
			}

			em.getTransaction().begin();

			em.remove(deletedAssignment);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public Assignment getAssignmentById(long assignmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment assignment = em.find(Assignment.class, assignmentId);
			if (assignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignmentId);
			}
			return assignment;

		} catch (PersistenceException e) {
			System.out.println("Failed to get assignment, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Assignment> getAllAssignments() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT a FROM Assignment a");
			List<Assignment> assignments = query.getResultList();

			if (assignments.isEmpty()) {
				System.out.println("No assignments found in the database.");
			}

			return assignments;
		} catch (PersistenceException e) {
			System.out.println("Failed to get assignments, please try again");
		} finally {
			em.close();
		}
		return null;
	}
}
