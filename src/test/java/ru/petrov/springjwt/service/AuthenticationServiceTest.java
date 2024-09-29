package ru.petrov.springjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.petrov.springjwt.model.Role;
import ru.petrov.springjwt.model.User;
import ru.petrov.springjwt.model.dto.JwtAuthenticationResponse;
import ru.petrov.springjwt.model.dto.SignInRequest;
import ru.petrov.springjwt.model.dto.SignUpRequest;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @InjectMocks
    private AuthenticationService authenticationService;

    private SignUpRequest signUpRequest;
    private SignInRequest signInRequest;
    private User user;

    @BeforeEach
    public void setUp() {
        signUpRequest = new SignUpRequest("username", "email@example.com", "password");
        signInRequest = new SignInRequest("username", "password");
        user = User.builder()
                .username("username")
                .email("email@example.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    @DisplayName("Попытка успешного создания пользователя")
    public void testSignUp() {
        when(passwordEncoder.encode(signUpRequest.getPassword())).thenReturn("encodedPassword");
        when(userService.create(any(User.class))).thenReturn(user);
        when(jwtService.generateToken(any(User.class))).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signUp(signUpRequest);

        assertAll(
                () -> verify(userService).create(any(User.class)),
                () -> verify(jwtService).generateToken(any(User.class)),
                () -> assertEquals("jwtToken", response.getToken())
        );
    }

    @Test
    @DisplayName("Попытка успешной авторизации")
    public void testSignIn() {
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userDetailsService.loadUserByUsername(signInRequest.getUsername())).thenReturn(user);
        when(jwtService.generateToken(user)).thenReturn("jwtToken");

        JwtAuthenticationResponse response = authenticationService.signIn(signInRequest);
        assertAll(
                () -> verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class)),
                () -> verify(jwtService).generateToken(user),
                () -> assertEquals("jwtToken", response.getToken())
        );
    }
}