package ru.petrov.springjwt.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Пример ответа", example = "Hello. Your role: [ROLE_ADMIN]")
public record ExampleResponse(String message) {
}
