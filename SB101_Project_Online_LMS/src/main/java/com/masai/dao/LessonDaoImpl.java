package com.masai.dao;

import java.util.List;

import com.masai.entity.EMUtils;
import com.masai.entity.Lesson;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class LessonDaoImpl implements LessonDao {

	@Override
	public void createLesson(Lesson lesson) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if lesson with same name exists or not
			Query query = em.createQuery("Select Count(l) From Lesson l WHERE title = :title");
			query.setParameter("title", lesson.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same lesson title
				throw new SomethingWentWrongException("The lesson " + lesson.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(lesson);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add course, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateLesson(long lessonId,Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if lesson with given id exists
			Lesson updatedLesson = em.find(Lesson.class, lessonId);
			if (updatedLesson == null) {
				throw new NoRecordFoundException("No lesson found with given id : " + lesson.getLessonId());
			}

			// You are here means lesson exists with given id
			// check if lesson is to be renamed
			if (!updatedLesson.getTitle().equals(lesson.getTitle())) {
				// you are here means lesson is to be renamed, check for no existing lesson with
				// new name.
				// check if lesson with same name exists
				Query query = em.createQuery("SELECT count(l) FROM Lesson l WHERE title = :title");
				query.setParameter("title", lesson.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means company with given name exists so throw exceptions
					throw new SomethingWentWrongException("Lesson already exists with title : " + lesson.getTitle());
				}
			}
			em.getTransaction().begin();
			updatedLesson.setTitle(lesson.getTitle());
			updatedLesson.setContent(lesson.getContent());
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update lesson, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteLesson(long lessonId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if lesson with given id exists
			Lesson deletedLesson = em.find(Lesson.class, lessonId);
			if (deletedLesson == null) {
				throw new NoRecordFoundException("No lesson found with given id : " + lessonId);
			}

			em.getTransaction().begin();

			em.remove(deletedLesson);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete lesson, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public Lesson getLessonById(long lessonId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if lesson with given id exists
			Lesson lesson = em.find(Lesson.class, lessonId);
			if (lesson == null) {
				throw new NoRecordFoundException("No lesson found with given id : " + lessonId);
			}
			return lesson;
		} catch (PersistenceException e) {
			System.out.println("Failed to get lesson, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Lesson> getAllLessons() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT l FROM Lesson l");
			List<Lesson> lessons = query.getResultList();

			if (lessons.isEmpty()) {
				System.out.println("No lessons found in the database.");
			}
			return lessons;
		} catch (PersistenceException e) {
			System.out.println("Failed to get lessons, please try again");
		} finally {
			em.close();
		}
		return null;
	}
}
