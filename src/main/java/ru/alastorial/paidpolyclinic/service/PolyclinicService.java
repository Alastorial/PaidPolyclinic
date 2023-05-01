package ru.alastorial.paidpolyclinic.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.alastorial.paidpolyclinic.dto.DoctorDto;
import ru.alastorial.paidpolyclinic.dto.PolyclinicDto;
import ru.alastorial.paidpolyclinic.entity.Polyclinic;
import ru.alastorial.paidpolyclinic.error.BadRequestException;
import ru.alastorial.paidpolyclinic.error.NotFoundException;
import ru.alastorial.paidpolyclinic.mapper.DoctorMapper;
import ru.alastorial.paidpolyclinic.mapper.PolyclinicMapper;
import ru.alastorial.paidpolyclinic.repository.PolyclinicRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PolyclinicService {

    private final PolyclinicRepository polyclinicRepository;

    private final DoctorMapper doctorMapper;
    private final PolyclinicMapper polyclinicMapper;

    public List<PolyclinicDto> getAll() {
        return polyclinicRepository.findAll().stream().map(polyclinicMapper::toDto).toList();
    }

    public PolyclinicDto getById(UUID id) {
        return polyclinicMapper.toDto(polyclinicRepository.findById(id).orElseThrow(() -> new NotFoundException("Polyclinic with id " + id + " not found")));
    }

    public List<DoctorDto> getDoctorsByPolyclinicId(UUID id) {
        Polyclinic polyclinic = polyclinicRepository.findById(id).orElseThrow(() -> new NotFoundException("Polyclinic with id " + id + " not found"));
        return polyclinic.getDoctors().stream().map(doctorMapper::toDto).toList();
    }

    public PolyclinicDto save(Polyclinic polyclinic) {
        if (polyclinicRepository.getByName(polyclinic.getName()) != null) {
            throw new BadRequestException("Polyclinic with name " + polyclinic.getName() + " already exists");
        }

        return polyclinicMapper.toDto(polyclinicRepository.save(polyclinic));
    }

    public void delete(UUID id) {
        Polyclinic polyclinic = polyclinicRepository.findById(id).orElseThrow(() -> new BadRequestException("There is no polyclinic with id: " + id));
        polyclinicRepository.delete(polyclinic);
    }


}
