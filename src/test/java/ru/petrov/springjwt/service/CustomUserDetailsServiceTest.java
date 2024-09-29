package ru.petrov.springjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ru.petrov.springjwt.model.Role;
import ru.petrov.springjwt.model.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

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
    @DisplayName("Попытка для существующего пользователя")
    public void testLoadUserByUsername_UserFound() {
        when(userService.getByUsername("username")).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername("username");

        assertAll(
                () -> verify(userService).getByUsername("username"),
                () -> assertNotNull(userDetails),
                () -> assertEquals("username", userDetails.getUsername())
        );
    }

    @Test
    @DisplayName("Попытка для несуществующего пользователя")
    public void testLoadUserByUsername_UserNotFound() {
        when(userService.getByUsername(anyString())).thenThrow(new UsernameNotFoundException("Пользователь не найден"));

        assertAll(
                () -> assertThrows(UsernameNotFoundException.class, () ->
                        customUserDetailsService.loadUserByUsername("nonexistentUser")),
                () -> verify(userService).getByUsername("nonexistentUser")
        );
    }
}
