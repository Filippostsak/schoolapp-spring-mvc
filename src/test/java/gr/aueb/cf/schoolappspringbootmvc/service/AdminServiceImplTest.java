package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.AdminRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;
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
 * Unit tests for the {@link AdminServiceImpl} class.
 */
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    /**
     * Sets up the test environment. Initializes mocks and injects them into the tested class.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link AdminServiceImpl#registerAdmin(RegisterAdminDTO)} method.
     * Verifies that an admin is registered successfully when the user does not already exist.
     */
    @Test
    public void testRegisterAdmin() {
        RegisterAdminDTO dto = new RegisterAdminDTO();
        dto.setUsername("testadmin");
        dto.setPassword("testpassword");

        User user = new User();
        user.setUsername("testadmin");
        user.setPassword("testpassword");

        Admin admin = new Admin();
        admin.addUser(user);

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("encodedPassword");
        when(adminRepository.save(any(Admin.class))).thenReturn(admin);

        try {
            Admin result = adminService.registerAdmin(dto);
            assertNotNull(result);
            assertNotNull(result.getUser());
            assertEquals(result.getUser().getUsername(), "testadmin");
            verify(userRepository, times(1)).findByUsername("testadmin");
            verify(passwordEncoder, times(1)).encode("testpassword");
            verify(adminRepository, times(1)).save(any(Admin.class));
        } catch (AdminAlreadyExistsException e) {
            fail("AdminAlreadyExistsException should not be thrown");
        }
    }

    /**
     * Tests the {@link AdminServiceImpl#registerAdmin(RegisterAdminDTO)} method.
     * Verifies that an {@link AdminAlreadyExistsException} is thrown when an admin with the specified username already exists.
     *
     * @throws AdminAlreadyExistsException if an admin with the specified username already exists.
     */
    @Test(expectedExceptions = AdminAlreadyExistsException.class)
    public void testRegisterAdminThrowsException() throws AdminAlreadyExistsException {
        RegisterAdminDTO dto = new RegisterAdminDTO();
        dto.setUsername("existingadmin");
        dto.setPassword("password");

        User existingUser = new User();
        existingUser.setUsername("existingadmin");

        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(existingUser));

        adminService.registerAdmin(dto);
    }

    /**
     * Tests the {@link AdminServiceImpl#findAllAdmins()} method.
     * Verifies that the method returns a list of all admins.
     */
    @Test
    public void testFindAllAdmins() {
        Admin admin = new Admin();
        when(adminRepository.findAll()).thenReturn(Collections.singletonList(admin));

        try {
            List<Admin> admins = adminService.findAllAdmins();
            assertNotNull(admins);
            assertEquals(admins.size(), 1);
            verify(adminRepository, times(1)).findAll();
        } catch (Exception e) {
            fail("Exception should not be thrown");
        }
    }
}
