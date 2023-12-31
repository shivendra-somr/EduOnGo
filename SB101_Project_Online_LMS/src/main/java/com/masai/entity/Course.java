package com.masai.entity;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private long courseId;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String description;

	@Column(name = "is_deleted", nullable = false, columnDefinition = "boolean default false")
	private boolean isDeleted;

	@ManyToOne
	@JoinColumn(name = "instructor_id")
	private Instructor instructor;

	@Column(name = "course_duration")
	private int courseDuration;

	// Define course-lesson association
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Lesson> lessons;

	// Define course-assignment association
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Assignment> assignments;

	// Define course-quiz association
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Quiz> quizzes;

	// Define course-assessment association
	@OneToOne(mappedBy = "course", fetch = FetchType.EAGER)
	private Assessment assessment;

	// Define course-enrollment association
	@OneToMany(mappedBy = "course", fetch = FetchType.EAGER)
	private List<Enrollment> enrollments;

	public Course() {
		super();
	}

	

	public Course(String title, String description, int courseDuration) {
		super();
		this.title = title;
		this.description = description;
		this.courseDuration = courseDuration;
	}



	public long getCourseId() {
		return courseId;
	}

	public void setCourseId(long courseId) {
		this.courseId = courseId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Instructor getInstructor() {
		return instructor;
	}

	public void setInstructor(Instructor instructor) {
		this.instructor = instructor;
	}

	public int getCourseDuration() {
		return courseDuration;
	}

	public void setCourseDuration(int courseDuration) {
		this.courseDuration = courseDuration;
	}

	public boolean isDeleted() {
		return isDeleted;
	}

	public void setDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public List<Lesson> getLessons() {
		return lessons;
	}

	public void setLessons(List<Lesson> lessons) {
		this.lessons = lessons;
	}

	public List<Assignment> getAssignments() {
		return assignments;
	}

	public void setAssignments(List<Assignment> assignments) {
		this.assignments = assignments;
	}

	public List<Quiz> getQuizzes() {
		return quizzes;
	}

	public void setQuizzes(List<Quiz> quizzes) {
		this.quizzes = quizzes;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}

	public List<Enrollment> getEnrollments() {
		return enrollments;
	}

	public void setEnrollments(List<Enrollment> enrollments) {
		this.enrollments = enrollments;
	}



	@Override
	public String toString() {
		return String.format("Course Id : %s, title : %s, description : %s, courseDuration : %s", courseId, title,
				description, courseDuration);
	}

}
