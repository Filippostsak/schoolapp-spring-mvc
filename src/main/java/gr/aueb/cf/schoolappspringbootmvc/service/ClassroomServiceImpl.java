package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.ClassroomRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ClassroomServiceImpl implements IClassroomService {

    private final ClassroomRepository classroomRepository;
    private final TeacherRepository teacherRepository;
    private final ClassroomMapper classroomMapper;
    private final ITeacherService teacherService;

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

    @Override
    public boolean classroomNameExists(String name) {
        try{
            return classroomRepository.existsByName(name);
        } catch (Exception e) {
            log.error("Error checking if classroom name exists", e);
            throw new RuntimeException("Error checking if classroom name exists", e);
        }
    }

    @Override
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


    @Override
    public Page<Classroom> findClassroomsByTeacher(Long teacherId, Pageable pageable) {
        return classroomRepository.findByTeachers_Id(teacherId, pageable);
    }

    @Override
    public Optional<Classroom> findById(Long classroomId) throws Exception {
        try{
            return classroomRepository.findById(classroomId);
        }catch (Exception e){
            log.error("Error retrieving classroom with ID: {}", classroomId, e);
            throw new Exception("Error retrieving classroom", e);
        }
    }

    @Override
    public void addTeacherToClassroom(AddTeacherToClassroomDTO dto) {
        try {
            Classroom classroom = classroomRepository.findById(dto.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));

            Teacher teacher = teacherRepository.findByUserUsername(dto.getTeacherUsername())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));

            classroom.addExtraTeacher(teacher); // Use addExtraTeacher method to add to extra_classroom_teachers table
            classroomRepository.save(classroom);
            log.info("Teacher {} added to classroom ID: {}", dto.getTeacherUsername(), dto.getClassroomId());
        } catch (Exception e) {
            log.error("Error adding teacher {} to classroom ID: {}", dto.getTeacherUsername(), dto.getClassroomId(), e);
            throw new RuntimeException("Error adding teacher to classroom", e);
        }
    }


    @Override
    public void save(Classroom classroom) throws ClassroomAlreadyExistsException {
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
}
