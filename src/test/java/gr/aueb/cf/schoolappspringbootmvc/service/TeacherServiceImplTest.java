package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.teacher.RegisterTeacherDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Teacher;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.TeacherRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.TeacherAlreadyExistsException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.testng.Assert.*;

/**
 * Unit tests for the {@link TeacherServiceImpl} class.
 */
public class TeacherServiceImplTest {

    @Mock
    private TeacherRepository teacherRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private TeacherServiceImpl teacherService;

    /**
     * Sets up the test environment. Initializes mocks and injects them into the tested class.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Tests the {@link TeacherServiceImpl#registerTeacher(RegisterTeacherDTO)} method.
     * Verifies that a teacher is registered successfully when the user does not already exist.
     */
    @Test
    public void testRegisterTeacher() {
        RegisterTeacherDTO dto = new RegisterTeacherDTO();
        dto.setUsername("testuser");
        dto.setPassword("testpassword");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        Teacher teacher = new Teacher();
        teacher.addUser(user);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(teacherRepository.save(any(Teacher.class))).thenReturn(teacher);

        try {
            Teacher result = teacherService.registerTeacher(dto);
            assertNotNull(result);
            assertNotNull(result.getUser());
            assertEquals(result.getUser().getUsername(), "testuser");
            verify(userRepository, times(1)).findByUsername("testuser");
            verify(passwordEncoder, times(1)).encode("testpassword");
            verify(teacherRepository, times(1)).save(any(Teacher.class));
        } catch (TeacherAlreadyExistsException e) {
            fail("TeacherAlreadyExistsException should not be thrown");
        }
    }

    /**
     * Tests the {@link TeacherServiceImpl#registerTeacher(RegisterTeacherDTO)} method.
     * Verifies that a {@link TeacherAlreadyExistsException} is thrown when a teacher with the specified username already exists.
     *
     * @throws TeacherAlreadyExistsException if a teacher with the specified username already exists.
     */
    @Test(expectedExceptions = TeacherAlreadyExistsException.class)
    public void testRegisterTeacherThrowsException() throws TeacherAlreadyExistsException {
        RegisterTeacherDTO dto = new RegisterTeacherDTO();
        dto.setUsername("existinguser");
        dto.setPassword("password");

        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        teacherService.registerTeacher(dto);
    }

    /**
     * Tests the {@link TeacherServiceImpl#findAllTeachers()} method.
     * Verifies that the method returns a list of all teachers.
     */
    @Test
    public void testFindAllTeachers() {
        Teacher teacher = new Teacher();
        when(teacherRepository.findAll()).thenReturn(Collections.singletonList(teacher));

        try {
            List<Teacher> teachers = teacherService.findAllTeachers();
            assertNotNull(teachers);
            assertEquals(teachers.size(), 1);
            verify(teacherRepository, times(1)).findAll();
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }
}
