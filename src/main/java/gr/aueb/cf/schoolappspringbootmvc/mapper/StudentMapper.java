package gr.aueb.cf.schoolappspringbootmvc.mapper;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.SearchStudentToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class StudentMapper {

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

    // Helper method to map Classroom entity to ClassroomDTO
    private SearchStudentToClassroomDTO.ClassroomDTO toClassroomDTO(Classroom classroom) {
        return new SearchStudentToClassroomDTO.ClassroomDTO(
                classroom.getId(),
                classroom.getName()
        );
    }
}
