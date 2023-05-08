package ru.alastorial.paidpolyclinic.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PatientRegistryDTO {

    private UUID id;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotBlank(message = "Необходимо указать логин/имя пользователя")
    private String username;

    @NotBlank(message = "Необходимо указать пароль")
    private String password;

    private Instant createdAt;

    private List<UUID> appointmentsId;

}
