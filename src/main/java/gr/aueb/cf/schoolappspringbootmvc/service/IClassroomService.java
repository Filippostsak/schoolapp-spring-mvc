package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.MeetingUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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

    Optional<Classroom> findById(Long classroomId) throws Exception;

    void addTeacherToClassroom(AddTeacherToClassroomDTO dto) throws Exception;

    void save(Classroom classroom) throws ClassroomAlreadyExistsException;

    Classroom updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO);

    void removeStudentFromClassroom(Long classroomId, Long studentId);

    @Transactional
    MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, MeetingUpdateDTO meetingUpdateDTO);
}
