package com.masai.dao;

import java.util.List;

import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface QuizDao {

	void createQuiz(Quiz quiz) throws SomethingWentWrongException;

	void updateQuiz(long quizId,Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException;

	void deleteQuiz(long quizId) throws NoRecordFoundException;

	Quiz getQuizById(long quizId) throws NoRecordFoundException;

	List<Quiz> getAllQuizzes();
}
