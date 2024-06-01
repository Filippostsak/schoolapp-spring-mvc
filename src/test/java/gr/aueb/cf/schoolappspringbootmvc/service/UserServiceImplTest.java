package gr.aueb.cf.schoolappspringbootmvc.service;

import gr.aueb.cf.schoolappspringbootmvc.model.User;
import gr.aueb.cf.schoolappspringbootmvc.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testGetUserByUsernameSuccess() {
        String username = "testUser";
        User mockUser = new User();
        mockUser.setUsername(username);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));

        User user = userService.getUserByUsername(username);

        assertNotNull(user);
        assertEquals(username, user.getUsername());
        verify(userRepository, times(1)).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetUserByUsernameNotFound() {
        String username = "nonExistentUser";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername(username));

        assertEquals("User with username " + username + " not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetUserByUsernameWithNull() {
        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername(null));

        assertEquals("User with username null not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(null);
        verifyNoMoreInteractions(userRepository);
    }

    @Test
    public void testGetUserByUsernameWithEmptyString() {
        String username = "";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        Exception exception = assertThrows(UsernameNotFoundException.class, () -> userService.getUserByUsername(username));

        assertEquals("User with username  not found", exception.getMessage());
        verify(userRepository, times(1)).findByUsername(username);
        verifyNoMoreInteractions(userRepository);
    }
}
