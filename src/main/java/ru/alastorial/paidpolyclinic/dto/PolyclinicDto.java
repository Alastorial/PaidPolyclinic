package ru.alastorial.paidpolyclinic.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class PolyclinicDto {

    private UUID id;

    @NotEmpty(message = "Name should not be empty")
    private String name;

    private List<UUID> doctorsId;

}
