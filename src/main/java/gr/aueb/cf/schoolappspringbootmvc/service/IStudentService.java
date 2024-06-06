package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.*;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;

import java.util.List;

/**
 * Service interface for managing students.
 */
public interface IStudentService {

    /**
     * Registers a new student.
     *
     * @param dto the DTO containing the student's registration information
     * @return the registered student
     * @throws StudentAlreadyExistsException if a student with the same details already exists
     */
    Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException;

    /**
     * Retrieves all students.
     *
     * @return a list of all students
     * @throws Exception if an error occurs while fetching the students
     */
    List<Student> findAllStudents() throws Exception;

    /**
     * Searches for students whose last names contain the specified substring,
     * ordered by their last names.
     *
     * @param lastname the substring to search for in last names
     * @return a list of students whose last names contain the specified substring
     */
    List<Student> findByLastnameContainingOrderByLastname(String lastname);

    /**
     * Searches for students by their last names.
     *
     * @param lastname the last name to search for
     * @return a list of students with the specified last name
     */
    List<Student> searchStudentsByLastname(String lastname);

    /**
     * Retrieves the classrooms associated with a specific student.
     *
     * @param studentId the ID of the student
     * @return a DTO containing the student's classroom information
     * @throws ClassroomNotFoundException if the classrooms for the student are not found
     */
    SearchStudentToClassroomDTO getStudentClassrooms(Long studentId) throws ClassroomNotFoundException;

    /**
     * Retrieves the student with the specified ID.
     *
     * @param dto the ID of the student
     * @return the student with the specified ID
     * @throws StudentNotFoundException if the student is not found
     */
    StudentGetCurrentStudentDTO getCurrentAuthenticatedStudent(StudentGetCurrentStudentDTO dto) throws StudentNotFoundException;

    /**
     * Retrieves the student with the specified ID.
     *
     * @param username the ID of the student
     * @return the student with the specified ID
     * @throws StudentNotFoundException if the student is not found
     */
    Student getStudentIdByUsername(String username) throws StudentNotFoundException;

    /**
     * Retrieves the student with the specified classroom ID.
     *
     * @param classroomId the ID of the classroom
     * @return the student with the specified ID
     * @throws StudentNotFoundException if the student is not found
     */
    List<StudentGetUsernameAndIdDTO> findStudentUsernamesAndIdsByClassroomId(Long classroomId);



}
