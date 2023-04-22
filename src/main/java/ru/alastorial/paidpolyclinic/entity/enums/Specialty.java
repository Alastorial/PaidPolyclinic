package ru.alastorial.paidpolyclinic.entity.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Specialty {

    CHIEF_MEDICAL_OFFICER("Главный врач"),
    DEPUTY_CHIEF_PHYSICIAN("Заместитель главного врача"),
    THERAPIST("Терапевт"),
    PEDIATRICIAN("Педиатр"),
    OPTOMETRIST("Окулист"),
    NEUROLOGIST("Невролог"),
    SURGEON("Хирург"),
    ALLERGIST("Аллерголог");

    private final String specialization;

}
