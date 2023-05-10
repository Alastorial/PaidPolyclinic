package ru.alastorial.paidpolyclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.alastorial.paidpolyclinic.dto.DoctorDto;
import ru.alastorial.paidpolyclinic.entity.Doctor;
import ru.alastorial.paidpolyclinic.entity.enums.Specialty;

import java.time.LocalTime;

@Mapper(componentModel = "spring")
public interface DoctorMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "specialty", source = "specialty")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "appointmentsId", expression = "java(doctor.getAppointments().stream().map(app -> app.getId()).toList())")
    @Mapping(target = "polyclinicId", expression = "java(doctor.getPolyclinic() == null ? null : doctor.getPolyclinic().getId())")
    @Mapping(target = "price", source = "price")
    DoctorDto toDto(Doctor doctor);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "firstName", source = "firstName")
    @Mapping(target = "lastName", source = "lastName")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "specialty", source = "specialty", qualifiedByName = "stringToEnum")
    @Mapping(target = "workStart", source = "workStart", qualifiedByName = "timeToLocalTime")
    @Mapping(target = "workEnd", source = "workEnd", qualifiedByName = "timeToLocalTime")
    @Mapping(target = "duration", source = "duration")
    @Mapping(target = "price", source = "price")
    Doctor toEntity(DoctorDto doctorDto);

    @Named("timeToLocalTime")
    default LocalTime timeToLocalTime(String date) {
        return LocalTime.parse(date);
    }

    @Named("stringToEnum")
    default Specialty stringToEnum(String specialty) {
        return Specialty.fromString(specialty);
    }

}
