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
	public Instructor getInstructorById(long instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getInstructorById(instructorId);
	}

	@Override
	public void updateInstructorDetails(long instructorId,Instructor instructor)
			throws NoRecordFoundException, SomethingWentWrongException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.updateInstructorDetails(instructorId,instructor);
		;
	}

	@Override
	public void deleteInstructorById(long instructorId) throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		instructorDao.deleteInstructorById(instructorId);
	}

	@Override
	public List<Instructor> getAllInstructor() throws NoRecordFoundException {
		InstructorDao instructorDao = new InstructorDaoImpl();
		return instructorDao.getAllInstructor();
	}

	@Override
	public void changePassword(long loggedInUserId, String currentPassword, String newPassword) throws NoRecordFoundException, SomethingWentWrongException {
		// Retrieve the instructor from the database using the instructorId
		InstructorDao instructorDao = new InstructorDaoImpl();
        Instructor instructor = instructorDao.getInstructorById(loggedInUserId);

        // Check if the instructor exists
        if (instructor == null) {
            throw new NoRecordFoundException("Instructor with ID " + loggedInUserId + " not found.");
        }

        // Verify the current password against the one stored in the database
        if (!instructor.getPassword().equals(currentPassword)) {
            throw new SomethingWentWrongException("Incorrect current password. Please try again.");
        }

        // Update the instructor's password with the new one
        instructor.setPassword(newPassword);
        try {
			instructorDao.updateInstructorDetails(loggedInUserId,instructor);
		} catch (NoRecordFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SomethingWentWrongException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
