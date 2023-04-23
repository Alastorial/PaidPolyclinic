package ru.alastorial.paidpolyclinic.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class AppointmentDto {

    private UUID id;

    @NotNull(message = "Doctor id should not be empty")
    private UUID doctorId;

    @NotEmpty(message = "Visit time should not be empty")
    private String time;

    @NotNull(message = "Patient id should not be empty")
    private UUID patientId;

    private Instant createdAt;
}
