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
import ru.alastorial.paidpolyclinic.dto.PolyclinicDto;
import ru.alastorial.paidpolyclinic.entity.Polyclinic;
import ru.alastorial.paidpolyclinic.service.PolyclinicService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/polyclinics")
@RequiredArgsConstructor
public class PolyclinicController {
    private final PolyclinicService polyclinicService;
    @GetMapping
    public List<PolyclinicDto> getAll() {
        return polyclinicService.getAll();
    }

    @GetMapping("/{id}")
    public PolyclinicDto getOne(@PathVariable UUID id) {
        return polyclinicService.getById(id);
    }

    @GetMapping("/{id}/doctors")
    public List<DoctorDto> getAllDoctorsByPolyclinic(@PathVariable UUID id) {
        return polyclinicService.getDoctorsByPolyclinicId(id);
    }

    @PostMapping
    public PolyclinicDto create(@RequestBody @Valid Polyclinic polyclinic) {
        return polyclinicService.save(polyclinic);
    }

    @PatchMapping
    public PolyclinicDto update(@RequestBody @Valid Polyclinic polyclinic) {
        return polyclinicService.save(polyclinic);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        polyclinicService.delete(id);
    }

}
