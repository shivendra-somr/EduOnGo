package com.masai.dao;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface InstructorDao {

	void registerInstructor(Instructor instructor) throws SomethingWentWrongException;

	void login(String username, String password) throws SomethingWentWrongException;
	
	Instructor getInstructorById(int instructorId) throws NoRecordFoundException;
	
	void updateInstructorDetails(Instructor instructor) throws NoRecordFoundException, SomethingWentWrongException;
	
	void deleteInstructorById(int instructorId) throws NoRecordFoundException;
	
	List<Instructor> getAllInstructor() throws NoRecordFoundException;

	void createCourse(Course course) throws SomethingWentWrongException;

	void updateCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteCourse(int courseId) throws NoRecordFoundException;

	Course getCourseById(int courseId) throws NoRecordFoundException;

	List<Course> getAllCourses();

	void createLesson(Lesson lesson) throws SomethingWentWrongException;

	void updateLesson(Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteLesson(int lessonId) throws NoRecordFoundException;

	Lesson getLessonById(int lessonId) throws NoRecordFoundException;

	List<Lesson> getAllLessons();

	void createAssignment(Assignment assignment) throws SomethingWentWrongException;

	void updateAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException;

	void deleteAssignment(int assignmentId) throws NoRecordFoundException;

	Assignment getAssignmentById(int assignmentId) throws NoRecordFoundException;

	List<Assignment> getAllAssignments();

	void createQuiz(Quiz quiz) throws SomethingWentWrongException;

	void updateQuiz(Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException;

	void deleteQuiz(int quizId) throws NoRecordFoundException;

	Quiz getQuizById(int quizId) throws NoRecordFoundException;

	List<Quiz> getAllQuizzes();

	void createAssessment(Assessment assessment) throws SomethingWentWrongException;

	void updateAssessment(Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException;

	void deleteAssessment(int assessmentId) throws NoRecordFoundException;

	Assessment getAssessmentById(int assessmentId) throws NoRecordFoundException;

	List<Assessment> getAllAssessments();
}
