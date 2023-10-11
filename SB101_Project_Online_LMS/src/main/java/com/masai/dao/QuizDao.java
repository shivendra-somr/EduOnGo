package com.masai.dao;

import java.util.List;

import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface QuizDao {

    /**
     * Creates a new quiz record in the data store.
     * 
     * @param quiz The Quiz object representing the quiz to be created.
     * @throws SomethingWentWrongException if an unexpected error occurs while creating the quiz.
     */
    void createQuiz(Quiz quiz) throws SomethingWentWrongException;

    /**
     * Updates an existing quiz record in the data store with the provided quiz data.
     * 
     * @param quizId The unique identifier of the quiz to be updated.
     * @param quiz The Quiz object containing the updated quiz data.
     * @throws NoRecordFoundException if no quiz record is found with the provided quizId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the quiz.
     */
    void updateQuiz(long quizId, Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes a quiz record from the data store.
     * 
     * @param quizId The unique identifier of the quiz to be deleted.
     * @throws NoRecordFoundException if no quiz record is found with the provided quizId.
     */
    void deleteQuiz(long quizId) throws NoRecordFoundException;

    /**
     * Retrieves a quiz's information by its unique identifier (quizId) from the data store.
     * 
     * @param quizId The unique identifier of the quiz.
     * @return A Quiz object containing the quiz's information.
     * @throws NoRecordFoundException if no quiz record is found with the provided quizId.
     */
    Quiz getQuizById(long quizId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all quizzes available in the data store.
     * 
     * @return List of Quiz objects representing all available quizzes.
     */
    List<Quiz> getAllQuizzes();
}
