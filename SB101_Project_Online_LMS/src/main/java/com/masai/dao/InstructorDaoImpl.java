package com.masai.dao;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.EMUtils;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.LoggedInUserId;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.Query;

public class InstructorDaoImpl implements InstructorDao {

	public void registerInstructor(Instructor instructor) throws SomethingWentWrongException {

		try (EntityManager em = EMUtils.getEntityManager()) {
			// check if instructor with same username exists or not
			Query query = em.createQuery("Select Count(i) From Instructor i WHERE username = :username");
			query.setParameter("username", instructor.getUsername());
			if ((long) query.getSingleResult() > 0) {
				// throwing error msg for same username
				throw new SomethingWentWrongException("The username " + instructor.getUsername() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(instructor);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			System.out.println("Failed to register instructor, please try again");
		} 

	}

	@Override
	public void login(String username, String password) throws SomethingWentWrongException {

		try (EntityManager em = EMUtils.getEntityManager()){
			// check if instructor with same username exists or not
			Query query = em.createQuery(
					"Select i From Instructor i WHERE i.username = :username AND i.password =:password AND i.isDeleted = false");
			query.setParameter("username", username);
			query.setParameter("password", password);
			List<Instructor> instructors = query.getResultList();
			if (instructors.isEmpty()) {
				// you are here means instructor with given name does not exists so throw
				// exceptions
				throw new SomethingWentWrongException("The username or password is incorrect");
			}
			LoggedInUserId.loggedInUserId = instructors.get(0).getInstructorId();
		} catch (PersistenceException e) {
			System.out.println("Failed to login instructor, please try again ");
		} 
	}

	@Override
	public Instructor getInstructorById(int instructorId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor exists or not
			Instructor instructor = em.find(Instructor.class, instructorId);
			
			if(instructor == null) {
				throw new NoRecordFoundException("No instructor found with given id : " +instructorId);
			}
			return instructor;
		} catch (PersistenceException e) {
			System.out.println("Failed to register instructor, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public void updateInstructorDetails(Instructor instructor) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor with given id exists
			Instructor updatedInstructor = em.find(Instructor.class, instructor.getInstructorId());
			if (updatedInstructor == null) {
				throw new NoRecordFoundException("No instructor found with given id :" + instructor.getInstructorId());
			}

			// You are here means instructor exists with given id
			// check if instructor is to be renamed
			if (!updatedInstructor.getUsername().equals(instructor.getUsername())) {
				// you are here means instructor is to be renamed, check for no existing instructor with
				// new name.
				// check if instructor with same name exists
				Query query = em.createQuery("SELECT i FROM Instructor i WHERE username = :username");
				query.setParameter("title", instructor.getUsername());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means instructor with given name exists so throw exceptions
					throw new SomethingWentWrongException("Instructor already exists with title " + instructor.getUsername());
				}
			}
			// proceed for update operation
			em.getTransaction().begin();
			updatedInstructor.setUsername(instructor.getUsername());
			updatedInstructor.setPassword(instructor.getPassword());
			updatedInstructor.setContact(instructor.getContact());
			updatedInstructor.setDob(instructor.getDob());
			updatedInstructor.setEmail(instructor.getEmail());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update instructor, please try again");
		} finally {
			em.close();
		}
		
	}

	@Override
	public void deleteInstructorById(int instructorId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if instructor exists or not
			Instructor instructor = em.find(Instructor.class, instructorId);
			
			if(instructor == null) {
				throw new NoRecordFoundException("No instructor found with given id : " +instructorId);
			}
			em.getTransaction().begin();
			em.remove(instructor);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete instructor, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public List<Instructor> getAllInstructor() throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT i FROM Instructor i");
			List<Instructor> instructors = query.getResultList();

			if (instructors.isEmpty()) {
				System.out.println("No instructors found in the database.");
			}

			return instructors;
		} catch (PersistenceException e) {
			System.out.println("Failed to get instructors, please try again");
		} finally {
			em.close();
		}
		return null;
	}
	
	@Override
	public void createCourse(Course course) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if course with same name exists or not
			Query query = em.createQuery("Select Count(c) From Course c WHERE title = :title");
			query.setParameter("title", course.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same course title
				throw new SomethingWentWrongException("The course " + course.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(course);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add course, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateCourse(Course course) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course updatedCourse = em.find(Course.class, course.getCourseId());
			if (updatedCourse == null) {
				throw new NoRecordFoundException("No course found with given id :" + course.getCourseId());
			}

			// You are here means course exists with given id
			// check if course is to be renamed
			if (!updatedCourse.getTitle().equals(course.getTitle())) {
				// you are here means course is to be renamed, check for no existing course with
				// new name.
				// check if course with same name exists
				Query query = em.createQuery("SELECT count(c) FROM Course c WHERE title = :title");
				query.setParameter("title", course.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means company with given name exists so throw exceptions
					throw new SomethingWentWrongException("Course already exists with title " + course.getTitle());
				}
			}
			// proceed for update operation
			em.getTransaction().begin();
			updatedCourse.setTitle(course.getTitle());
			updatedCourse.setDescription(course.getDescription());
			updatedCourse.setCourseDuration(course.getCourseDuration());
			updatedCourse.setAssignments(course.getAssignments());
			updatedCourse.setAssessment(course.getAssessment());
			updatedCourse.setInstructor(course.getInstructor());
			updatedCourse.setLessons(course.getLessons());
			updatedCourse.setQuizzes(course.getQuizzes());
			updatedCourse.setEnrollments(course.getEnrollments());
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update course, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteCourse(int courseId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course deletedCourse = em.find(Course.class, courseId);
			if (deletedCourse == null) {
				throw new NoRecordFoundException("No course found with given id : " + deletedCourse);
			}

			em.getTransaction().begin();

			em.remove(deletedCourse);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete course, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public Course getCourseById(int courseId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if course with given id exists
			Course course = em.find(Course.class, courseId);
			if (course == null) {
				throw new NoRecordFoundException("No course found with given id : " + courseId);
			}
			return course;

		} catch (PersistenceException e) {
			System.out.println("Failed to get course, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Course> getAllCourses() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT c FROM Course c");
			List<Course> courses = query.getResultList();

			if (courses.isEmpty()) {
				System.out.println("No courses found in the database.");
			}

			return courses;
		} catch (PersistenceException e) {
			System.out.println("Failed to get course, please try again");
		} finally {
			em.close();
		}
		return null;
	}

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
	public void updateLesson(Lesson lesson) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if lesson with given id exists
			Lesson updatedLesson = em.find(Lesson.class, lesson.getLessonId());
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
			updatedLesson.setCourse(lesson.getCourse());
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update lesson, please try again");
		} finally {
			em.close();
		}

	}

	@Override
	public void deleteLesson(int lessonId) throws NoRecordFoundException {
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
	public Lesson getLessonById(int lessonId) throws NoRecordFoundException {
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

	@Override
	public void createAssignment(Assignment assignment) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if assignment with same name exists or not
			Query query = em.createQuery("Select Count(s) From Assignment a WHERE title = :title");
			query.setParameter("title", assignment.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same assignment title
				throw new SomethingWentWrongException("The assignment " + assignment.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(assignment);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateAssignment(Assignment assignment) throws SomethingWentWrongException, NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment updatedAssignment = em.find(Assignment.class, assignment.getAssignmentId());
			if (updatedAssignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignment.getAssignmentId());
			}

			// You are here means assignment exists with given id
			// check if assignment is to be renamed
			if (!updatedAssignment.getTitle().equals(assignment.getTitle())) {
				// you are here means assignment is to be renamed, check for no existing
				// assignment with
				// new name.
				// check if assignment with same name exists
				Query query = em.createQuery("SELECT count(a) FROM Assignment WHERE title = :title");
				query.setParameter("title", assignment.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means assignment with given name exists so throw exceptions
					throw new SomethingWentWrongException(
							"Assignment already exists with title : " + assignment.getTitle());
				}
			}
			em.getTransaction().begin();

			updatedAssignment.setCourse(assignment.getCourse());
			updatedAssignment.setDescription(assignment.getDescription());
			updatedAssignment.setDueDate(assignment.getDueDate());
			updatedAssignment.setTitle(assignment.getTitle());

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAssignment(int assignmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment deletedAssignment = em.find(Assignment.class, assignmentId);
			if (deletedAssignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignmentId);
			}

			em.getTransaction().begin();

			em.remove(deletedAssignment);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete assignment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public Assignment getAssignmentById(int assignmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assignment with given id exists
			Assignment assignment = em.find(Assignment.class, assignmentId);
			if (assignment == null) {
				throw new NoRecordFoundException("No assignment found with given id : " + assignmentId);
			}
			return assignment;

		} catch (PersistenceException e) {
			System.out.println("Failed to get assignment, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Assignment> getAllAssignments() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT a FROM Assignment a");
			List<Assignment> assignments = query.getResultList();

			if (assignments.isEmpty()) {
				System.out.println("No assignments found in the database.");
			}

			return assignments;
		} catch (PersistenceException e) {
			System.out.println("Failed to get assignments, please try again");
		} finally {
			em.close();
		}
		return null;
	}

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
	public void updateQuiz(Quiz quiz) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if quiz with given id exists
			Quiz updatedQuiz = em.find(Quiz.class, quiz.getQuizId());
			if (updatedQuiz == null) {
				throw new NoRecordFoundException("No quiz found with given id : " + quiz.getQuizId());
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
			updatedQuiz.setCourse(quiz.getCourse());
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
	public void deleteQuiz(int quizId) throws NoRecordFoundException {
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
	public Quiz getQuizById(int quizId) throws NoRecordFoundException {
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

	@Override
	public void createAssessment(Assessment assessment) throws SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();

			// check if assessment with same name exists or not
			Query query = em.createQuery("Select Count(a) From Assessment a WHERE title = :title");
			query.setParameter("title", assessment.getTitle());
			if ((long) query.getSingleResult() > 0) {
				// throwing error message for same assessment title
				throw new SomethingWentWrongException("The assessment " + assessment.getTitle() + "already exist!");
			}

			em.getTransaction().begin();

			em.persist(assessment);

			em.getTransaction().commit();
		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to add assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void updateAssessment(Assessment assessment) throws NoRecordFoundException, SomethingWentWrongException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment updatedAssessment = em.find(Assessment.class, assessment.getAssessmentId());
			if (updatedAssessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessment.getAssessmentId());
			}

			// You are here means assessment exists with given id
			// check if assessment is to be renamed
			if (!updatedAssessment.getTitle().equals(assessment.getTitle())) {
				// you are here means assessment is to be renamed, check for no existing
				// assessment
				// with new name.
				// check if assessment with same name exists
				Query query = em.createQuery("SELECT count(a) FROM Assessment a WHERE title = :title");
				query.setParameter("title", assessment.getTitle());
				if ((Long) query.getSingleResult() > 0) {
					// you are here means assessment with given name exists so throw exceptions
					throw new SomethingWentWrongException(
							"Assessment already exists with title : " + assessment.getTitle());
				}
			}
			em.getTransaction().begin();
			updatedAssessment.setCourse(assessment.getCourse());
			updatedAssessment.setDescription(assessment.getDescription());
			updatedAssessment.setTitle(assessment.getTitle());
			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to update assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public void deleteAssessment(int assessmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment deletedAssessment = em.find(Assessment.class, assessmentId);
			if (deletedAssessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessmentId);
			}

			em.getTransaction().begin();

			em.remove(deletedAssessment);

			em.getTransaction().commit();

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to delete assessment, please try again");
		} finally {
			em.close();
		}
	}

	@Override
	public Assessment getAssessmentById(int assessmentId) throws NoRecordFoundException {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			// check if assessment with given id exists
			Assessment assessment = em.find(Assessment.class, assessmentId);
			if (assessment == null) {
				throw new NoRecordFoundException("No assessment found with given id : " + assessmentId);
			}

			return assessment;

		} catch (PersistenceException e) {
			em.getTransaction().rollback();
			System.out.println("Failed to get assessment, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	@Override
	public List<Assessment> getAllAssessments() {
		EntityManager em = null;

		try {
			em = EMUtils.getEntityManager();
			Query query = em.createQuery("SELECT a FROM Assessment a");
			List<Assessment> assessments = query.getResultList();

			if (assessments.isEmpty()) {
				System.out.println("No assessments found in the database.");
			}

			return assessments;
		} catch (PersistenceException e) {
			System.out.println("Failed to get assessments, please try again");
		} finally {
			em.close();
		}
		return null;
	}

	

}
