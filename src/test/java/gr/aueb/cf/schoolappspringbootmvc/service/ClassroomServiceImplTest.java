package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.ClassroomUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.classroom.CreateClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.meetingDate.UpdateMeetingDateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.AddTeacherToClassroomDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.ClassroomMapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Classroom;
import gr.aueb.cf.schoolappspringbootmvc.model.MeetingDate;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.repository.ClassroomRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.MeetingDateRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.ClassroomAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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

    @InjectMocks
    private ClassroomServiceImpl classroomService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    //Positive scenarios

    @Test
    public void testCreateClassroom() {
        CreateClassroomDTO dto = new CreateClassroomDTO();
        dto.setName("Math");
        dto.setDescription("Math Classroom");

        Teacher teacher = new Teacher();
        teacher.setId(1L);
        Classroom classroom = new Classroom();
        classroom.setName("Math");
        classroom.setDescription("Math Classroom");
        classroom.setCreator(teacher);

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomMapper.toClassroom(dto, teacher.getId())).thenReturn(classroom);
        when(classroomRepository.save(classroom)).thenReturn(classroom);

        Classroom createdClassroom = classroomService.createClassroom(dto);

        assertNotNull(createdClassroom);
        assertEquals("Math", createdClassroom.getName());
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testGetCreatorTeacher() {
        Long classroomId = 1L;
        Classroom classroom = new Classroom();
        Teacher teacher = new Teacher();
        classroom.setCreator(teacher);

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));

        Teacher creatorTeacher = classroomService.getCreatorTeacher(classroomId);

        assertNotNull(creatorTeacher);
        verify(classroomRepository, times(1)).findById(classroomId);
    }

    @Test
    public void testGetStudentsInClassroom() {
        Long classroomId = 1L;
        Classroom classroom = new Classroom();
        List<Student> students = new ArrayList<>();
        classroom.setStudentsOfClassroom(students);

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));

        List<Student> result = classroomService.getStudentsInClassroom(classroomId);

        assertNotNull(result);
        verify(classroomRepository, times(1)).findById(classroomId);
    }

    @Test
    public void testFindClassroomsByTeacher() {
        Long teacherId = 1L;
        Teacher teacher = new Teacher();
        List<Classroom> classrooms = new ArrayList<>();
        teacher.setClassrooms(classrooms);

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.of(teacher));

        List<Classroom> result = classroomService.findClassroomsByTeacher(teacherId);

        assertNotNull(result);
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testFindAllClassrooms() {
        List<Classroom> classrooms = new ArrayList<>();
        when(classroomRepository.findAll()).thenReturn(classrooms);

        List<Classroom> result = classroomService.findAllClassrooms();

        assertNotNull(result);
        verify(classroomRepository, times(1)).findAll();
    }

    @Test
    public void testUpdateClassroom() {
        Long classroomId = 1L;
        CreateClassroomDTO dto = new CreateClassroomDTO();
        dto.setName("Updated Name");
        dto.setDescription("Updated Description");

        Classroom classroom = new Classroom();
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(classroom)).thenReturn(classroom);

        Classroom updatedClassroom = classroomService.updateClassroom(classroomId, dto);

        assertNotNull(updatedClassroom);
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testDeleteClassroom() {
        Long classroomId = 1L;
        Classroom classroom = new Classroom();

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));

        classroomService.deleteClassroom(classroomId);

        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, times(1)).delete(classroom);
    }

    @Test
    public void testClassroomNameExists() {
        String name = "Math";
        when(classroomRepository.existsByName(name)).thenReturn(true);

        boolean exists = classroomService.classroomNameExists(name);

        assertTrue(exists);
        verify(classroomRepository, times(1)).existsByName(name);
    }

    @Test
    public void testAddTeacherToClassroom() {
        Long classroomId = 1L;
        String teacherUsername = "teacher1";
        Classroom classroom = new Classroom();
        Teacher teacher = new Teacher();

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(teacherRepository.findByUserUsername(teacherUsername)).thenReturn(Optional.of(teacher));

        classroomService.addTeacherToClassroom(classroomId, teacherUsername);

        verify(classroomRepository, times(1)).findById(classroomId);
        verify(teacherRepository, times(1)).findByUserUsername(teacherUsername);
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testFindClassroomsByTeacherWithPageable() {
        Long teacherId = 1L;
        Pageable pageable = PageRequest.of(0, 10);
        Page<Classroom> classroomPage = new PageImpl<>(new ArrayList<>());

        when(classroomRepository.findByTeachers_Id(teacherId, pageable)).thenReturn(classroomPage);

        Page<Classroom> result = classroomService.findClassroomsByTeacher(teacherId, pageable);

        assertNotNull(result);
        verify(classroomRepository, times(1)).findByTeachers_Id(teacherId, pageable);
    }

    @Test
    public void testFindById() throws Exception {
        Long classroomId = 1L;
        Classroom classroom = new Classroom();

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));

        Optional<Classroom> result = classroomService.findById(classroomId);

        assertTrue(result.isPresent());
        verify(classroomRepository, times(1)).findById(classroomId);
    }

    @Test
    public void testAddTeacherToClassroomDTO() {
        AddTeacherToClassroomDTO dto = new AddTeacherToClassroomDTO();
        dto.setClassroomId(1L);
        dto.setTeacherUsername("teacher1");
        Classroom classroom = new Classroom();
        Teacher teacher = new Teacher();

        when(classroomRepository.findById(dto.getClassroomId())).thenReturn(Optional.of(classroom));
        when(teacherRepository.findByUserUsername(dto.getTeacherUsername())).thenReturn(Optional.of(teacher));

        classroomService.addTeacherToClassroom(dto);

        verify(classroomRepository, times(1)).findById(dto.getClassroomId());
        verify(teacherRepository, times(1)).findByUserUsername(dto.getTeacherUsername());
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testSave() throws ClassroomAlreadyExistsException {
        Classroom classroom = new Classroom();
        classroom.setName("Math");

        when(classroomRepository.existsByName(classroom.getName())).thenReturn(false);
        when(classroomRepository.save(classroom)).thenReturn(classroom);

        classroomService.save(classroom);

        verify(classroomRepository, times(1)).existsByName(classroom.getName());
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testUpdateClassroomDetails() {
        Long classroomId = 1L;
        ClassroomUpdateDTO classroomUpdateDTO = new ClassroomUpdateDTO();
        classroomUpdateDTO.setName("Updated Name");

        Teacher teacher = new Teacher();
        Classroom classroom = new Classroom();
        classroom.setCreator(teacher);

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(classroomRepository.save(classroom)).thenReturn(classroom);

        Classroom updatedClassroom = classroomService.updateClassroomDetails(classroomId, classroomUpdateDTO);

        assertNotNull(updatedClassroom);
        verify(teacherService, times(1)).getCurrentAuthenticatedTeacher();
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, times(1)).save(classroom);
    }

    @Test
    public void testUpdateMeetingDate() {
        Long classroomId = 1L;
        Long meetingDateId = 1L;
        UpdateMeetingDateDTO meetingUpdateDTO = new UpdateMeetingDateDTO();

        Teacher teacher = new Teacher();
        Classroom classroom = new Classroom();
        classroom.setCreator(teacher);
        MeetingDate meetingDate = new MeetingDate();

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(teacher));
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(meetingDateRepository.findById(meetingDateId)).thenReturn(Optional.of(meetingDate));
        when(meetingDateRepository.save(meetingDate)).thenReturn(meetingDate);

        MeetingDate updatedMeetingDate = classroomService.updateMeetingDate(classroomId, meetingDateId, meetingUpdateDTO);

        assertNotNull(updatedMeetingDate);
        verify(teacherService, times(1)).getCurrentAuthenticatedTeacher();
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(meetingDateRepository, times(1)).findById(meetingDateId);
        verify(meetingDateRepository, times(1)).save(meetingDate);
    }

    //Negative scenarios

    @Test
    public void testCreateClassroomWithException() {
        CreateClassroomDTO dto = new CreateClassroomDTO();

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.createClassroom(dto));

        assertEquals("Authenticated teacher not found", exception.getMessage());
        verify(teacherService, times(1)).getCurrentAuthenticatedTeacher();
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testGetCreatorTeacherNotFound() {
        Long classroomId = 1L;

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.getCreatorTeacher(classroomId));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
    }

    @Test
    public void testGetStudentsInClassroomNotFound() {
        Long classroomId = 1L;

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.getStudentsInClassroom(classroomId));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
    }

    @Test
    public void testFindClassroomsByTeacherNotFound() {
        Long teacherId = 1L;

        when(teacherRepository.findById(teacherId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.findClassroomsByTeacher(teacherId));

        assertEquals("Teacher not found", exception.getMessage());
        verify(teacherRepository, times(1)).findById(teacherId);
    }

    @Test
    public void testUpdateClassroomNotFound() {
        Long classroomId = 1L;
        CreateClassroomDTO dto = new CreateClassroomDTO();

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.updateClassroom(classroomId, dto));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testDeleteClassroomNotFound() {
        Long classroomId = 1L;

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.deleteClassroom(classroomId));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, never()).delete(any());
    }

    @Test
    public void testAddTeacherToClassroomNotFound() {
        Long classroomId = 1L;
        String teacherUsername = "teacher1";

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.addTeacherToClassroom(classroomId, teacherUsername));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(teacherRepository, never()).findByUserUsername(teacherUsername);
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testAddTeacherToClassroomTeacherNotFound() {
        Long classroomId = 1L;
        String teacherUsername = "teacher1";
        Classroom classroom = new Classroom();

        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(teacherRepository.findByUserUsername(teacherUsername)).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.addTeacherToClassroom(classroomId, teacherUsername));

        assertEquals("Teacher not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(teacherRepository, times(1)).findByUserUsername(teacherUsername);
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testAddTeacherToClassroomDTONotFound() {
        AddTeacherToClassroomDTO dto = new AddTeacherToClassroomDTO();
        dto.setClassroomId(1L);
        dto.setTeacherUsername("teacher1");

        when(classroomRepository.findById(dto.getClassroomId())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.addTeacherToClassroom(dto));

        assertEquals("Classroom not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(dto.getClassroomId());
        verify(teacherRepository, never()).findByUserUsername(dto.getTeacherUsername());
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testAddTeacherToClassroomDTOTeacherNotFound() {
        AddTeacherToClassroomDTO dto = new AddTeacherToClassroomDTO();
        dto.setClassroomId(1L);
        dto.setTeacherUsername("teacher1");
        Classroom classroom = new Classroom();

        when(classroomRepository.findById(dto.getClassroomId())).thenReturn(Optional.of(classroom));
        when(teacherRepository.findByUserUsername(dto.getTeacherUsername())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.addTeacherToClassroom(dto));

        assertEquals("Teacher not found", exception.getMessage());
        verify(classroomRepository, times(1)).findById(dto.getClassroomId());
        verify(teacherRepository, times(1)).findByUserUsername(dto.getTeacherUsername());
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testUpdateClassroomDetailsUnauthorized() {
        Long classroomId = 1L;
        ClassroomUpdateDTO classroomUpdateDTO = new ClassroomUpdateDTO();

        Teacher currentTeacher = new Teacher();
        Classroom classroom = new Classroom();
        classroom.setCreator(new Teacher()); // Different teacher

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(currentTeacher));
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.updateClassroomDetails(classroomId, classroomUpdateDTO));

        assertEquals("Teacher not authorized to update this classroom", exception.getMessage());
        verify(teacherService, times(1)).getCurrentAuthenticatedTeacher();
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(classroomRepository, never()).save(any());
    }

    @Test
    public void testUpdateMeetingDateUnauthorized() {
        Long classroomId = 1L;
        Long meetingDateId = 1L;
        UpdateMeetingDateDTO meetingUpdateDTO = new UpdateMeetingDateDTO();

        Teacher currentTeacher = new Teacher();
        Classroom classroom = new Classroom();
        classroom.setCreator(new Teacher());
        MeetingDate meetingDate = new MeetingDate();

        when(teacherService.getCurrentAuthenticatedTeacher()).thenReturn(Optional.of(currentTeacher));
        when(classroomRepository.findById(classroomId)).thenReturn(Optional.of(classroom));
        when(meetingDateRepository.findById(meetingDateId)).thenReturn(Optional.of(meetingDate));

        Exception exception = assertThrows(RuntimeException.class, () -> classroomService.updateMeetingDate(classroomId, meetingDateId, meetingUpdateDTO));

        assertEquals("Teacher not authorized to update meeting dates for this classroom", exception.getMessage());
        verify(teacherService, times(1)).getCurrentAuthenticatedTeacher();
        verify(classroomRepository, times(1)).findById(classroomId);
        verify(meetingDateRepository, times(1)).findById(meetingDateId);
        verify(meetingDateRepository, never()).save(any());
    }
}
