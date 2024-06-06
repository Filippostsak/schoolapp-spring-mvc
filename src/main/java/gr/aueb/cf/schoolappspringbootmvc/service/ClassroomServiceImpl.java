package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.*;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.FindMeetingMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.student.RemoveStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.MeetingDateMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.*;
import gr.aueb.cf.schoolappspringbootmvc.repository.ClassroomRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.MeetingDateRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Implementation of the {@link IClassroomService} interface.
 * Provides methods for managing classrooms.
 */

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomServiceImpl implements IClassroomService {

    // The classroom repository

    private final ClassroomRepository classroomRepository;

    // The teacher repository

    private final TeacherRepository teacherRepository;

    // The student repository

    private final StudentRepository studentRepository;

    // The meeting date repository

    private final MeetingDateRepository meetingDateRepository;

    // The classroom mapper

    private final ClassroomMapper classroomMapper;

    // The teacher service

    private final ITeacherService teacherService;

    // The meeting date mapper

    private final MeetingDateMapper meetingDateMapper;


    /**
     * Creates a classroom.
     * @param classroomDTO the data for creating the classroom.
     * @return the created classroom.
     */

    @Override
    @Transactional
    public Classroom createClassroom(CreateClassroomDTO classroomDTO) {
        try {
            // Get the current authenticated teacher
            Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                    .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

            // Map DTO to classroom entity
            Classroom classroom = classroomMapper.toClassroom(classroomDTO, currentTeacher.getId());

            // Add the creator as a teacher of the classroom
            classroom.setCreator(currentTeacher);
            classroom.addTeacher(currentTeacher);

            // Save the classroom entity
            Classroom savedClassroom = classroomRepository.save(classroom);

            log.info("Classroom created successfully with ID: {}", savedClassroom.getId());
            return savedClassroom;
        } catch (Exception e) {
            log.error("Error creating classroom", e);
            throw new RuntimeException("Error creating classroom", e);
        }
    }

    /**
     * Retrieves the teacher who created the classroom.
     * @param classroomId the id of the classroom.
     * @return the teacher who created the classroom.
     */

    @Override
    @Transactional(readOnly = true)
    public Teacher getCreatorTeacher(Long classroomId) {
        try {
            Classroom classroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            return classroom.getCreator();
        } catch (Exception e) {
            log.error("Error retrieving creator teacher for classroom ID: {}", classroomId, e);
            throw new RuntimeException("Error retrieving creator teacher", e);
        }
    }

    /**
     * Retrieves the students in the classroom.
     * @param classroomId the id of the classroom.
     * @return a list of students in the classroom.
     */

    @Override
    @Transactional(readOnly = true)
    public List<Student> getStudentsInClassroom(Long classroomId) {
        try {
            Classroom classroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            return classroom.getStudentsOfClassroom();
        } catch (Exception e) {
            log.error("Error retrieving students for classroom ID: {}", classroomId, e);
            throw new RuntimeException("Error retrieving students", e);
        }
    }

    /**
     * Retrieves the teacher of the classroom.
     * @param teacherId the id of the classroom.
     * @return the teacher of the classroom.
     */

    @Override
    @Transactional(readOnly = true)
    public List<Classroom> findClassroomsByTeacher(Long teacherId) {
        try {
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            return teacher.getClassrooms();
        } catch (Exception e) {
            log.error("Error retrieving classrooms for teacher ID: {}", teacherId, e);
            throw new RuntimeException("Error retrieving classrooms", e);
        }
    }

    /**
     * Retrieves all classrooms.
     * @return a list of all classrooms.
     */

    @Override
    @Transactional(readOnly = true)
    public List<Classroom> findAllClassrooms() {
        try {
            return classroomRepository.findAll();
        } catch (Exception e) {
            log.error("Error retrieving all classrooms", e);
            throw new RuntimeException("Error retrieving all classrooms", e);
        }
    }

    /**
     * Updates a classroom.
     * @param classroomId the id of the classroom.
     * @param classroomDTO the data for updating the classroom.
     * @return the updated classroom.
     */

    @Override
    @Transactional
    public Classroom updateClassroom(Long classroomId, CreateClassroomDTO classroomDTO) {
        try {
            Classroom existingClassroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            existingClassroom.setName(classroomDTO.getName());
            existingClassroom.setDescription(classroomDTO.getDescription());
            existingClassroom.setClassroomUrl(classroomDTO.getClassroomUrl());
            existingClassroom.setImageUrl(classroomDTO.getImageUrl());

            Classroom updatedClassroom = classroomRepository.save(existingClassroom);
            log.info("Classroom updated successfully with ID: {}", updatedClassroom.getId());
            return updatedClassroom;
        } catch (Exception e) {
            log.error("Error updating classroom ID: {}", classroomId, e);
            throw new RuntimeException("Error updating classroom", e);
        }
    }

    /**
     * Deletes a classroom.
     * @param classroomId the id of the classroom to delete.
     */

    @Override
    @Transactional
    public void deleteClassroom(Long classroomId) {
        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        // Remove associations with teachers
        for (Teacher teacher : classroom.getTeachers()) {
            teacher.getClassrooms().remove(classroom);
        }
        classroom.getTeachers().clear();

        // Remove associations with extra teachers
        for (Teacher teacher : classroom.getExtraTeachers()) {
            teacher.getExtraClassrooms().remove(classroom);
        }
        classroom.getExtraTeachers().clear();

        // Remove associations with students
        for (Student student : classroom.getStudentsOfClassroom()) {
            student.setClassroom(null);
        }
        classroom.getStudentsOfClassroom().clear();

        // Remove associated meeting dates
        for (MeetingDate meetingDate : classroom.getMeetingDates()) {
            meetingDate.setClassroom(null);
        }
        classroom.getMeetingDates().clear();

        classroomRepository.delete(classroom);
        log.info("Classroom deleted successfully with ID: {}", classroomId);
    }

    /**
     * Checks if a classroom name exists.
     * @param name the name of the classroom.
     * @return true if the classroom name exists, otherwise false.
     */
    @Override
    public boolean classroomNameExists(String name) {
        try{
            return classroomRepository.existsByName(name);
        } catch (Exception e) {
            log.error("Error checking if classroom name exists", e);
            throw new RuntimeException("Error checking if classroom name exists", e);
        }
    }

    /**
     * Adds a teacher to a classroom.
     * @param classroomId the id of the classroom.
     * @param teacherUsername the username of the teacher.
     */

    @Override
    @Transactional
    public void addTeacherToClassroom(Long classroomId, String teacherUsername) {
        try {
            Classroom classroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Teacher teacher = teacherRepository.findByUserUsername(teacherUsername)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            classroom.addExtraTeacher(teacher); // Use addExtraTeacher method to add to extra_classroom_teachers table
            classroomRepository.save(classroom);
            log.info("Teacher {} added to classroom ID: {}", teacherUsername, classroomId);
        } catch (Exception e) {
            log.error("Error adding teacher {} to classroom ID: {}", teacherUsername, classroomId, e);
            throw new RuntimeException("Error adding teacher to classroom", e);
        }
    }

    /**
     * Retrieves classrooms by teacher.
     * @param teacherId the id of the teacher.
     * @param pageable the pageable object.
     * @return a page of classrooms.
     */

    @Override
    public Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable) {
        return classroomRepository.findByTeachers_Id(teacherId, pageable);
    }

    /**
     * Retrieves a classroom by id.
     * @param classroomId the id of the classroom.
     * @return the classroom with the specified id.
     */

    @Override
    public Optional<Classroom> findById(Long classroomId) throws Exception {
        try{
            return classroomRepository.findById(classroomId);
        }catch (Exception e){
            log.error("Error retrieving classroom with ID: {}", classroomId, e);
            throw new Exception("Error retrieving classroom", e);
        }
    }

    /**
     * Adds a teacher to a classroom.
     * @param dto the DTO containing the classroom and teacher information.
     */

    @Override
    @Transactional
    public void addTeacherToClassroom(AddTeacherToClassroomDTO dto) {
        try {
            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Teacher teacher = teacherRepository.findByUserUsername(dto.getTeacherUsername())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            classroom.addExtraTeacher(teacher); // Use addExtraTeacher method to add to extra_classroom_teachers table
            classroomRepository.save(classroom);
            log.info("Teacher {} added to this classroom ID: {}", dto.getTeacherUsername(), dto.getClassroomId());
        } catch (Exception e) {
            log.error("Error adding teacher {} to classroom ID: {}", dto.getTeacherUsername(), dto.getClassroomId(), e);
            throw new RuntimeException("Error adding teacher to classroom", e);
        }
    }

    /**
     * Saves a classroom.
     * @param classroom the classroom to save.
     */

    @Override
    @Transactional
    public void save(Classroom classroom) {
        try {
            if (classroomRepository.existsByName(classroom.getName())) {
                throw new ClassroomAlreadyExistsException("Classroom with name " + classroom.getName() + " already exists");
            }
            classroomRepository.save(classroom);
        } catch (Exception e) {
            log.error("Error saving classroom", e);
            throw new RuntimeException("Error saving classroom", e);
        }
    }

    /**
     * Updates the details of a classroom.
     * This method first retrieves the currently authenticated teacher and the classroom to be updated.
     * It then checks if the authenticated teacher is the creator of the classroom.
     * If not, it throws a RuntimeException.
     * If the teacher is the creator, it updates the classroom details using the provided DTO and saves the updated classroom.
     *
     * @param classroomId The ID of the classroom to be updated.
     * @param classroomUpdateDTO The DTO containing the new details of the classroom.
     * @return The updated classroom.
     * @throws RuntimeException if the authenticated teacher is not the creator of the classroom or if the classroom or teacher could not be found.
     */

    @Override
    @Transactional
    public Classroom updateClassroomDetails(Long classroomId, ClassroomUpdateDTO classroomUpdateDTO) {
        Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!classroom.getCreator().equals(currentTeacher)) {
            throw new RuntimeException("Teacher not authorized to update this classroom");
        }

        classroomMapper.updateClassroomFromDTO(classroomUpdateDTO, classroom);

        return classroomRepository.save(classroom);
    }

    /**
     * Updates the meeting date of a classroom.
     * This method first retrieves the currently authenticated teacher and the classroom for which the meeting date is to be updated.
     * It then checks if the authenticated teacher is the creator of the classroom.
     * If not, it throws a RuntimeException.
     * If the teacher is the creator, it retrieves the meeting date to be updated, updates the meeting date details using the provided DTO, and saves the updated meeting date.
     *
     * @param classroomId The ID of the classroom for which the meeting date is to be updated.
     * @param meetingDateId The ID of the meeting date to be updated.
     * @param meetingUpdateDTO The DTO containing the new details of the meeting date.
     * @return The updated meeting date.
     * @throws RuntimeException if the authenticated teacher is not the creator of the classroom, or if the classroom, teacher, or meeting date could not be found.
     */

    @Override
    @Transactional
    public MeetingDate updateMeetingDate(Long classroomId, Long meetingDateId, UpdateMeetingDateDTO meetingUpdateDTO) {
        Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

        Classroom classroom = classroomRepository.findById(classroomId)
                .orElseThrow(() -> new RuntimeException("Classroom not found"));

        if (!classroom.getCreator().equals(currentTeacher)) {
            throw new RuntimeException("Teacher not authorized to update meeting dates for this classroom");
        }

        MeetingDate meetingDate = meetingDateRepository.findById(meetingDateId)
                .orElseThrow(() -> new RuntimeException("Meeting date not found"));

        meetingDateMapper.updateMeetingDateFromDTO(meetingUpdateDTO, meetingDate);

        return meetingDateRepository.save(meetingDate);
    }

    /**
     * Retrieves all classrooms and meetings for the currently authenticated teacher.
     * This method first retrieves the currently authenticated teacher.
     * It then retrieves all classrooms where the teacher is the creator or a teacher of the classroom.
     * For each classroom, it retrieves all meeting dates associated with the classroom.
     * It then maps each classroom and its meeting dates to a ClassroomFindMeetingsDTO object and returns a list of these objects.
     *
     * @return a list of ClassroomFindMeetingsDTO objects containing all classrooms and their meeting dates for the currently authenticated teacher.
     * @throws RuntimeException if the authenticated teacher could not be found.
     */

    @Override
    @Transactional(readOnly = true)
    public List<ClassroomFindMeetingsDTO> getAllClassroomsAndMeetingsForCurrentTeacher() {
        Teacher currentTeacher = teacherService.getCurrentAuthenticatedTeacher()
                .orElseThrow(() -> new RuntimeException("Authenticated teacher not found"));

        List<Classroom> classrooms = classroomRepository.findByCreatorOrTeachersContaining(currentTeacher, currentTeacher);

        return classrooms.stream().map(classroom -> {
            ClassroomFindMeetingsDTO classroomDTO = classroomMapper.toClassroomFindMeetingsDTO(classroom);
            List<FindMeetingMeetingDateDTO> meetingDates = meetingDateRepository.findByClassroom(classroom).stream()
                    .map(meetingDateMapper::toMeetingDateFindMeetingsDTO)
                    .collect(Collectors.toList());
            classroomDTO.setMeetingDates(meetingDates);
            return classroomDTO;
        }).collect(Collectors.toList());
    }

    /**
     * Removes a student from a classroom.
     * This method first retrieves the classroom and the student to be removed using the provided DTO.
     * It then removes the student from the classroom and saves the updated classroom.
     * If the classroom or student could not be found, or if an error occurs during the removal process, a RuntimeException is thrown.
     *
     * @param dto The DTO containing the IDs of the classroom and the student to be removed.
     * @throws RuntimeException if the classroom or student could not be found, or if an error occurs during the removal process.
     */

    @Override
    @Transactional
    public void removeStudentFromClassroom(RemoveStudentDTO dto) {
        try{
            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Student student = studentRepository.findById(dto.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            classroom.removeStudent(student);
            classroomRepository.save(classroom);
            log.info("Student {} removed from classroom ID: {}", student.getId(), classroom.getId());

        }catch (Exception e){
            log.error("Error removing student from classroom", e);
            throw new RuntimeException("Error removing student from classroom", e);
        }
    }


    /**
     * Checks if a student is in a classroom.
     * @param classroomId the name of the classroom.
     * @param studentId the id of the student.
     * @return true if the student is in the classroom, otherwise false.
     */
    @Override
    public boolean isStudentInClassroom(Long classroomId, Long studentId) {
        try{
            return classroomRepository.existsByIdAndStudentsOfClassroom_Id(classroomId, studentId);
        }catch (Exception e){
            log.error("Error checking if student is in classroom", e);
            throw new RuntimeException("Error checking if student is in classroom", e);
        }
    }

    /**
     * Adds a student to a classroom.
     * @param classroomId the name of the classroom.
     * @param studentId the id of the student.
     */

    @Override
    public void addStudentToClassroom(Long classroomId, Long studentId) {
        if (isStudentInClassroom(classroomId, studentId)) {
            throw new RuntimeException("Student is already in classroom");
        }
        try{
            Classroom classroom = classroomRepository.findById(classroomId)
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Student student = studentRepository.findById(studentId)
                    .orElseThrow(() -> new RuntimeException("Student not found"));

            classroom.addStudent(student);
            classroomRepository.save(classroom);
            log.info("Student {} added to classroom ID: {}", student.getId(), classroom.getId());

        }catch (Exception e){
            log.error("Error adding student to classroom", e);
            throw new RuntimeException("Error adding student to classroom", e);
        }
    }


    /**
     * Retrieves a classroom by its name.
     * @param id the name of the classroom.
     * @return the classroom with the specified name.
     * @throws Exception if an error occurs while retrieving the classroom.
     */

    @Override
    public ClassroomReadOnlyDTO getByClassroomId(Long id) throws Exception {
        try{
            if (!classroomRepository.existsById(id)) {
                throw new RuntimeException("Classroom not found");
            }
            Classroom classroom = classroomRepository.findById(id).orElseThrow(() ->
                    new RuntimeException("Classroom not found"));
            log.info("Classroom retrieved successfully with ID: {}", id);
            return ClassroomMapper.toReadOnlyDTO(classroom);
        }catch (Exception e){
            log.error("Error retrieving classroom with ID: {}", id, e);
            throw new Exception("Error retrieving classroom", e);
        }
    }

    @Override
    public List<ClassroomStudentsClassroomDTO> findClassroomsByStudentId(Long studentId) {
        try {
            if (!studentRepository.existsById(studentId)) {
                throw new StudentNotFoundException("Student not found with ID: " + studentId);
            }
            log.info("Retrieving classrooms by student ID: {}", studentId);
            List<Classroom> classrooms = classroomRepository.findClassroomsByStudentsOfClassroom_Id(studentId);
            log.info("Classrooms retrieved successfully by student ID: {}", studentId);
            if (classrooms.isEmpty()) {
                throw new RuntimeException("No classrooms found for student ID: " + studentId);
            }
            log.info("Classrooms found for student ID: {}", studentId);
            return ClassroomMapper.toClassroomStudentsClassroomDTOList(classrooms);
        } catch (StudentNotFoundException e) {
            log.error("Student not found: {}", e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Error retrieving classrooms by student ID: {}", studentId, e);
            throw new RuntimeException("Error retrieving classrooms by student ID", e);
        }
    }

    @Override
    public List<Classroom> getAllClassroomsByTeacherId(Long teacherId) {
        try{
            if (!teacherRepository.existsById(teacherId)) {
                throw new RuntimeException("Teacher not found with ID: " + teacherId);
            }
            Teacher teacher = teacherRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            return teacher.getClassrooms();
        }
        catch (Exception e){
            log.error("Error retrieving classrooms by teacher ID: {}", teacherId, e);
            throw new RuntimeException("Error retrieving classrooms by teacher ID", e);
        }
    }


}
