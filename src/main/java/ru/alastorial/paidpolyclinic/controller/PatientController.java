package ru.alastorial.paidpolyclinic.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
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
import ru.alastorial.paidpolyclinic.dto.PatientRegistryDto;
import ru.alastorial.paidpolyclinic.dto.PatientResDto;
import ru.alastorial.paidpolyclinic.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientResDto> getAll() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public PatientResDto getOne(@PathVariable UUID id) {
        return patientService.getById(id);
    }

    @GetMapping("/{id}/appointments")
    public List<AppointmentDto> getAllAppointmentsByPatient(@PathVariable UUID id) {
        return patientService.getAppointmentsByPatientId(id);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @PostMapping("/appointments")
    public PatientResDto makeAppointment(@RequestParam UUID appointmentId) {
        return patientService.makeAppointment(appointmentId);
    }

    @PreAuthorize("hasAuthority('PATIENT')")
    @DeleteMapping("/appointments")
    public PatientResDto removeAppointment(@RequestParam UUID appointmentId) {
        return patientService.removeAppointment(appointmentId);
    }

    @PatchMapping
    public PatientResDto update(@RequestBody @Valid PatientRegistryDto patientRegistryDTO) {
        return patientService.update(patientRegistryDTO);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        patientService.delete(id);
    }

}
