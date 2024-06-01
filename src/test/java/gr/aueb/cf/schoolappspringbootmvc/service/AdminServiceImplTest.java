package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.Mapper;
import gr.aueb.cf.schoolappspringbootmvc.model.Admin;
import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.AdminRepository;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import gr.aueb.cf.schoolappspringbootmvc.service.exceptions.AdminAlreadyExistsException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    private RegisterAdminDTO registerAdminDTO;
    private Admin admin;
    private User user;

    @BeforeEach
    void setUp() {
        registerAdminDTO = new RegisterAdminDTO();
        registerAdminDTO.setUsername("adminUser");
        registerAdminDTO.setEmail("admin@example.com");
        registerAdminDTO.setPassword("Admin@1234");
        registerAdminDTO.setConfirmPassword("Admin@1234");
        registerAdminDTO.setFirstname("Admin");
        registerAdminDTO.setLastname("User");
        registerAdminDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        registerAdminDTO.setCountry("Country");
        registerAdminDTO.setCity("City");

        admin = new Admin();
        admin.setFirstname("Admin");
        admin.setLastname("User");

        user = new User();
        user.setUsername("adminUser");
        user.setPassword("Admin@1234");
    }

    @Test
    void registerAdmin_ShouldRegisterAdmin_WhenAdminDoesNotExist() throws AdminAlreadyExistsException {
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            // Arrange
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
            when(adminRepository.save(any(Admin.class))).thenReturn(admin);
            mockedMapper.when(() -> Mapper.extractAdminFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(admin);
            mockedMapper.when(() -> Mapper.extractUserFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(user);

            // Act
            Admin registeredAdmin = adminService.registerAdmin(registerAdminDTO);

            // Assert
            assertNotNull(registeredAdmin);
            assertEquals("Admin", registeredAdmin.getFirstname());
            assertEquals("User", registeredAdmin.getLastname());
            verify(userRepository, times(1)).findByUsername(anyString());
            verify(passwordEncoder, times(1)).encode(anyString());
            verify(adminRepository, times(1)).save(any(Admin.class));
        }
    }

    @Test
    void findAllAdmins_ShouldReturnListOfAdmins() throws Exception {
        // Arrange
        List<Admin> admins = List.of(admin);
        when(adminRepository.findAll()).thenReturn(admins);

        // Act
        List<Admin> result = adminService.findAllAdmins();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Admin", result.get(0).getFirstname());
        assertEquals("User", result.get(0).getLastname());
        verify(adminRepository, times(1)).findAll();
    }
    @Test
    void registerAdmin_ShouldThrowException_WhenAdminAlreadyExists() {
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            // Arrange
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));
            mockedMapper.when(() -> Mapper.extractAdminFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(admin);
            mockedMapper.when(() -> Mapper.extractUserFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(user);

            // Act and Assert
            assertThrows(AdminAlreadyExistsException.class, () -> adminService.registerAdmin(registerAdminDTO));
            verify(userRepository, times(1)).findByUsername(anyString());
            verify(adminRepository, times(0)).save(any(Admin.class));
        }
    }

    @Test
    void registerAdmin_ShouldThrowException_WhenPasswordEncodingFails() {
        try (MockedStatic<Mapper> mockedMapper = mockStatic(Mapper.class)) {
            // Arrange
            when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(anyString())).thenThrow(new RuntimeException("Encoding failed"));
            mockedMapper.when(() -> Mapper.extractAdminFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(admin);
            mockedMapper.when(() -> Mapper.extractUserFromRegisterAdminDTO(any(RegisterAdminDTO.class))).thenReturn(user);

            // Act and Assert
            RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.registerAdmin(registerAdminDTO));
            assertEquals("Encoding failed", exception.getMessage());
            verify(userRepository, times(1)).findByUsername(anyString());
            verify(adminRepository, times(0)).save(any(Admin.class));
        }
    }

    @Test
    void findAllAdmins_ShouldHandleException() {
        // Arrange
        when(adminRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act and Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> adminService.findAllAdmins());
        assertEquals("Database error", exception.getMessage());
        verify(adminRepository, times(1)).findAll();
    }
}
