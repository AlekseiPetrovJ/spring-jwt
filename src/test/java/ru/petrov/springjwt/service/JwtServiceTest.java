package ru.petrov.springjwt.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;
import ru.petrov.springjwt.model.Role;
import ru.petrov.springjwt.model.User;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles(profiles = "test")
@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    private final Duration tokenExpiration = Duration.ofMinutes(60);
    private User user;

    @BeforeEach
    public void setUp() {
        String jwtSecret = "im8476r34f34f34geue7wn6ergn76ergfs76wngrfiwuntr8476r34f34f34geue7wn6ergn76ergfs76wngrfiwuntrfbyt5fbyt5rd6";
        jwtService.setJwtSecret(jwtSecret);
        jwtService.setTokenExpiration(tokenExpiration);
        user = User.builder()
                .username("username")
                .email("email@example.com")
                .role(Role.ROLE_USER)
                .build();
    }

    @Test
    public void testExtractUserName() {
        String token = jwtService.generateToken(user);
        String username = jwtService.extractUserName(token);

        assertEquals(user.getUsername(), username);
    }

    @Test
    public void testGenerateToken() {
        String token = jwtService.generateToken(user);

        assertNotNull(token);
        assertFalse(token.isEmpty());
    }

    @Test
    public void testIsTokenValid_ValidToken() {
        String token = jwtService.generateToken(user);

        assertTrue(jwtService.isTokenValid(token, user));
    }

    @Test
    public void testIsTokenValid_InvalidToken() {
        String token = jwtService.generateToken(user);
        User otherUser = User.builder()
                .username("otherusername")
                .email("emaile@example.com")
                .role(Role.ROLE_USER)
                .build();
        assertFalse(jwtService.isTokenValid(token, otherUser));
    }
}