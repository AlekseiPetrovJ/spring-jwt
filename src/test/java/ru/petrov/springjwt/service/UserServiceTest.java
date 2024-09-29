package ru.petrov.springjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.petrov.springjwt.exception.UserAlreadyExistException;
import ru.petrov.springjwt.model.Role;
import ru.petrov.springjwt.model.User;
import ru.petrov.springjwt.repository.UserRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = User.builder()
                .username("username")
                .email("email@example.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    public void testCreate_UserSuccessfullyCreated() {
        when(repository.existsByUsername(user.getUsername())).thenReturn(false);
        when(repository.existsByEmail(user.getEmail())).thenReturn(false);
        when(repository.save(any(User.class))).thenReturn(user);

        User createdUser = userService.create(user);

        assertAll(
                () -> verify(repository).save(user),
                () -> assertEquals(user, createdUser)
        );

    }

    @Test
    public void testCreate_UserAlreadyExistsByUsername() {
        when(repository.existsByUsername(user.getUsername())).thenReturn(true);

        assertAll(
                () -> assertThrows(UserAlreadyExistException.class, () -> userService.create(user)),
                () -> verify(repository, never()).save(any(User.class))
        );
    }

    @Test
    public void testCreate_UserAlreadyExistsByEmail() {
        when(repository.existsByUsername(user.getUsername())).thenReturn(false);
        when(repository.existsByEmail(user.getEmail())).thenReturn(true);

        assertAll(
                () -> assertThrows(UserAlreadyExistException.class, () -> userService.create(user)),
                () -> verify(repository, never()).save(any(User.class))
        );
    }

    @Test
    public void testGetByUsername_UserFound() {
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User foundUser = userService.getByUsername(user.getUsername());

        assertAll(
                () -> assertEquals(user, foundUser),
                () -> verify(repository).findByUsername(user.getUsername())
        );
    }

    @Test
    public void testGetByUsername_UserNotFound() {
        when(repository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertAll(
                () -> assertThrows(UsernameNotFoundException.class, () -> userService.getByUsername("nonexistentUser")),
                () -> verify(repository).findByUsername("nonexistentUser")
        );
    }

    @Test
    public void testGetCurrentUser() {
        SecurityContext securityContext = mock(SecurityContext.class);
        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.setContext(securityContext);

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(user.getUsername());
        when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        User currentUser = userService.getCurrentUser();

        assertAll(
                () -> assertEquals(user, currentUser),
                () -> verify(repository).findByUsername(user.getUsername())
        );
    }
}
