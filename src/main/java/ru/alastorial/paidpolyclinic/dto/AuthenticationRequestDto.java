package ru.alastorial.paidpolyclinic.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class AuthenticationRequestDto {

    @NotBlank(message = "Необходимо указать логин/имя пользователя")
    private String username;

    @NotBlank(message = "Необходимо указать пароль")
    private String password;
}
