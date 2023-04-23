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
import ru.alastorial.paidpolyclinic.dto.AppointmentDto;
import ru.alastorial.paidpolyclinic.service.AppointmentService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @GetMapping
    public List<AppointmentDto> getAll() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public AppointmentDto getOne(@PathVariable UUID id) {
        return appointmentService.getById(id);
    }

    @PostMapping
    public AppointmentDto create(@RequestBody @Valid AppointmentDto appointmentDto) {
        return appointmentService.save(appointmentDto);
    }

    @PatchMapping
    public AppointmentDto update(@RequestBody @Valid AppointmentDto appointmentDto) {
        return appointmentService.save(appointmentDto);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        appointmentService.delete(id);
    }


}
