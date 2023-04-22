package ru.alastorial.paidpolyclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.dto.DoctorDto;
import ru.alastorial.paidpolyclinic.entity.Doctor;
import ru.alastorial.paidpolyclinic.error.BadRequestException;
import ru.alastorial.paidpolyclinic.error.NotFoundException;
import ru.alastorial.paidpolyclinic.mapper.DoctorMapper;
import ru.alastorial.paidpolyclinic.repository.DoctorRepository;
import ru.alastorial.paidpolyclinic.repository.PolyclinicRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    private final PolyclinicRepository polyclinicRepository;

    private final DoctorMapper doctorMapper;

    public List<DoctorDto> getAll() {
        return doctorRepository.findAll().stream().map(doctorMapper::toDto).toList();
    }

    public Doctor getById(UUID id) {
        return doctorRepository.findById(id).orElseThrow(() -> new NotFoundException("Doctor with id " + id + " not found"));
    }

    public DoctorDto save(DoctorDto doctorDto) {
        Doctor doctor = doctorMapper.toEntity(doctorDto);

        if (doctorDto.getPolyclinicId() != null) {
            doctor.setPolyclinic(polyclinicRepository.findById(doctorDto.getPolyclinicId()).orElseThrow(() -> new BadRequestException("There is no polyclinic with id: " + doctorDto.getPolyclinicId())));
        }

        return doctorMapper.toDto(doctorRepository.save(doctor));
    }

    public void delete(UUID id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new BadRequestException("There is no doctor with id: " + id));
        doctorRepository.delete(doctor);
    }


}
