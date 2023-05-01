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
import ru.alastorial.paidpolyclinic.dto.PatientDto;
import ru.alastorial.paidpolyclinic.service.PatientService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @GetMapping
    public List<PatientDto> getAll() {
        return patientService.getAll();
    }

    @GetMapping("/{id}")
    public PatientDto getOne(@PathVariable UUID id) {
        return patientService.getById(id);
    }

    @GetMapping("/{id}/appointments")
    public List<AppointmentDto> getAllAppointmentsByPatient(@PathVariable UUID id) {
        return patientService.getAppointmentsByPatientId(id);
    }

    @PostMapping
    public PatientDto create(@RequestBody @Valid PatientDto patientDto) {
        return patientService.save(patientDto);
    }

    @PostMapping("/appointments")
    public PatientDto makeAppointment(@RequestParam UUID patientId, @RequestParam UUID appointmentId) {
        return patientService.makeAppointment(patientId, appointmentId);
    }

    @DeleteMapping("/appointments")
    public PatientDto removeAppointment(@RequestParam UUID patientId, @RequestParam UUID appointmentId) {
        return patientService.removeAppointment(patientId, appointmentId);
    }

    @PatchMapping
    public PatientDto update(@RequestBody @Valid PatientDto patientDto) {
        return patientService.save(patientDto);
    }

    @DeleteMapping
    public void delete(@RequestParam UUID id) {
        patientService.delete(id);
    }

}
