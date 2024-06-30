package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminGetAuthenticatedAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.AdminUpdateDTO;
import gr.aueb.cf.schoolappspringbootmvc.dto.admin.RegisterAdminDTO;
import gr.aueb.cf.schoolappspringbootmvc.mapper.AdminMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.UserMapper;
import gr.aueb.cf.schoolappspringbootmvc.mapper.exception.AdminNotFoundException;
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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminRepository adminRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AdminMapper adminMapper;

    @InjectMocks
    private AdminServiceImpl adminService;

    private RegisterAdminDTO registerAdminDTO;
    private Admin admin;
    private User user;

    @BeforeEach
    void setUp() {
        registerAdminDTO = new RegisterAdminDTO();
        registerAdminDTO.setUsername("testUsername");
        registerAdminDTO.setPassword("testPassword");
        registerAdminDTO.setConfirmPassword("testPassword");
        registerAdminDTO.setEmail("test@example.com");
        registerAdminDTO.setFirstname("Test");
        registerAdminDTO.setLastname("User");
        registerAdminDTO.setBirthDate(LocalDate.of(1990, 1, 1));
        registerAdminDTO.setCountry("Country");
        registerAdminDTO.setCity("City");

        admin = new Admin();
        user = new User();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
    }

    @Test
    void registerAdmin_whenAdminDoesNotExist_shouldRegisterAdmin() throws AdminAlreadyExistsException {
        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.extractAdminFromRegisterAdminDTO(registerAdminDTO)).thenReturn(admin);
            mockedUserMapper.when(() -> UserMapper.extractUserFromRegisterAdminDTO(registerAdminDTO)).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.empty());
            when(passwordEncoder.encode(user.getPassword())).thenReturn("encodedPassword");

            Admin registeredAdmin = adminService.registerAdmin(registerAdminDTO);

            verify(adminRepository).save(admin);
            assertNotNull(registeredAdmin);
        }
    }

    @Test
    void registerAdmin_whenAdminAlreadyExists_shouldThrowException() {
        try (MockedStatic<UserMapper> mockedUserMapper = mockStatic(UserMapper.class)) {
            mockedUserMapper.when(() -> UserMapper.extractAdminFromRegisterAdminDTO(registerAdminDTO)).thenReturn(admin);
            mockedUserMapper.when(() -> UserMapper.extractUserFromRegisterAdminDTO(registerAdminDTO)).thenReturn(user);
            when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

            assertThrows(AdminAlreadyExistsException.class, () -> adminService.registerAdmin(registerAdminDTO));
        }
    }

    @Test
    void findAllAdmins_shouldReturnAllAdmins() throws Exception {
        List<Admin> adminList = List.of(admin);
        when(adminRepository.findAll()).thenReturn(adminList);

        List<Admin> result = adminService.findAllAdmins();

        assertEquals(adminList.size(), result.size());
    }

    @Test
    void getAuthenticatedAdmin_whenAdminIsAuthenticated_shouldReturnAdmin() throws AdminNotFoundException {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(adminRepository.findByUserUsername("testUsername")).thenReturn(Optional.of(admin));
        AdminGetAuthenticatedAdminDTO dto = new AdminGetAuthenticatedAdminDTO();
        when(adminMapper.toAdminGetAuthenticatedAdminDTO(admin)).thenReturn(dto);

        AdminGetAuthenticatedAdminDTO result = adminService.getAuthenticatedAdmin(dto);

        assertNotNull(result);
        assertEquals("testUsername", result.getUsername());
    }

    @Test
    void getAuthenticatedAdmin_whenAdminNotFound_shouldThrowException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        AdminGetAuthenticatedAdminDTO dto = new AdminGetAuthenticatedAdminDTO();

        assertThrows(AdminNotFoundException.class, () -> adminService.getAuthenticatedAdmin(dto));
    }

    @Test
    void deleteCurrentAdmin_whenAdminExists_shouldDeleteAdmin() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(adminRepository.findByUserUsername("testUsername")).thenReturn(Optional.of(admin));

        adminService.deleteCurrentAdmin();

        verify(adminRepository).delete(admin);
        verify(userRepository).delete(user);
    }

    @Test
    void deleteCurrentAdmin_whenAdminNotFound_shouldThrowException() {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.deleteCurrentAdmin());
    }

    @Test
    void updateAdmin_whenAdminExists_shouldUpdateAdmin() {
        AdminUpdateDTO updateDTO = new AdminUpdateDTO("Firstname", "Lastname", "username", "email@example.com", "Password1@", LocalDate.now(), "Country", "City");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.of(user));
        when(adminRepository.findByUserUsername("testUsername")).thenReturn(Optional.of(admin));
        when(adminMapper.mapAdminDTOToAdmin(updateDTO, admin)).thenReturn(admin);

        adminService.updateAdmin(updateDTO);

        verify(adminRepository).save(admin);
        verify(userRepository).save(user);
    }

    @Test
    void updateAdmin_whenAdminNotFound_shouldThrowException() {
        AdminUpdateDTO updateDTO = new AdminUpdateDTO("Firstname", "Lastname", "username", "email@example.com", "Password1@", LocalDate.now(), "Country", "City");
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        SecurityContextHolder.setContext(securityContext);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn("testUsername");
        when(userRepository.findByUsername("testUsername")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> adminService.updateAdmin(updateDTO));
    }
}
