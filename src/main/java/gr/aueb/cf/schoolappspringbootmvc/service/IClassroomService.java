package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.*;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.*;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
 * Service for managing classrooms.
 */

public interface IClassroomService {
    Classroom createClassroom(CreateClassroomDTO classroomDTO);

    /**
     * Retrieves the teacher who created the classroom.
     *
     * @param classroomId the id of the classroom.
     * @return the teacher who created the classroom.
     */

    Teacher getCreatorTeacher(Long classroomId);

    /**
     * Retrieves the students in the classroom.
     *
     * @param classroomId the id of the classroom.
     * @return a list of students in the classroom.
     */

    List<Student> getStudentsInClassroom(Long classroomId);

    /**
     * Retrieves the teacher of the classroom.
     *
     * @param teacherId the id of the classroom.
     * @return the teacher of the classroom.
     */

    List<Classroom> findClassroomsByTeacher(Long teacherId);

    /**
     * Retrieves all classrooms.
     *
     * @return a list of all classrooms.
     */

    List<Classroom> findAllClassrooms();

    /**
     * Retrieves a classroom by its id.
     *
     * @param classroomId the id of the classroom.
     * @return the classroom with the specified id.
     */

    Classroom updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO);

    /**
     * Deletes a classroom.
     *
     * @param classroomId the id of the classroom to delete.
     */

    void deleteClassroom(Long classroomId);

    /**
     * Checks if a classroom with the specified name exists.
     *
     * @param name the name of the classroom.
     * @return true if a classroom with the specified name exists, false otherwise.
     */

    boolean classroomNameExists(String name);

    /**
     * Adds a teacher to a classroom.
     *
     * @param classroomId the id of the classroom.
     * @param teacherUsername the username of the teacher to add.
     */

    void addTeacherToClassroom(Long classroomId, String teacherUsername);

    /**
     * Removes a teacher from a classroom.
     *
     * @param teacherId the id of the classroom.
     * @param pageable the username of the teacher to remove.
     */

    Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable);

    /**
     * Retrieves a classroom by its id.
     *
     * @param classroomId the id of the classroom.
     * @return the classroom with the specified id.
     */

    Optional<Classroom> findById(Long classroomId) throws Exception;

    /**
     * Retrieves a classroom by its name.
     *
     * @param dto the name of the classroom.
     */

    void addTeacherToClassroom(AddTeacherToClassroomDTO dto) throws Exception;

    /**
     * Retrieves a classroom by its name.
     *
     * @param classroom the name of the classroom.
     */

    void save(Classroom classroom) throws ClassroomAlreadyExistsException;

    /**
     * Retrieves a classroom by its name.
     *
     * @param classroomId the name of the classroom.
     * @return the classroom with the specified name.
     */

    Classroom updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO);

    /**
     * Retrieves a classroom by its name.
     *
     * @param classroomId the name of the classroom.
     * @return the classroom with the specified name.
     */

    MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO);

    /**
     * Retrieves a classroom by its name.
     * @return the classroom with the specified name.
     */

    List<ClassroomFindMeetingsDTO> getAllClassroomsAndMeetingsForCurrentTeacher();

    /**
     * Retrieves a classroom by its name.
     *
     * @param dto the name of the classroom.
     */

    void removeStudentFromClassroom(RemoveStudentDTO dto);

    /**
     * Retrieves a classroom by its name.
     *
     * @param classroomId the name of the classroom.
     * @return the classroom with the specified name.
     */

    boolean isStudentInClassroom(Long classroomId, Long studentId);

    /**
     * Retrieves a classroom by its name.
     *
     * @param classroomId the name of the classroom.
     */

    void addStudentToClassroom(Long classroomId, Long studentId) throws RuntimeException;

    /**
     * Retrieves a classroom by its name.
     *
     * @param id the name of the classroom.
     * @return the classroom with the specified name.
     */

    ClassroomReadOnlyDTO getByClassroomId(Long id) throws Exception;

    /**
     * Retrieves a classroom by its name.
     *
     * @param studentId the name of the classroom.
     * @return the classroom with the specified name.
     */
    List<ClassroomStudentsClassroomDTO> findClassroomsByStudentId(Long studentId);

    /**
     * Retrieves a classroom by its name.
     *
     * @param teacherId the name of the classroom.
     * @return the classroom with the specified name.
     */
    List<Classroom> getAllClassroomsByTeacherId(Long teacherId);
}
