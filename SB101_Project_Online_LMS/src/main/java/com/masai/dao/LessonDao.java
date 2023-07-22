package com.masai.dao;

import java.util.List;

import com.masai.entity.Lesson;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface LessonDao {

	void createLesson(Lesson lesson) throws SomethingWentWrongException;

	void updateLesson(long lessonId,Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteLesson(long lessonId) throws NoRecordFoundException;

	Lesson getLessonById(long lessonId) throws NoRecordFoundException;

	List<Lesson> getAllLessons();
}
