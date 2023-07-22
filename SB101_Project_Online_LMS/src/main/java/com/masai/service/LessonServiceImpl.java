package com.masai.service;

import java.util.List;

import com.masai.dao.LessonDao;
import com.masai.dao.LessonDaoImpl;
import com.masai.entity.Lesson;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class LessonServiceImpl implements LessonService {

	@Override
	public void createLesson(Lesson lesson) throws SomethingWentWrongException {
		LessonDao lessonDao = new LessonDaoImpl();
		lessonDao.createLesson(lesson);
		
	}

	@Override
	public void updateLesson(long lessonId,Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException {
		LessonDao lessonDao = new LessonDaoImpl();
		lessonDao.updateLesson(lessonId,lesson);
	}

	@Override
	public void deleteLesson(long lessonId) throws NoRecordFoundException {
		LessonDao lessonDao = new LessonDaoImpl();
		lessonDao.deleteLesson(lessonId);
	}

	@Override
	public Lesson getLessonById(long lessonId) throws NoRecordFoundException {
		LessonDao lessonDao = new LessonDaoImpl();
		return lessonDao.getLessonById(lessonId);
	}

	@Override
	public List<Lesson> getAllLessons() {
		LessonDao lessonDao = new LessonDaoImpl();
		return lessonDao.getAllLessons();
	}

}
