package ru.alastorial.paidpolyclinic.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
public class DoctorDto {

    private UUID id;

    @NotEmpty(message = "First name should not be empty")
    private String firstName;

    @NotEmpty(message = "Last name should not be empty")
    private String lastName;

    @NotEmpty(message = "Phone number should not be empty")
    private String phoneNumber;

    @NotEmpty(message = "Email should not be empty")
    private String email;

    @NotNull(message = "Specialty should not be empty")
    private String specialty;

    @NotEmpty(message = "Work start should not be empty")
    private String workStart;

    @NotEmpty(message = "Work end should not be empty")
    private String workEnd;

    @Min(1)
    private int duration;

    @Min(1)
    private int price;

    @NotNull(message = "Polyclinic id should not be empty")
    private UUID polyclinicId;

    private Instant createdAt;

    private List<UUID> appointmentsId;

}
