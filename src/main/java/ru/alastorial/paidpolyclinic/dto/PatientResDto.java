package ru.alastorial.paidpolyclinic.dto;

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
public class PatientResDto {

    private UUID id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String email;

    private String username;

    private Instant createdAt;

    private List<UUID> appointmentsId;

}
