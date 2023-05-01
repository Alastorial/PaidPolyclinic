package ru.alastorial.paidpolyclinic.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.alastorial.paidpolyclinic.dto.PolyclinicDto;
import ru.alastorial.paidpolyclinic.entity.Polyclinic;

@Mapper(componentModel = "spring")
public interface PolyclinicMapper {


    @Mapping(target = "id", source = "id")
    @Mapping(target = "name", source = "name")
    @Mapping(target = "doctorsId", expression = "java(polyclinic.getDoctors().stream().map(doc -> doc.getId()).toList())")
    PolyclinicDto toDto(Polyclinic polyclinic);

}
