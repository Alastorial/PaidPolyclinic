package ru.alastorial.paidpolyclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alastorial.paidpolyclinic.dto.DoctorDto;
import ru.alastorial.paidpolyclinic.entity.Doctor;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "specialty", source = "specialty")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "polyclinicId", expression = "java(doctor.getPolyclinic() == null ? null : doctor.getPolyclinic().getId())")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "specialty", source = "specialty")
    Doctor toEntity(DoctorDto doctorDto);


}
