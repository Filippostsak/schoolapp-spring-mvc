package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;

import java.util.List;

/**
 * Service interface for managing teachers.
 */
public interface ITeacherService {

    /**
     * Registers a new teacher based on the provided data transfer object.
     *
     * @param dto the data transfer object containing the information needed to register a teacher.
     * @return the registered teacher.
     * @throws TeacherAlreadyExistsException if a teacher with the same username already exists.
     */
    Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException;

    /**
     * Retrieves all teachers.
     *
     * @return a list of all teachers.
     * @throws Exception if there is an error retrieving the teachers.
     */
    List<Teacher> findAllTeachers() throws Exception;
}
