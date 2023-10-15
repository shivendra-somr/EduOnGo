package com.masai.dao;

import java.util.List;

import com.masai.entity.Course;
import com.masai.exception.NoRecordFoundException;
import com.masai.exception.SomethingWentWrongException;

public interface CourseDao {

    /**
     * Creates a new course record in the data store.
     * 
     * @param course The Course object representing the course to be created.
     * @throws SomethingWentWrongException if an unexpected error occurs while creating the course.
     */
    void createCourse(Course course) throws SomethingWentWrongException;

    /**
     * Updates an existing course record in the data store with the provided course data.
     * 
     * @param courseId The unique identifier of the course to be updated.
     * @param course The Course object containing the updated course data.
     * @throws NoRecordFoundException if no course record is found with the provided courseId.
     * @throws SomethingWentWrongException if an unexpected error occurs while updating the course.
     */
    void updateCourse(long courseId, Course course) throws NoRecordFoundException, SomethingWentWrongException;

    /**
     * Deletes a course record from the data store.
     * 
     * @param courseId The unique identifier of the course to be deleted.
     * @throws NoRecordFoundException if no course record is found with the provided courseId.
     */
    void deleteCourse(long courseId) throws NoRecordFoundException;

    /**
     * Retrieves a course's information by its unique identifier (courseId) from the data store.
     * 
     * @param courseId The unique identifier of the course.
     * @return A Course object containing the course's information.
     * @throws NoRecordFoundException if no course record is found with the provided courseId.
     */
    Course getCourseById(long courseId) throws NoRecordFoundException;

    /**
     * Retrieves a list of all courses available in the data store.
     * 
     * @return List of Course objects representing all available courses.
     */
    List<Course> getAllCourses();
}
