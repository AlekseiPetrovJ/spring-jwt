package ru.petrov.springjwt.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Запрос на аутентификацию")
public class SignInRequest {

    @Schema(description = "Имя пользователя", example = "admin")
    private String username;

    @Schema(description = "Пароль", example = "admin")
    private String password;
}