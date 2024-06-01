package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.GetTeachersIdDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;

import java.util.List;
import java.util.Optional;

public interface ITeacherService {

    /**
     * Registers a new teacher.
     *
     * @param dto the data transfer object containing teacher registration information.
     * @return the registered teacher.
     * @throws TeacherAlreadyExistsException if a teacher with the specified username already exists.
     */
    Teacher registerTeacher(RegisterTeacherDTO dto) throws TeacherAlreadyExistsException;

    /**
     * Retrieves all teachers.
     *
     * @return a list of all teachers.
     * @throws Exception if an error occurs while retrieving the teachers.
     */
    List<Teacher> findAllTeachers() throws Exception;

    /**
     * Searches for students by their last name.
     *
     * @param lastName the last name to search for.
     * @return a list of students with matching last names.
     */
    List<Student> searchStudentsByLastName(String lastName);

    /**
     * Finds a teacher by their first name.
     *
     * @param firstname the first name to search for.
     * @return the teacher with the matching first name.
     */
    Teacher findTeacherByFirstname(String firstname);

    /**
     * Retrieves the currently authenticated teacher.
     *
     * @return an Optional containing the current teacher if found, otherwise empty.
     */
    Optional<Teacher> getCurrentAuthenticatedTeacher();

    /**
     * Retrieves a teacher by their username.
     *
     * @param username the username to search for.
     * @return an Optional containing the teacher with the matching username if found, otherwise empty.
     */

    Optional<Teacher> findByUsername(String username);

    /**
     * Retrieves a teacher by their username containing the specified string.
     *
     * @param username the string to search for in the username.
     * @return an Optional containing the teacher with the matching username if found, otherwise empty.
     */

    List<Teacher> findByUsernameContaining(String username) throws Exception;

    Optional<GetTeachersIdDTO> findById(Long id) throws Exception;
}
