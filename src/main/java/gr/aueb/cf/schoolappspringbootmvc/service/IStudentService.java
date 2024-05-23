package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;

import java.util.List;

/**
 * Service interface for managing students.
 */
public interface IStudentService {

    Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException;

    List<Student> findAllStudents() throws Exception;

    List<Student> findByLastnameContainingOrderByLastname(String lastname);

    List<Student> searchStudentsByLastname(String lastname);
}
