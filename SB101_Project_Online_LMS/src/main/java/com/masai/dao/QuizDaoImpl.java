package com.masai.dao;

import java.util.List;

import com.masai.entity.EMUtils;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class QuizDaoImpl implements QuizDao {


	@Override
	public void createQuiz(Quiz quiz) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if quiz with same name exists or not
			Query query = em.createQuery("Select Count(q) From Quiz q WHERE title = :title");
			query.setParameter("title", quiz.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same quiz title
				throw new SomethingWentWrongException("The quiz " + quiz.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(quiz);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add quiz, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public void updateQuiz(long quizId, Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if quiz with given id exists
			Quiz updatedQuiz = em.find(Quiz.class, quizId);
			if (updatedQuiz == null) {
				throw new NoRecordFoundException("No quiz found with given id : " + quizId);
			}

			// You are here means quiz exists with given id
			// check if quiz is to be renamed
			if (!updatedQuiz.getTitle().equals(quiz.getTitle())) {
				// you are here means quiz is to be renamed, check for no existing quiz with new
				// name.
				// check if quiz with same name exists
				Query query = em.createQuery("SELECT count(q) FROM Quiz q WHERE title = :title");
				query.setParameter("title", quiz.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means quiz with given name exists so throw exceptions
					throw new SomethingWentWrongException("Quiz already exists with title : " + quiz.getTitle());
				}
			}
			em.getTransaction().begin();
			updatedQuiz.setDescription(quiz.getDescription());
			updatedQuiz.setTimeLimit(quiz.getTimeLimit());
			updatedQuiz.setTitle(quiz.getTitle());

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update quiz, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteQuiz(long quizId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if quiz with given id exists
			Quiz deletedQuiz = em.find(Quiz.class, quizId);
			if (deletedQuiz == null) {
				throw new NoRecordFoundException("No quiz found with given id : " + quizId);
			}

			em.getTransaction().begin();

			em.remove(deletedQuiz);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete quiz, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public Quiz getQuizById(long quizId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if quiz with given id exists
			Quiz quiz = em.find(Quiz.class, quizId);
			if (quiz == null) {
				throw new NoRecordFoundException("No quiz found with given id : " + quizId);
			}
			return quiz;

		} catch (PersistenceException e) {
			System.out.println("Failed to get quiz, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT q FROM Quiz q");
			List<Quiz> quizzes = query.getResultList();

			if (quizzes.isEmpty()) {
				System.out.println("No quizzes found in the database.");
			}

			return quizzes;
		} catch (PersistenceException e) {
			System.out.println("Failed to get quizzes, please try again");
		} finally {
			em.close();
		}
		return null;
	}
}
