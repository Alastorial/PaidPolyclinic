package ru.alastorial.paidpolyclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alastorial.paidpolyclinic.dto.PatientRegistryDto;
import ru.alastorial.paidpolyclinic.dto.PatientResDto;
import ru.alastorial.paidpolyclinic.entity.Patient;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "username", source = "username")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "createdAt", source = "createdAt")
    Patient toEntity(PatientRegistryDto patientRegistryDTO);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdAt", source = "createdAt")
    PatientRegistryDto toRegDto(Patient patient);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "appointmentsId", expression = "java(patient.getAppointments().stream().map(app -> app.getId()).toList())")
    PatientResDto toDto(Patient patient);

}
