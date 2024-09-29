package ru.petrov.springjwt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.server.ResponseStatusException;
import ru.petrov.springjwt.model.dto.JwtAuthenticationResponse;
import ru.petrov.springjwt.model.dto.SignInRequest;
import ru.petrov.springjwt.model.dto.SignUpRequest;
import ru.petrov.springjwt.service.AuthenticationService;

import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class AuthControllerTest {
    @Mock
    private AuthenticationService authenticationService;

    @InjectMocks
    private AuthController authController;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Попытка получить токен в случае успешной регистрации")
    void signUp_ShouldReturnCreated_WhenValidRequest() {
        SignUpRequest request = new SignUpRequest();
        request.setEmail("asfsdf@sdfs.ru");
        request.setPassword("teststst");
        request.setUsername("teststst");

        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("token");

        when(authenticationService.signUp(any(SignUpRequest.class))).thenReturn(jwtResponse);

        JwtAuthenticationResponse response = authController.signUp(request);

        assertEquals(jwtResponse.getToken(), response.getToken());
    }

    @Test
    @DisplayName("Попытка получить токен в случае успешной авторизации")
    void signIn_ShouldReturnToken_WhenValidRequest() {
        SignInRequest request = new SignInRequest("teststst", "teststst");
        JwtAuthenticationResponse jwtResponse = new JwtAuthenticationResponse("token");

        when(authenticationService.signIn(any(SignInRequest.class))).thenReturn(jwtResponse);

        JwtAuthenticationResponse response = authController.signIn(request);

        assertEquals(jwtResponse.getToken(), response.getToken());
    }

    @Test
    @DisplayName("Перехват исключения. Проверка валидации.")
    void handleException_ShouldReturnBadRequest_WhenValidationFails() throws NoSuchMethodException {

        BindingResult bindingResult = new BeanPropertyBindingResult(new Object(), "signUpRequest");
        bindingResult.addError(new FieldError("signUpRequest", "fieldName", "Validation error!"));
        Method method = AuthController.class.getMethod("signUp", SignUpRequest.class);
        MethodParameter parameter = new MethodParameter(method, 0);

        MethodArgumentNotValidException exception = new MethodArgumentNotValidException(parameter, bindingResult);
        ResponseStatusException response = authController.handleException(exception);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(exception.getMessage(), response.getReason());
    }
}
