package com.masai.dao;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.entity.EMUtils;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class AssessmentDaoImpl implements AssessmentDao{

	@Override
	public void createAssessment(Assessment assessment) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if assessment with same name exists or not
			Query query = em.createQuery("Select Count(a) From Assessment a WHERE title = :title");
			query.setParameter("title", assessment.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same assessment title
				throw new SomethingWentWrongException("The assessment " + assessment.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(assessment);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateAssessment(long assessmentId,Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment updatedAssessment = em.find(Assessment.class, assessmentId);
			if (updatedAssessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessmentId);
			}

			// You are here means assessment exists with given id
			// check if assessment is to be renamed
			if (!updatedAssessment.getTitle().equals(assessment.getTitle())) {
				// you are here means assessment is to be renamed, check for no existing
				// assessment
				// with new name.
				// check if assessment with same name exists
				Query query = em.createQuery("SELECT count(a) FROM Assessment a WHERE title = :title");
				query.setParameter("title", assessment.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means assessment with given name exists so throw exceptions
					throw new SomethingWentWrongException(
							"Assessment already exists with title : " + assessment.getTitle());
				}
			}
			em.getTransaction().begin();
			updatedAssessment.setCourse(assessment.getCourse());
			updatedAssessment.setDescription(assessment.getDescription());
			updatedAssessment.setTitle(assessment.getTitle());
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAssessment(long assessmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment deletedAssessment = em.find(Assessment.class, assessmentId);
			if (deletedAssessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessmentId);
			}

			em.getTransaction().begin();

			em.remove(deletedAssessment);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public Assessment getAssessmentById(long assessmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment assessment = em.find(Assessment.class, assessmentId);
			if (assessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessmentId);
			}

			return assessment;

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to get assessment, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Assessment> getAllAssessments() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT a FROM Assessment a");
			List<Assessment> assessments = query.getResultList();

			if (assessments.isEmpty()) {
				System.out.println("No assessments found in the database.");
			}

			return assessments;
		} catch (PersistenceException e) {
			System.out.println("Failed to get assessments, please try again");
		} finally {
			em.close();
		}
		return null;
	}
}
