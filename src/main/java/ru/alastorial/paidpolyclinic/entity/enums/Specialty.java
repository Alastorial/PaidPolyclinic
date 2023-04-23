package ru.alastorial.paidpolyclinic.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.alastorial.paidpolyclinic.error.BadRequestException;

@Getter
@AllArgsConstructor
public enum Specialty {

    CHIEF_MEDICAL_OFFICER("Главный врач"),
    DEPUTY_CHIEF_PHYSICIAN("Заместитель главного врача"),
    THERAPIST("Терапевт"),
    PEDIATRICIAN("Педиатр"),
    OPTOMETRIST("Окулист"),
    NEUROLOGIST("Невролог"),
    SURGEON("Хирург"),
    ALLERGIST("Аллерголог");

    private String specialization;

    public static Specialty fromString(String value) {
        if (value != null) {
            for (Specialty rt : Specialty.values()) {
                if (value.equalsIgnoreCase(rt.toString())) {
                    return rt;
                }
            }
        }
        throw new BadRequestException("No such specialization");
    }

}
