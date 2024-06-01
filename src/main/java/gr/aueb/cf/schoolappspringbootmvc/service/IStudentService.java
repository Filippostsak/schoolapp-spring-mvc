package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.AddStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomNotFoundException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;

import java.util.List;

/**
 * Service interface for managing students.
 */
public interface IStudentService {

    Student registerStudent(RegisterStudentDTO dto) throws StudentAlreadyExistsException;

    List<Student> findAllStudents() throws Exception;

    List<Student> findByLastnameContainingOrderByLastname(String lastname);

    List<Student> searchStudentsByLastname(String lastname);

    SearchStudentToClassroomDTO getStudentClassrooms(Long studentId) throws ClassroomNotFoundException;
}
