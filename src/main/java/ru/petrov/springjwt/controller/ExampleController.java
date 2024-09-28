package ru.petrov.springjwt.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.petrov.springjwt.model.dto.ExampleResponse;
import ru.petrov.springjwt.service.UserService;

@RestController
@RequestMapping(path = "/example",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Tag(name = "Примеры", description = "Примеры запросов с разными правами доступа")
@SecurityRequirement(name = "bearerAuth")
public class ExampleController {
    private final UserService userService;

    @GetMapping
    @Operation(summary = "Доступен только авторизованным пользователям")
    public ResponseEntity<ExampleResponse> example() {
        return new ResponseEntity<>(getAuthorities(), HttpStatus.OK);
    }


    @GetMapping("/admin")
    @Operation(summary = "Доступен только авторизованным пользователям с ролью ADMIN")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ExampleResponse> exampleAdmin() {
        return new ResponseEntity<>(getAuthorities(), HttpStatus.OK);
    }

    private ExampleResponse getAuthorities() {
        return new ExampleResponse("Hello. " +
                "Your role: " + userService.getCurrentUser().getAuthorities());
    }

}