package com.masai.service;

import java.util.List;

import com.masai.dao.InstructorDao;
import com.masai.dao.InstructorDaoImpl;
import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public class InstructorServiceImpl implements InstructorService {

	public void registration(Instructor instructor) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.registerInstructor(instructor);

	}

	public void login(String username, String password) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.login(username, password);
	}

	@Override
	public Instructor getInstructorById(int instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getInstructorById(instructorId);
	}

	@Override
	public void updateInstructorDetails(Instructor instructor)
			throws NoRecordFoundException, SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateInstructorDetails(instructor);
		;
	}

	@Override
	public void deleteInstructorById(int instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteInstructorById(instructorId);
	}

	@Override
	public List<Instructor> getAllInstructor() throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllInstructor();
	}

	@Override
	public void createCourse(Course course) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.createCourse(course);
	}

	@Override
	public void updateCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateCourse(course);
	}

	@Override
	public void deleteCourse(int courseId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteCourse(courseId);
	}

	@Override
	public Course getCourseById(int courseId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getCourseById(courseId);
	}

	@Override
	public List<Course> getAllCourses() {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllCourses();
	}

	@Override
	public void createLesson(Lesson lesson) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.createLesson(lesson);
	}

	@Override
	public void updateLesson(Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateLesson(lesson);
	}

	@Override
	public void deleteLesson(int lessonId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteLesson(lessonId);
	}

	@Override
	public Lesson getLessonById(int lessonId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getLessonById(lessonId);
	}

	@Override
	public List<Lesson> getAllLessons() {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllLessons();
	}

	@Override
	public void createAssignment(Assignment assignment) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.createAssignment(assignment);
	}

	@Override
	public void updateAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateAssignment(assignment);
	}

	@Override
	public void deleteAssignment(int assignmentId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteAssignment(assignmentId);
	}

	@Override
	public Assignment getAssignmentById(int assignmentId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAssignmentById(assignmentId);
	}

	@Override
	public List<Assignment> getAllAssignments() {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllAssignments();
	}

	@Override
	public void createQuiz(Quiz quiz) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.createQuiz(quiz);
	}

	@Override
	public void updateQuiz(Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateQuiz(quiz);
	}

	@Override
	public void deleteQuiz(int quizId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteQuiz(quizId);
	}

	@Override
	public Quiz getQuizById(int quizId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getQuizById(quizId);
	}

	@Override
	public List<Quiz> getAllQuizzes() {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllQuizzes();
	}

	@Override
	public void createAssessment(Assessment assessment) throws SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.createAssessment(assessment);
	}

	@Override
	public void updateAssessment(Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateAssessment(assessment);
	}

	@Override
	public void deleteAssessment(int assessmentId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteAssessment(assessmentId);
	}

	@Override
	public Assessment getAssessmentById(int assessmentId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAssessmentById(assessmentId);
	}

	@Override
	public List<Assessment> getAllAssessments() {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllAssessments();
	}

}
