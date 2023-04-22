package ru.alastorial.paidpolyclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.alastorial.paidpolyclinic.dto.DoctorDto;
import ru.alastorial.paidpolyclinic.entity.Doctor;
import ru.alastorial.paidpolyclinic.service.DoctorService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/doctor")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping
    public List<DoctorDto> getAll() {
        return doctorService.getAll();
    }

    @GetMapping("/{id}")
    public Doctor getOne(@PathVariable UUID id) {
        return doctorService.getById(id);
    }

    @PostMapping
    public DoctorDto create(@RequestBody @Valid DoctorDto doctorDto) {
        return doctorService.save(doctorDto);
    }

    @PatchMapping
    public DoctorDto update(@RequestBody @Valid DoctorDto doctorDto) {
        return doctorService.save(doctorDto);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        doctorService.delete(id);
    }

}
