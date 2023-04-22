package ru.alastorial.paidpolyclinic.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.alastorial.paidpolyclinic.entity.enums.Specialty;

import java.time.Instant;
import java.util.UUID;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class DoctorDto {

    private UUID id;

    @NotBlank(message = "First name should not be empty")
    private String firstName;

    @NotBlank(message = "Last name should not be empty")
    private String lastName;

    @NotBlank(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotBlank(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Specialty should not be empty")
    private Specialty specialty;

    private UUID polyclinicId;

    private Instant createdAt;

}
