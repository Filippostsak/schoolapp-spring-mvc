package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.*;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClassroomServiceImplTest {

    @Mock
    private ClassroomRepository classroomRepository;

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private MeetingDateRepository meetingDateRepository;

    @Mock
    private ClassroomMapper classroomMapper;

    @Mock
    private ITeacherService teacherService;

    @Mock
    private MeetingDateMapper meetingDateMapper;

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    private Classroom classroom;
    private Teacher teacher;
    private Student student;
    private MeetingDate meetingDate;
    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setEmail("teacher@example.com");

        teacher = new Teacher();
        teacher.setId(1L);
        teacher.setUser(user);

        classroom = new Classroom();
        classroom.setId(1L);
        classroom.setName("Test Classroom");
        classroom.setCreator(teacher);
        classroom.addTeacher(teacher);

        student = new Student();
        student.setId(1L);

        meetingDate = new MeetingDate();
        meetingDate.setId(1L);
    }

    @Test
    void createClassroom_shouldCreateClassroom() {
        CreateClassroomDTO classroomDTO = new CreateClassroomDTO();
        classroomDTO.setName("New Classroom");

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomMapper.toClassroom(classroomDTO, teacher.getId())).thenReturn(classroom);
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        Classroom createdClassroom = classroomService.createClassroom(classroomDTO);

        assertNotNull(createdClassroom);
        assertEquals(classroom.getId(), createdClassroom.getId());
        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void getCreatorTeacher_shouldReturnCreator() {
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        Teacher creator = classroomService.getCreatorTeacher(classroom.getId());

        assertNotNull(creator);
        assertEquals(teacher.getId(), creator.getId());
    }

    @Test
    void getStudentsInClassroom_shouldReturnStudents() {
        classroom.addStudent(student);
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        List<Student> students = classroomService.getStudentsInClassroom(classroom.getId());

        assertNotNull(students);
        assertEquals(1, students.size());
        assertEquals(student.getId(), students.get(0).getId());
    }

    @Test
    void findClassroomsByTeacher_shouldReturnClassrooms() {
        teacher.addClassroom(classroom);
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        List<Classroom> classrooms = classroomService.findClassroomsByTeacher(teacher.getId());

        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
        assertEquals(classroom.getId(), classrooms.get(0).getId());
    }

    @Test
    void findAllClassrooms_shouldReturnAllClassrooms() {
        when(classroomRepository.findAll()).thenReturn(List.of(classroom));

        List<Classroom> classrooms = classroomService.findAllClassrooms();

        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
    }

    @Test
    void updateClassroom_shouldUpdateClassroom() {
        CreateClassroomDTO classroomDTO = new CreateClassroomDTO();
        classroomDTO.setName("Updated Classroom");

        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        Classroom updatedClassroom = classroomService.updateClassroom(classroom.getId(), classroomDTO);

        assertNotNull(updatedClassroom);
        assertEquals(classroom.getId(), updatedClassroom.getId());
        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void deleteClassroom_shouldDeleteClassroom() {
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));

        classroomService.deleteClassroom(classroom.getId());

        verify(classroomRepository).delete(any(Classroom.class));
    }

    @Test
    void deleteClassroom_whenClassroomNotFound_shouldThrowException() {
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> classroomService.deleteClassroom(classroom.getId()));
    }

    @Test
    void addTeacherToClassroom_shouldAddTeacher() {
        AddTeacherToClassroomDTO dto = new AddTeacherToClassroomDTO();
        dto.setClassroomId(classroom.getId());
        dto.setTeacherUsername("testTeacher");

        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(teacherRepository.findByUserUsername(dto.getTeacherUsername())).thenReturn(Optional.of(teacher));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        classroomService.addTeacherToClassroom(dto);

        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void updateMeetingDate_shouldUpdateMeetingDate() {
        UpdateMeetingDateDTO meetingUpdateDTO = new UpdateMeetingDateDTO();
        meetingUpdateDTO.setDate(meetingDate.getDate());

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(meetingDateRepository.findById(meetingDate.getId())).thenReturn(Optional.of(meetingDate));
        doNothing().when(meetingDateMapper).updateMeetingDateFromDTO(meetingUpdateDTO, meetingDate);
        when(meetingDateRepository.save(any(MeetingDate.class))).thenReturn(meetingDate);

        MeetingDate updatedMeetingDate = classroomService.updateMeetingDate(classroom.getId(), meetingDate.getId(), meetingUpdateDTO);

        assertNotNull(updatedMeetingDate);
        assertEquals(meetingDate.getId(), updatedMeetingDate.getId());
        verify(meetingDateRepository).save(any(MeetingDate.class));
    }


    @Test
    void updateClassroomDetails_shouldUpdateClassroomDetails() {
        ClassroomUpdateDTO classroomUpdateDTO = new ClassroomUpdateDTO();
        classroomUpdateDTO.setName("Updated Classroom");

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        Classroom updatedClassroom = classroomService.updateClassroomDetails(classroom.getId(), classroomUpdateDTO);

        assertNotNull(updatedClassroom);
        assertEquals(classroom.getId(), updatedClassroom.getId());
        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void removeStudentFromClassroom_shouldRemoveStudent() {
        RemoveStudentDTO dto = new RemoveStudentDTO();
        dto.setClassroomId(classroom.getId());
        dto.setStudentId(student.getId());

        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        classroomService.removeStudentFromClassroom(dto);

        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void addStudentToClassroom_shouldAddStudent() {
        when(classroomRepository.findById(classroom.getId())).thenReturn(Optional.of(classroom));
        when(studentRepository.findById(student.getId())).thenReturn(Optional.of(student));
        when(classroomRepository.save(any(Classroom.class))).thenReturn(classroom);

        classroomService.addStudentToClassroom(classroom.getId(), student.getId());

        verify(classroomRepository).save(any(Classroom.class));
    }

    @Test
    void findClassroomsByStudentId_shouldReturnClassrooms() throws Exception {
        when(studentRepository.existsById(student.getId())).thenReturn(true);
        when(classroomRepository.findClassroomsByStudentsOfClassroom_Id(student.getId())).thenReturn(List.of(classroom));

        List<ClassroomStudentsClassroomDTO> classrooms = classroomService.findClassroomsByStudentId(student.getId());

        assertNotNull(classrooms);
        assertEquals(1, classrooms.size());
    }

    @Test
    void getAllClassroomsByTeacherId_shouldReturnClassrooms() {
        when(teacherRepository.existsById(teacher.getId())).thenReturn(true);
        when(teacherRepository.findById(teacher.getId())).thenReturn(Optional.of(teacher));

        List<Classroom> classrooms = classroomService.getAllClassroomsByTeacherId(teacher.getId());

        assertNotNull(classrooms);
        assertEquals(teacher.getClassrooms().size(), classrooms.size());
    }

    @Test
    void isStudentInClassroom_shouldReturnTrue() {
        when(classroomRepository.existsByIdAndStudentsOfClassroom_Id(classroom.getId(), student.getId())).thenReturn(true);

        boolean result = classroomService.isStudentInClassroom(classroom.getId(), student.getId());

        assertTrue(result);
    }

    @Test
    void classroomNameExists_shouldReturnTrue() {
        when(classroomRepository.existsByName(classroom.getName())).thenReturn(true);

        boolean result = classroomService.classroomNameExists(classroom.getName());

        assertTrue(result);
    }
}
