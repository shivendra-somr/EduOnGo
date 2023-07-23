package com.masai.service;

import java.util.List;

import com.masai.entity.Assessment;
import com.masai.entity.Assignment;
import com.masai.entity.Course;
import com.masai.entity.Instructor;
import com.masai.entity.Lesson;
import com.masai.entity.Quiz;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface InstructorService {
	void registration(Instructor instructor) throws SomethingWentWrongException;

	void login(String username, String password) throws SomethingWentWrongException;

	Instructor getInstructorById(long instructorId) throws NoRecordFoundException;

	void updateInstructorDetails(long instructorId,Instructor instructor) throws NoRecordFoundException, SomethingWentWrongException;

	void deleteInstructorById(long instructorId) throws NoRecordFoundException;

	List<Instructor> getAllInstructor() throws NoRecordFoundException;

	void changePassword(long loggedInUserId, String currentPassword, String newPassword) throws NoRecordFoundException, SomethingWentWrongException;

}
