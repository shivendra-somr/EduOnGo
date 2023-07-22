package com.masai.service;

import java.util.List;

import com.masai.dao.QuizDao;
import com.masai.dao.QuizDaoImpl;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class QuizServiceImpl implements QuizService {

	@Override
	public void createQuiz(Quiz quiz) throws SomethingWentWrongException {
		QuizDao quizDao = new QuizDaoImpl();
		quizDao.createQuiz(quiz);
		
	}

	@Override
	public void updateQuiz(long quizId,Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException {
		QuizDao quizDao = new QuizDaoImpl();
		quizDao.updateQuiz(quizId,quiz);
	}

	@Override
	public void deleteQuiz(long quizId) throws NoRecordFoundException {
		QuizDao quizDao = new QuizDaoImpl();
		quizDao.deleteQuiz(quizId);
	}

	@Override
	public Quiz getQuizById(long quizId) throws NoRecordFoundException {
		QuizDao quizDao = new QuizDaoImpl();
		return quizDao.getQuizById(quizId);
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		QuizDao quizDao = new QuizDaoImpl();
		return quizDao.getAllQuizzes();
	}

}
