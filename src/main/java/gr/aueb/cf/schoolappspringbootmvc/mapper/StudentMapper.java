package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.StudentGetCurrentStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

/**
 * Mapper class for converting between Student-related DTOs and entities.
 */

@Component
public class StudentMapper {

    /**
     * Converts a Student entity to a SearchStudentDTO.
     *
     * @param student the Student entity
     * @return the SearchStudentDTO
     */

    // Maps Student entity to SearchStudentDTO
    public static SearchStudentDTO toSearchStudentDTO(Student student) {
        return new SearchStudentDTO(
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                student.getClassrooms().stream()
                        .map(Classroom::getId)
                        .collect(Collectors.toList()),
                student.getUser().getEmail(),
                student.getUser().getCountry()
        );
    }

    /**
     * Converts a Student entity to a SearchStudentToClassroomDTO.
     *
     * @param student the Student entity
     * @return the SearchStudentToClassroomDTO
     */

    // Maps Student entity to SearchStudentToClassroomDTO
    public SearchStudentToClassroomDTO toSearchStudentToClassroomDTO(Student student) {
        return new SearchStudentToClassroomDTO(
                student.getId(),
                student.getFirstname(),
                student.getLastname(),
                student.getClassrooms().stream()
                        .map(this::toClassroomDTO)
                        .collect(Collectors.toList())
        );
    }

    /**
     * Converts a Classroom entity to a ClassroomDTO.
     *
     * @param classroom the Classroom entity
     * @return the ClassroomDTO
     */

    // Helper method to map Classroom entity to ClassroomDTO
    private SearchStudentToClassroomDTO.ClassroomDTO toClassroomDTO(Classroom classroom) {
        return new SearchStudentToClassroomDTO.ClassroomDTO(
                classroom.getId(),
                classroom.getName()
        );
    }

    /**
     * Converts a Student entity to a StudentGetCurrentStudentDTO.
     *
     * @param student the Student entity
     * @return the StudentGetCurrentStudentDTO
     */

    // Map student dto to user entity to get the user details

    public StudentGetCurrentStudentDTO toGetCurrentStudentDTO(Student student) {
        return new StudentGetCurrentStudentDTO(
                student.getId(),
                student.getUser().getUsername()
        );
    }

}
