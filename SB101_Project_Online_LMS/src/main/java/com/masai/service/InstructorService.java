package com.masai.service;

import com.masai.entity.Instructor;
import com.masai.exception.SomethingWentWrongException;

public interface InstructorService {
	void registration(Instructor instructor) throws SomethingWentWrongException;
	void login(String username, String password) throws SomethingWentWrongException;
}
