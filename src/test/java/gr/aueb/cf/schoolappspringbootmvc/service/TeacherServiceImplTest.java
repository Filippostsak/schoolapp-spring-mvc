package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private StudentServiceImpl studentServiceImpl;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRegisterTeacherSuccess() throws TeacherAlreadyExistsException {
        RegisterTeacherDTO dto = new RegisterTeacherDTO(
                "newTeacher", "newTeacher@example.com", "Password1!", "Password1!",
                "John", "Doe", LocalDate.of(1980, 1, 1), "Country", "City"
        );

        User user = Mapper.extractUserFromRegisterTeacherDTO(dto);
        Teacher teacher = Mapper.extractTeacherFromRegisterTeacherDTO(dto);

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(dto.getPassword())).thenReturn("encodedPassword");
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        Teacher registeredTeacher = teacherService.registerTeacher(dto);

        assertNotNull(registeredTeacher);
        assertEquals(dto.getUsername(), registeredTeacher.getUser().getUsername());
        verify(userRepository, times(1)).findByUsername(dto.getUsername());
        verify(passwordEncoder, times(1)).encode(dto.getPassword());
        verify(teacherRepository, times(1)).save(any(Teacher.class));
    }

    @Test
    public void testRegisterTeacherAlreadyExists() {
        RegisterTeacherDTO dto = new RegisterTeacherDTO(
                "existingTeacher", "existingTeacher@example.com", "Password1!", "Password1!",
                "John", "Doe", LocalDate.of(1980, 1, 1), "Country", "City"
        );

        when(userRepository.findByUsername(dto.getUsername())).thenReturn(Optional.of(new User()));

        TeacherAlreadyExistsException exception = assertThrows(TeacherAlreadyExistsException.class, () -> teacherService.registerTeacher(dto));

        assertEquals("existingTeacher", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(dto.getUsername());
        verify(teacherRepository, never()).save(any(Teacher.class));
    }

    @Test
    public void testFindAllTeachers() throws Exception {
        List<Teacher> mockTeachers = List.of(new Teacher(), new Teacher());

        when(teacherRepository.findAll()).thenReturn(mockTeachers);

        List<Teacher> teachers = teacherService.findAllTeachers();

        assertEquals(mockTeachers.size(), teachers.size());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testFindAllTeachersException() {
        when(teacherRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        Exception exception = assertThrows(Exception.class, () -> teacherService.findAllTeachers());

        assertEquals("Error retrieving all teachers", exception.getMessage());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    public void testSearchStudentsByLastName() {
        String lastName = "Smith";
        List<Student> mockStudents = List.of(new Student(), new Student());

        when(studentServiceImpl.findByLastnameContainingOrderByLastname(lastName)).thenReturn(mockStudents);

        List<Student> students = teacherService.searchStudentsByLastName(lastName);

        assertEquals(mockStudents.size(), students.size());
        verify(studentServiceImpl, times(1)).findByLastnameContainingOrderByLastname(lastName);
    }

    @Test
    public void testFindTeacherByFirstname() {
        String firstname = "John";
        Teacher mockTeacher = new Teacher();

        when(teacherRepository.findByFirstname(firstname)).thenReturn(mockTeacher);

        Teacher teacher = teacherService.findTeacherByFirstname(firstname);

        assertNotNull(teacher);
        verify(teacherRepository, times(1)).findByFirstname(firstname);
    }

    @Test
    public void testGetCurrentAuthenticatedTeacher() {
        String username = "authenticatedUser";
        User user = new User();
        user.setUsername(username);
        Teacher mockTeacher = new Teacher();
        mockTeacher.addUser(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(mock(org.springframework.security.core.Authentication.class));
        when(securityContext.getAuthentication().getPrincipal()).thenReturn(userDetails);

        SecurityContextHolder.setContext(securityContext);

        when(teacherRepository.findByUserUsername(username)).thenReturn(Optional.of(mockTeacher));

        Optional<Teacher> teacher = teacherService.getCurrentAuthenticatedTeacher();

        assertTrue(teacher.isPresent());
        assertEquals(username, teacher.get().getUser().getUsername());
        verify(teacherRepository, times(1)).findByUserUsername(username);
    }

    @Test
    public void testFindByUsername() {
        String username = "user1";
        User user = new User();
        user.setUsername(username);
        Teacher mockTeacher = new Teacher();
        mockTeacher.addUser(user);

        when(teacherRepository.findByUserUsername(username)).thenReturn(Optional.of(mockTeacher));

        Optional<Teacher> teacher = teacherService.findByUsername(username);

        assertTrue(teacher.isPresent());
        assertEquals(username, teacher.get().getUser().getUsername());
        verify(teacherRepository, times(1)).findByUserUsername(username);
    }

    @Test
    public void testFindByUsernameContaining() throws Exception {
        String username = "user";
        List<Teacher> mockTeachers = List.of(new Teacher(), new Teacher());

        when(teacherRepository.findByUserUsernameContaining(username)).thenReturn(mockTeachers);

        List<Teacher> teachers = teacherService.findByUsernameContaining(username);

        assertEquals(mockTeachers.size(), teachers.size());
        verify(teacherRepository, times(1)).findByUserUsernameContaining(username);
    }
}
