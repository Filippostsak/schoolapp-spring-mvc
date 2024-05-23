package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IClassroomService {
    Classroom createClassroom(CreateClassroomDTO classroomDTO);

    Teacher getCreatorTeacher(Long classroomId);

    List<Student> getStudentsInClassroom(Long classroomId);

    List<Classroom> findClassroomsByTeacher(Long teacherId);

    List<Classroom> findAllClassrooms();

    Classroom updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO);

    void deleteClassroom(Long classroomId);

    boolean classroomNameExists(String name);

    void addTeacherToClassroom(Long classroomId, String teacherUsername);

    Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable);
}