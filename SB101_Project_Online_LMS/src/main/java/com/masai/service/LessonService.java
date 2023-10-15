package com.masai.service;

import java.util.List;

import com.masai.entity.Lesson;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface LessonService {

    /**
     * Creates a new lesson in the system.
     * 
     * @param lesson The Lesson object representing the lesson to be created.
     * @throws SomethingWentWrongException if an unexpected error occurs while creating the lesson.
     */
    void createLesson(Lesson lesson) throws SomethingWentWrongException;

    /**
     * Updates an existing lesson with the provided lesson data.
     * 
     * @param lessonId The unique identifier of the lesson to be updated.
     * @param lesson The Lesson object containing the updated lesson data.
     * @throws NoRecordFoundException if no lesson record is found with the provided lessonId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the lesson.
     */
    void updateLesson(long lessonId, Lesson lesson) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes a lesson record from the system.
     * 
     * @param lessonId The unique identifier of the lesson to be deleted.
     * @throws NoRecordFoundException if no lesson record is found with the provided lessonId.
     */
    void deleteLesson(long lessonId) throws NoRecordFoundException;

    /**
     * Retrieves a lesson's information by its unique identifier (lessonId).
     * 
     * @param lessonId The unique identifier of the lesson.
     * @return A Lesson object containing the lesson's information.
     * @throws NoRecordFoundException if no lesson record is found with the provided lessonId.
     */
    Lesson getLessonById(long lessonId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all lessons available in the system.
     * 
     * @return List of Lesson objects representing all available lessons.
     */
    List<Lesson> getAllLessons();
}
