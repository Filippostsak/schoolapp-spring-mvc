package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;

import java.util.List;

/**
 * Service interface for managing students.
 */
public interface IStudentService {

    /**
     * Registers a new student based on the provided data transfer object.
     *
     * @param dto the data transfer object containing the information needed to register a student.
     * @return the registered student.
     * @throws StudentAlreadyExistsException if a student with the same username already exists.
     */
    Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException;

    /**
     * Retrieves all students.
     *
     * @return a list of all students.
     * @throws Exception if there is an error retrieving the students.
     */
    List<Student> findAllStudents() throws Exception;
}
