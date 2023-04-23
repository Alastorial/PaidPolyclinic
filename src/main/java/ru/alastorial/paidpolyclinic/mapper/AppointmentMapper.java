package ru.alastorial.paidpolyclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import ru.alastorial.paidpolyclinic.dto.AppointmentDto;
import ru.alastorial.paidpolyclinic.entity.Appointment;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public interface AppointmentMapper {

    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time")
    @Mapping(target = "patientId", expression = "java(appointment.getPatient() == null ? null : appointment.getPatient().getId())")
    @Mapping(target = "createdAt", source = "createdAt")
    @Mapping(target = "doctorId", expression = "java(appointment.getDoctor() == null ? null : appointment.getDoctor().getId())")
    AppointmentDto toDto(Appointment appointment);


    @Mapping(target = "id", source = "id")
    @Mapping(target = "time", source = "time", qualifiedByName = "timeToLocalDateTime")
    @Mapping(target = "createdAt", source = "createdAt")
    Appointment toEntity(AppointmentDto appointmentDto);

    @Named("timeToLocalDateTime")
    default LocalDateTime timeToLocalDateTime(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return LocalDateTime.parse(date, formatter);
    }


}
