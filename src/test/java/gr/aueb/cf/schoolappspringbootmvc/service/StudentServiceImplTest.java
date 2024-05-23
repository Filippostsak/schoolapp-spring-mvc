package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.student.RegisterStudentDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Student;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.StudentRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.StudentAlreadyExistsException;
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
 * Unit tests for the {@link StudentServiceImpl} class.
 */
public class StudentServiceImplTest {

    @Mock
    private StudentRepository studentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private StudentServiceImpl studentService;

    /**
     * Sets up the test environment. Initializes mocks and injects them into the tested class.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link StudentServiceImpl#registerStudent(RegisterStudentDTO)} method.
     * Verifies that a student is registered successfully when the user does not already exist.
     */
    @Test
    public void testRegisterStudent() {
        RegisterStudentDTO dto = new RegisterStudentDTO();
        dto.setUsername("testuser");
        dto.setPassword("testpassword");

        User user = new User();
        user.setUsername("testuser");
        user.setPassword("testpassword");

        Student student = new Student();
        student.addUser(user);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(studentRepository.save(any(Student.class))).thenReturn(student);

        try {
            Student result = studentService.registerStudent(dto);
            assertNotNull(result);
            assertNotNull(result.getUser());
            assertEquals(result.getUser().getUsername(), "testuser");
            verify(userRepository, times(1)).findByUsername("testuser");
            verify(passwordEncoder, times(1)).encode("testpassword");
            verify(studentRepository, times(1)).save(any(Student.class));
        } catch (StudentAlreadyExistsException e) {
            fail("StudentAlreadyExistsException should not be thrown");
        }
    }

    /**
     * Tests the {@link StudentServiceImpl#registerStudent(RegisterStudentDTO)} method.
     * Verifies that a {@link StudentAlreadyExistsException} is thrown when a student with the specified username already exists.
     *
     * @throws StudentAlreadyExistsException if a student with the specified username already exists.
     */
    @Test(expectedExceptions = StudentAlreadyExistsException.class)
    public void testRegisterStudentThrowsException() throws StudentAlreadyExistsException {
        RegisterStudentDTO dto = new RegisterStudentDTO();
        dto.setUsername("existinguser");
        dto.setPassword("password");

        User existingUser = new User();
        existingUser.setUsername("existinguser");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        studentService.registerStudent(dto);
    }

    /**
     * Tests the {@link StudentServiceImpl#findAllStudents()} method.
     * Verifies that the method returns a list of all students.
     */
    @Test
    public void testFindAllStudents() {
        Student student = new Student();
        when(studentRepository.findAll()).thenReturn(Collections.singletonList(student));

        try {
            List<Student> students = studentService.findAllStudents();
            assertNotNull(students);
            assertEquals(students.size(), 1);
            verify(studentRepository, times(1)).findAll();
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }
}
