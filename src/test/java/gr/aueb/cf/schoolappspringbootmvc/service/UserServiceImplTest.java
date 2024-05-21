package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertThrows;

/**
 * Unit tests for the {@link UserServiceImpl} class.
 */
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    /**
     * Sets up the test environment. Initializes mocks and injects them into the tested class.
     */
    @BeforeMethod
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    /**
     * Tests the {@link UserServiceImpl#getUserByUsername(String)} method.
     * Verifies that the method returns the expected user when the user exists.
     */
    @Test
    public void testGetUserByUsername_UserExists() {
        // Arrange
        String username = "testUser";
        User expectedUser = new User();
        expectedUser.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(expectedUser));

        // Act
        User actualUser = userService.getUserByUsername(username);

        // Assert
        assertEquals(actualUser, expectedUser);
    }

    /**
     * Tests the {@link UserServiceImpl#getUserByUsername(String)} method.
     * Verifies that the method throws a {@link UsernameNotFoundException} when the user is not found.
     */
    @Test
    public void testGetUserByUsername_UserNotFound() {
        // Arrange
        String username = "nonExistentUser";
        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> {
            userService.getUserByUsername(username);
        });
    }
}
